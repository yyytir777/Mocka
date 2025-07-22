package jodag.generator;

import jodag.generator.primitive.PrimitiveGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Supplier;

public class EntityInstanceCreator {

    private static final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    private static final Map<Class<?>, Class<?>> primitiveToWrapperMap = Map.ofEntries(
            Map.entry(boolean.class, Boolean.class),
            Map.entry(byte.class, Byte.class),
            Map.entry(short.class, Short.class),
            Map.entry(int.class, Integer.class),
            Map.entry(long.class, Long.class),
            Map.entry(float.class, Float.class),
            Map.entry(double.class, Double.class),
            Map.entry(char.class, Character.class)
    );

    private static final Map<Class<?>, Supplier<Object>> defaultValueMap = Map.ofEntries(
            Map.entry(Boolean.class, primitiveGenerator::getBoolean),
            Map.entry(Byte.class, primitiveGenerator::getByte),
            Map.entry(Short.class, primitiveGenerator::getShort),
            Map.entry(Integer.class, primitiveGenerator::getInteger),
            Map.entry(Long.class, primitiveGenerator::getLong),
            Map.entry(Float.class, primitiveGenerator::getFloat),
            Map.entry(Double.class, primitiveGenerator::getDouble),
            Map.entry(Character.class, primitiveGenerator::getCharacter),
            Map.entry(String.class, () -> GeneratorFactory.string().get())
    );

    public static <T> T create(Class<T> clazz) throws NoSuchMethodException {
        Constructor<T> constructor = getAllArgsConstructor(clazz);
        constructor.setAccessible(true);

        Class<?>[] parameterTypes = constructor.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = generateValue(parameterTypes[i]);
        }

        try {
            return constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T generateValue(Class<T> parameterType) {

        // primitive & wrapper handler
        Class<?> wrapperType = primitiveToWrapperMap.get(parameterType);
        Supplier<Object> supplier = defaultValueMap.get(wrapperType);

//        if (supplier == null) {
//            throw new UnsupportedOperationException("지원하지 않는 파라미터 타입 : " + parameterType.getName());
//        }
//
//        // enum handler
//        if(parameterType.isEnum()) {
//            T[] enumValues = parameterType.getEnumConstants();
//            if(e)
//        }

        // date handler

        //
        return (T) supplier.get();
    }

    @SuppressWarnings("unchecked")
    private static <T> Constructor<T> getAllArgsConstructor(Class<T> clazz) {
        return (Constructor<T>) Arrays.stream(clazz.getDeclaredConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new IllegalStateException("생성자가 존재하지 않습니다. - " + clazz.getSimpleName()));
    }
}
