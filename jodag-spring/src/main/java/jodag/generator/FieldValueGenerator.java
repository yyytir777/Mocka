package jodag.generator;

import jakarta.persistence.*;
import jodag.annotation.Email;
import jodag.annotation.LoremIpsum;
import jodag.random.RandomProvider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

public class FieldValueGenerator {

    private static final FieldValueGenerator INSTANCE = new FieldValueGenerator();

    private final RandomProvider randomProvider;

    private FieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();
    }

    public static FieldValueGenerator getInstance() {
        return INSTANCE;
    }

    private final List<Predicate<Field>> notGenerableConditions = List.of(
            field -> field.isAnnotationPresent(GeneratedValue.class) && field.isAnnotationPresent(Id.class),
            field -> field.isAnnotationPresent(Transient.class),
            field -> field.isAnnotationPresent(ElementCollection.class),
            field -> Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers())
    );

    private final Map<Class<?>, Supplier<?>> primitiveGeneratorMap = Map.ofEntries(
            Map.entry(int.class, GeneratorFactory.primitive()::getInteger),
            Map.entry(Integer.class, GeneratorFactory.primitive()::getInteger),
            Map.entry(long.class, GeneratorFactory.primitive()::getLong),
            Map.entry(Long.class, GeneratorFactory.primitive()::getLong),
            Map.entry(float.class, GeneratorFactory.primitive()::getFloat),
            Map.entry(Float.class, GeneratorFactory.primitive()::getFloat),
            Map.entry(double.class, GeneratorFactory.primitive()::getDouble),
            Map.entry(Double.class, GeneratorFactory.primitive()::getDouble),
            Map.entry(boolean.class, GeneratorFactory.primitive()::getBoolean),
            Map.entry(Boolean.class, GeneratorFactory.primitive()::getBoolean),
            Map.entry(char.class, GeneratorFactory.primitive()::getCharacter),
            Map.entry(Character.class, GeneratorFactory.primitive()::getCharacter)
    );

    public Object get(Field field) {
        Class<?> type = field.getType();

        // 필드 생성이 필요 없는 경우 ex) generatevalue, transient, elementcollection ...
        if(isNotGenerable(field)) {
            return null;
        }

        // Email 애노테이션 + String 필드
        if(field.getGenericType().equals(String.class) && hasAnnotation(field, Email.class)) {
            return GeneratorFactory.email().get();
        }
        else if(!field.getGenericType().equals(String.class) && hasAnnotation(field, Email.class)) {
            throw new RuntimeException("Email 애노테이션은 String 타입에만 사용 가능합니다.");
        }

        // String인 필드 + length가 명시되어 있을 때
        if(hasAnnotation(field, Column.class)) {
            Column column = field.getAnnotation(Column.class);

            if(field.getGenericType().equals(String.class) && column.length() > 0) {

                if(hasAnnotation(field, LoremIpsum.class)) {
                    return GeneratorFactory.loremIpsum().get(column.length());
                }
                return GeneratorFactory.string().getUpTo(column.length());
            }
        }

        // enum일 경우
        if(type.isEnum() && hasAnnotation(field, Enumerated.class)) {
            Object[] enumConstants = type.getEnumConstants();
            if(enumConstants.length == 0) return null;
            return enumConstants[randomProvider.getInt(enumConstants.length)];
        }

        // primitive일 경우
        if(isPrimitiveOrWrapper(type)) {
            Supplier<?> generator = primitiveGeneratorMap.get(type);
            if(generator != null) return generator.get();
        }


        // java.math일 경우
        if(type.equals(BigDecimal.class)) {
            return GeneratorFactory.numeric().getBigDecimal();
        } else if(type.equals(BigInteger.class)) {
            return GeneratorFactory.numeric().getBigInteger();
        }

        // 날짜 타입일 경우 (java.util, java.time, java.sql)
        if(isDateTimeType(type)) {
            return GeneratorFactory.dateTime().get(type);
        }

        throw new UnsupportedOperationException("지원하지 않는 타입입니다.");
    }

    private boolean isDateTimeType(Class<?> type) {
        return Temporal.class.isAssignableFrom(type)
                || type.equals(java.util.Calendar.class)
                || java.util.Date.class.isAssignableFrom(type);
    }

    private boolean hasAnnotation(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    private boolean isNotGenerable(Field field) {
        return notGenerableConditions.stream().anyMatch(p -> p.test(field));
    }
}
