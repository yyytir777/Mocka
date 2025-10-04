package jodag.generator.orm;

import jodag.annotation.FileSource;
import jodag.annotation.ValueSource;
import jodag.exception.ValueSourceException;
import jodag.generator.FileSourceCreator;
import jodag.generator.factory.GeneratorRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public abstract class AbstractCreator {

    private final FileSourceCreator fileSourceCreator;

    public AbstractCreator(FileSourceCreator fileSourceCreator) {
        this.fileSourceCreator = fileSourceCreator;
    }

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

    protected  <T> T initInstance(Class<T> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        T instance;
        FileSource fileSource = clazz.getAnnotation(FileSource.class);
        if(fileSource != null) {
            instance = fileSourceCreator.createFromFileSource(clazz, fileSource);
        } else {
            instance = clazz.getDeclaredConstructor().newInstance();
        }

        return instance;
    }
}
