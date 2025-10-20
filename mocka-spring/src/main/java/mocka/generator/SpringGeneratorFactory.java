package mocka.generator;

import jakarta.annotation.PostConstruct;
import mocka.exception.GeneratorException;
import mocka.generator.factory.GeneratorFactory;
import mocka.generator.orm.ORMCreator;
import mocka.generator.orm.ORMResolver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * {@code SpringGeneratorFactory} integrates the core {@link GeneratorFactory}
 * with Spring, providing automatic discovery and registration of entity generators
 * using ORM resolvers.
 * <p>
 * This factory scans entities from the configured {@link ORMCreator},
 * registers them, and allows retrieval of {@link EntityGenerator} instances
 * for specific entity classes.
 * </p>
 *
 * <p><b>Lifecycle:</b> The initialization process is performed after Spring dependency injection
 * via the {@link jakarta.annotation.PostConstruct @PostConstruct} annotated {@code init()} method.</p>
 */
@Component
public class SpringGeneratorFactory extends GeneratorFactory {

    /**
     * Responsible for creating entity instances during generation.
     */
    private final EntityInstanceCreator entityInstanceCreator;

    /**
     * Provides ORM resolvers to discover and load entity classes.
     */
    private final ORMCreator ormCreator;

    /**
     * A registry mapping entity classes to their corresponding {@link EntityGenerator}.
     */
    private static final Map<Class<?>, EntityGenerator<?>> entityGenerators = new ConcurrentHashMap<>();

    /**
     * A set of entity classes discovered during ORM scanning.
     */
    private final Set<Class<?>> SCANNED_CLASSES = new HashSet<>();

    /**
     * Constructs a new {@code SpringGeneratorFactory} with the required dependencies.
     *
     * @param entityInstanceCreator the factory responsible for creating entity instances
     * @param ormCreator            the ORM creator used to provide entity resolvers
     */
    public SpringGeneratorFactory(EntityInstanceCreator entityInstanceCreator, ORMCreator ormCreator) {
        this.entityInstanceCreator = entityInstanceCreator;
        this.ormCreator = ormCreator;
    }


    /**
     * Initializes the factory by registering scanned generators automatically.
     * <p>
     * Executed automatically after Spring dependency injection is complete.
     * </p>
     */
    @PostConstruct
    private void init() {
        load();
        registerEntities();
    }

    /**
     * Loads entity classes from {@link ORMResolver}
     * <p>
     * provided by the {@link ORMCreator}.
     * </p>
     */
    private void load() {
        List<ORMResolver> resolvers = ormCreator.getResolver();
        for (ORMResolver resolver : resolvers) {
            Set<Class<?>> load = resolver.load();
            SCANNED_CLASSES.addAll(load);
        }
    }

    /**
     * put EntityGenerator generated using scanned classes automatically.
     */
    private void registerEntities() {
        for (Class<?> clazz : SCANNED_CLASSES) {
            entityGenerators.put(clazz, new EntityGenerator<>(clazz, entityInstanceCreator));
        }
    }

    /**
     * Retrieves the {@link EntityGenerator} associated with the given entity class. <br>
     * When the application starts, {@code SpringGeneratorFactory} automatically scans and registers all entity generators.
     * <p>
     * Therefore, you do not need to manually register the entity class you want to generate.
     * </p>
     *
     * @param clazz the entity class
     * @param <T>   the entity type
     * @return the {@link EntityGenerator} for the given class
     * @throws GeneratorException if no generator is found for the class
     */
    @SuppressWarnings("unchecked")
    public <T> EntityGenerator<T> getGenerator(Class<T> clazz) {
        EntityGenerator<?> generator = entityGenerators.get(clazz);
        if (generator == null) {
            throw new GeneratorException("Cannot find entity class");
        }
        return (EntityGenerator<T>) generator;
    }

    /**
     * @return list of entity classes with registered generators
     */
    public List<Class<?>> getEntityGeneratorNames() {
        return new ArrayList<>(entityGenerators.keySet());
    }

    /**
     * @return the ORM creator instance used by this factory.
     */
    public ORMCreator getOrmCreator() {
        return this.ormCreator;
    }
}