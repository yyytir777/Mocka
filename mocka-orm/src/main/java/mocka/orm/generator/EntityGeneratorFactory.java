package mocka.orm.generator;

import mocka.core.exception.GeneratorException;
import mocka.core.generator.factory.GeneratorFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EntityGeneratorFactory extends GeneratorFactory {

    private static final Map<Class<?>, EntityGenerator<?>> ORM_GENERATORS = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> EntityGenerator<T> getGenerator(Class<T> clazz) {
        EntityGenerator<?> generator = ORM_GENERATORS.get(clazz);
        if (generator == null) {
            throw new GeneratorException("Cannot find entity class");
        }
        return (EntityGenerator<T>) generator;
    }

    public List<Class<?>> getEntityGeneratorNames() {
        return new ArrayList<>(ORM_GENERATORS.keySet());
    }

    public void registerAll(Map<Class<?>, EntityGenerator<?>> generators) {
        ORM_GENERATORS.putAll(generators);
    }
}
