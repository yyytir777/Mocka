package jodag.generator;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringGeneratorFactory extends GeneratorFactory {

    private static final Map<String, Generator<?>> entityGenerators = new ConcurrentHashMap<>();

    public static <T> void add(Class<T> clazz) {
        entityGenerators.put(clazz.getSimpleName(), new EntityGenerator<>(clazz, EntityInstanceCreator.getInstance()));
    }

    @SuppressWarnings("unchecked")
    public static <T> Generator<T> getGenerator(Class<T> clazz) {
        Generator<?> generator = entityGenerators.get(clazz.getSimpleName());
        if (generator == null) {
            throw new IllegalStateException("No generator for class " + clazz.getName());
        }
        return (Generator<T>) generator;
    }

    public static List<String> getGeneratorNames() {
        return new ArrayList<>(entityGenerators.keySet());
    }
}
