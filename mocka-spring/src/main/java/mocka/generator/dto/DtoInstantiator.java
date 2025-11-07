package mocka.generator.dto;


import org.springframework.core.codec.Hints;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DtoInstantiator {

    private final DtoFieldValueGenerator generator;

    public DtoInstantiator() {
        this.generator = DtoFieldValueGenerator.getInstance();
    }

    public <T> T create(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value;

                if(field.get(instance) != null) continue;

                value = generator.get(field);
                field.set(instance, value);
            }

            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
