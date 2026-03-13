package mocka.orm.generator;

import mocka.core.generator.Creator;
import mocka.core.annotation.FileSource;
import mocka.core.annotation.RegexSource;
import mocka.core.annotation.ValueSource;
import mocka.core.exception.ValueSourceException;
import mocka.core.generator.factory.GeneratorRegistry;
import mocka.core.generator.regex.RegexGenerator;

import java.lang.reflect.Field;

public abstract class AbstractORMCreator implements Creator {

    private final RegexGenerator regexGenerator = RegexGenerator.getInstance();

    /**
     * Handles field value generation for fields annotated with {@link ValueSource}.
     *
     * <p>
     * This method resolves a generator from the {@link GeneratorRegistry} using the information
     * provided in the {@link ValueSource} annotation. It follows a priority-based resolution strategy:
     * </p>
     *
     * <ol>
     *   <li><b>Generator Key:</b> If {@code generatorKey} is specified, looks up the generator directly by key</li>
     *   <li><b>Path and Type:</b> If both {@code path} and {@code type} are specified,
     *       looks up the generator using path and type information</li>
     * </ol>
     *
     * <p><b>Usage Examples:</b></p>
     * <pre>
     * // Using generator key
     * {@literal @}ValueSource(generatorKey = "name")
     * private String name;
     *
     * // Using path and type
     * {@literal @}ValueSource(path = "user.email", type = String.class)
     * private String email;
     * </pre>
     *
     * @param field the field annotated with {@link ValueSource}
     * @param <T>   the expected return type of the generated value
     * @return the generated value from the resolved generator
     * @throws ValueSourceException if the generator cannot be resolved from the {@link ValueSource} annotation
     *                              (e.g., when neither generatorKey nor path+type combination is properly specified)
     */
    @SuppressWarnings("unchecked")
    public <T> T handleValueSource(Field field) {
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

    public String handleRegexSource(Field field) {
        RegexSource regexSource = field.getAnnotation(RegexSource.class);
        String pattern = regexSource.value();
        return regexGenerator.get(pattern);
    }


    public <T> T initInstance(Class<T> clazz) {
        T instance = null;
        FileSource fileSource = clazz.getAnnotation(FileSource.class);
        if(fileSource != null) {
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
