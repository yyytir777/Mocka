package jodag.generator;

import jodag.exception.MissingRequiredAnnotationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringGeneratorFactory extends GeneratorFactory {

    private static final Map<String, EntityGenerator<?>> entityGenerators = new ConcurrentHashMap<>();

    public static <T> void add(Class<T> clazz) {
        entityGenerators.put(clazz.getSimpleName(), new EntityGenerator<>(clazz, EntityInstanceCreator.getInstance()));
    }

    @SuppressWarnings("unchecked")
    public static <T> EntityGenerator<T> getGenerator(Class<T> clazz) {
        EntityGenerator<?> generator = entityGenerators.get(clazz.getSimpleName());
        if (generator == null) {
            throw new MissingRequiredAnnotationException("No @Generate annotation for target class " + clazz.getSimpleName());
        }
        return (EntityGenerator<T>) generator;
    }

    public static List<String> getGeneratorNames() {
        return new ArrayList<>(entityGenerators.keySet());
    }
}
