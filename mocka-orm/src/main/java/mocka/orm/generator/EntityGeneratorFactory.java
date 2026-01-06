package mocka.orm.generator;

import mocka.core.exception.GeneratorException;
import mocka.core.generator.factory.GeneratorFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code EntityGeneratorFactory} is a registry and access point for
 * {@link EntityGenerator} instances.
 *
 * <p>
 * This factory maintains a mapping between entity classes and their
 * corresponding {@link EntityGenerator}. It allows generators to be
 * retrieved by entity type at runtime.
 * </p>
 *
 * <p>
 * All EntityGenerators are registered during application initialization
 * by {@link EntityGeneratorInitializer}.
 * </p>
 */
@Component
public class EntityGeneratorFactory extends GeneratorFactory {

    /**
     * Registry mapping entity classes to their corresponding generators.
     */
    private static final Map<Class<?>, EntityGenerator<?>> ORM_GENERATORS = new ConcurrentHashMap<>();

    /**
     * Returns the {@link EntityGenerator} associated with the given entity class.
     *
     * @param clazz the entity class
     * @param <T>   the entity type
     * @return the corresponding entity generator
     * @throws GeneratorException if no generator is registered for the class
     */
    @SuppressWarnings("unchecked")
    public <T> EntityGenerator<T> getGenerator(Class<T> clazz) {
        EntityGenerator<?> generator = ORM_GENERATORS.get(clazz);
        if (generator == null) {
            throw new GeneratorException("Cannot find entity class");
        }
        return (EntityGenerator<T>) generator;
    }

    /**
     * Returns all entity classes for which generators are registered.
     *
     * @return a list of registered entity classes
     */
    public List<Class<?>> getEntityGeneratorNames() {
        return new ArrayList<>(ORM_GENERATORS.keySet());
    }


    /**
     * Registers multiple {@link EntityGenerator} instances at once.
     *
     * @param generators a map of entity classes to generators
     */
    public void registerAll(Map<Class<?>, EntityGenerator<?>> generators) {
        ORM_GENERATORS.putAll(generators);
    }
}
