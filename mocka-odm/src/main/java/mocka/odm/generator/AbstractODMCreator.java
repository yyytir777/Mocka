package mocka.odm.generator;

import mocka.core.annotation.FileSource;
import mocka.core.annotation.RegexSource;
import mocka.core.annotation.ValueSource;
import mocka.core.exception.ValueSourceException;
import mocka.core.generator.Creator;
import mocka.core.generator.factory.GeneratorRegistry;
import mocka.core.generator.regex.RegexGenerator;

import java.lang.reflect.Field;

public abstract class AbstractODMCreator implements Creator {

    private final RegexGenerator regexGenerator = RegexGenerator.getInstance();

    @SuppressWarnings("unchecked")
    @Override
    public <T> T handleValueSource(Field field) {
        ValueSource valueSource = field.getAnnotation(ValueSource.class);
        String path = valueSource.path();
        Class<?> type = valueSource.type();
        String key = valueSource.generatorKey();

        // 1. generatorKey
        if(!key.isEmpty()){
            return (T) GeneratorRegistry.getGenerator(key).get();
        }

        // 2. (path, type)
        if(path != null && type != Object.class) {
            return (T) GeneratorRegistry.getGenerator(path, path, type).get();
        }

        throw new ValueSourceException("Cannot resolve generator from ValueSource: " + valueSource);
    }

    @Override
    public String handleRegexSource(Field field) {
        RegexSource regexSource = field.getAnnotation(RegexSource.class);
        String pattern = regexSource.value();
        return regexGenerator.get(pattern);
    }

    @Override
    public <T> T initInstance(Class<T> clazz) {
        T instance = null;
        FileSource fileSource = clazz.getAnnotation(FileSource.class);
        if(fileSource != null){
            instance = createFromFileSource(clazz, fileSource);
        }
        if(instance == null) {
            try {
                instance = clazz.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to create instance of " + clazz.getName(), e);
            }
        }
        return instance;
    }
}
