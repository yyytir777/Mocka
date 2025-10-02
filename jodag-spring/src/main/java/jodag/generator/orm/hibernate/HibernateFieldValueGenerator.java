package jodag.generator.orm.hibernate;

import jakarta.persistence.*;
import jodag.annotation.LoremIpsum;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.orm.FieldValueGenerator;
import jodag.random.RandomProvider;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

/**
 * Generates random values for Hibernate entity fields based on their types and JPA annotations.
 *
 * <p>
 * This generator analyzes field metadata (type, annotations, constraints) and produces
 * appropriate random values for entity instantiation. It supports various data types including
 * primitives, dates, enums, embeddables, and respects JPA validation constraints.
 * </p>
 */
@Component
public class HibernateFieldValueGenerator implements FieldValueGenerator {

    private final RandomProvider randomProvider;
    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    private HibernateFieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();
    }

    private final List<Predicate<Field>> notGenerableConditions = List.of(
            field -> field.isAnnotationPresent(GeneratedValue.class) && field.isAnnotationPresent(Id.class),
            field -> field.isAnnotationPresent(Transient.class),
            field -> field.isAnnotationPresent(ElementCollection.class),
            field -> Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
    );

    private final Map<Class<?>, Supplier<?>> primitiveGeneratorMap = Map.ofEntries(
            Map.entry(int.class, () -> generatorFactory.asInteger().get()),
            Map.entry(Integer.class, () ->  generatorFactory.asInteger().get()),
            Map.entry(long.class, () -> generatorFactory.asLong().get()),
            Map.entry(Long.class, () -> generatorFactory.asLong().get()),
            Map.entry(float.class, () -> generatorFactory.asFloat().get()),
            Map.entry(Float.class, () -> generatorFactory.asFloat().get()),
            Map.entry(double.class, () -> generatorFactory.asDouble().get()),
            Map.entry(Double.class, () -> generatorFactory.asDouble().get()),
            Map.entry(boolean.class, () -> generatorFactory.asBoolean().get()),
            Map.entry(Boolean.class, () -> generatorFactory.asBoolean().get()),
            Map.entry(char.class, () -> generatorFactory.asCharacter().get()),
            Map.entry(Character.class, () -> generatorFactory.asCharacter().get())
    );

    /**
     * Generates a random value for the given field based on its type and annotations.
     *
     * @param field the field to generate a value for
     * @return a randomly generated value appropriate for the field type,
     *         or null if the field should not be generated
     * @throws RuntimeException if {@code @Email} is used on a non-String field
     * @throws UnsupportedOperationException if the field type is not supported
     */
     @Override
    public Object get(Field field) {
        Class<?> type = field.getType();

        // no need to generate... ex) generateValue, transient, elementCollection ...
        if(isNotGenerable(field)) {
            return null;
        }

        // string + length
        if(hasAnnotation(field, Column.class)) {
            Column column = field.getAnnotation(Column.class);

            if(field.getGenericType().equals(String.class) && column.length() > 0) {

                if(hasAnnotation(field, LoremIpsum.class)) {
                    return generatorFactory.asLoremIpsum().get(column.length());
                }
                return generatorFactory.asString().getUpTo(column.length());
            }
        }

        // enum
        if(type.isEnum() && hasAnnotation(field, Enumerated.class)) {
            Object[] enumConstants = type.getEnumConstants();
            if(enumConstants.length == 0) return null;
            return enumConstants[randomProvider.getInt(enumConstants.length)];
        }

        // primitive
        if(isPrimitiveOrWrapper(type)) {
            Supplier<?> generator = primitiveGeneratorMap.get(type);
            if(generator != null) return generator.get();
        }


        // java.math
        if(type.equals(BigDecimal.class)) {
            return generatorFactory.asBigDecimal().get();
        } else if(type.equals(BigInteger.class)) {
            return generatorFactory.asBigInteger().get();
        }

        // java.util, java.time, java.sql
        if (type.getPackageName().startsWith("java.time")) {
            return generatorFactory.asDateTime().get(type);
        } else if (type.getPackageName().startsWith("java.sql")) {
            return generatorFactory.asSqlDate().get(type);
        } else if (type.getPackageName().startsWith("java.util")) {
            return generatorFactory.asLegacyDate().get(type);
        }

        // byte[], char[]
        if (type.isArray()) {
            Class<?> componentType = type.getComponentType();
            if (componentType.isPrimitive()) {
                // primitive array : byte[], char[]
                if (componentType == byte.class) return generatorFactory.asByteArray().get();
                else if (componentType == char.class) return generatorFactory.asCharacterArray().get();
                throw new UnsupportedOperationException();
            }
        }

        if(type == String.class) {
            return generatorFactory.asString().get();
        }

        if(hasAnnotation(field, Embedded.class) &&
        field.getType().isAnnotationPresent(Embeddable.class)) {
            return createEmbeddableInstance(field.getType());
        }

        throw new UnsupportedOperationException();
    }

    /**
     * Recursively creates an instance of an embeddable class and populates its fields.
     *
     * <p>
     * This method is used for handling {@code @Embedded} types that are marked with
     * {@code @Embeddable}. It creates an instance and recursively generates values
     * for all its fields.
     * </p>
     *
     * @param type the embeddable class to instantiate
     * @return a fully populated instance of the embeddable class
     * @throws RuntimeException if instantiation or field access fails
     * @see Embedded
     * @see Embeddable
     */
    private Object createEmbeddableInstance(Class<?> type) {
        try {
            Object instance = type.getDeclaredConstructor().newInstance();
            for(Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, get(field));
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasAnnotation(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    private boolean isNotGenerable(Field field) {
        return notGenerableConditions.stream().anyMatch(p -> p.test(field));
    }
}
