package jodag.generator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

public class EntityInstanceCreator {

    private static final EntityInstanceCreator INSTANCE = new EntityInstanceCreator(FieldValueGenerator.getInstance());

    private final FieldValueGenerator fieldValueGenerator;

    public static EntityInstanceCreator getInstance() {
        return INSTANCE;
    }

    private EntityInstanceCreator(FieldValueGenerator fieldValueGenerator) {
        this.fieldValueGenerator = fieldValueGenerator;
    }

    public <T> T create(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                // 한 필드에 대한 애노테이션 메타데이터
                // 필드클래스와 애노테이션을 분석하여 해당 필드를 랜덤으로 채움 -> 값이 없는 경우도 존재
                Object value = generateValue(field);
                field.set(instance, value);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 클래스의 필드 값을 분석하여 해당 필드의 랜덤 값을 리턴함
     */
    @SuppressWarnings("unchecked")
    private <T> T generateValue(Field field) {
        return (T) fieldValueGenerator.get(field);
    }


    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> getAllArgsConstructor(Class<T> clazz) {
        return (Constructor<T>) Arrays.stream(clazz.getDeclaredConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new IllegalStateException("생성자가 존재하지 않습니다. - " + clazz.getSimpleName()));
    }
}
