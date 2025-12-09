package mocka.orm.generator.mybatis;

import mocka.core.generator.factory.GeneratorFactory;
import mocka.core.generator.FieldValueGenerator;
import mocka.core.random.RandomProvider;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.function.Supplier;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

/**
 * Implementation of {@link FieldValueGenerator} specialized for MyBatis.
 * <p>
 * This component is responsible for generating random or mock values
 * for fields of entities managed by MyBatis. It supports:
 * </p>
 * <ul>
 *   <li>Primitive and wrapper types (int, long, float, double, boolean, char, etc.)</li>
 *   <li>{@code Enum} types</li>
 *   <li>{@link BigDecimal}, {@link BigInteger}</li>
 *   <li>Date/time classes from {@code java.util}, {@code java.time}, and {@code java.sql}</li>
 *   <li>Primitive arrays ({@code byte[]}, {@code char[]})</li>
 * </ul>
 * <p>
 * If the given field type is unsupported, an {@link UnsupportedOperationException}
 * will be thrown.
 * </p>
 */
@Component
public class MyBatisFieldValueGenerator implements FieldValueGenerator {

    private final RandomProvider randomProvider;
    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    public MyBatisFieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();
    }

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

        // enum
        if (type.isEnum()) {
            Object[] enumConstants = type.getEnumConstants();
            if(enumConstants.length == 0) return null;
            return enumConstants[randomProvider.getNextIdx(enumConstants.length)];
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
                // primitive 배열: byte[], char[]
                if (componentType == byte.class) return generatorFactory.asByteArray().get();
                else if (componentType == char.class) return generatorFactory.asCharacterArray().get();
            }
        }

        if(type == String.class) {
            return generatorFactory.asString().get();
        }

        throw new UnsupportedOperationException();
    }

    private boolean hasAnnotation(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }
}
