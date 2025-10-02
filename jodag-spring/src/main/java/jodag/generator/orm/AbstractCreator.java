package jodag.generator.orm;

import jodag.annotation.ValueSource;
import jodag.exception.ValueSourceException;
import jodag.generator.factory.GeneratorRegistry;

import java.lang.reflect.Field;

public abstract class AbstractCreator {

    @SuppressWarnings("unchecked")
    protected  <T> T handleValueSource(Field field) {
        ValueSource valueSource = field.getAnnotation(ValueSource.class);
        String path = valueSource.path();
        Class<?> type = valueSource.type();
        String key = valueSource.generatorKey();

        // 1. generateKey
        if (!key.isEmpty()) {
            return (T) GeneratorRegistry.getGenerator(key).get();
        }

        // 2. (path, type)
        if (!valueSource.path().isEmpty() && valueSource.type() != Object.class) {
            return (T) GeneratorRegistry.getGenerator(path, path, type).get();
        }

        throw new ValueSourceException("Cannot resolve generator from ValueSource: " + valueSource);
    }

}
