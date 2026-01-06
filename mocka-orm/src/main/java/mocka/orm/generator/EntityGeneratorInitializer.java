package mocka.orm.generator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code EntityGeneratorInitializer} is responsible for discovering ORM-managed
 * entity classes at application startup and registering corresponding
 * {@link EntityGenerator} instances.
 *
 * <p>
 * During initialization, this component:
 * </p>
 * <ol>
 *   <li>Loads entity classes managed by each ORM</li>
 *   <li>Creates {@link EntityGenerator} instances per entity class</li>
 *   <li>Registers them into {@link EntityGeneratorFactory}</li>
 * </ol>
 *
 * <p>
 * This class does not participate in entity generation at runtime.
 * Its sole responsibility is bootstrap-time discovery and registration.
 * </p>
 */
@Component
public class EntityGeneratorInitializer {

    /**
     * Factory responsible for storing and providing {@link EntityGenerator} instances.
     */
    private final EntityGeneratorFactory entityGeneratorFactory;

    /**
     * Resolver used to obtain available {@link ORMCreator} implementations.
     */
    private final ORMResolver ormResolver;

    public EntityGeneratorInitializer(EntityGeneratorFactory entityGeneratorFactory, ORMResolver ormResolver) {
        this.entityGeneratorFactory = entityGeneratorFactory;
        this.ormResolver = ormResolver;
    }

    /**
     * Initializes and registers entity generators after the Spring context
     * has been fully constructed.
     *
     * <p>
     * This method scans all resolved {@link ORMCreator}s, loads the entity classes
     * they manage, and creates a corresponding {@link EntityGenerator} for each.
     * </p>
     */
    @PostConstruct
    public void init() {
        Map<Class<?>, EntityGenerator<?>> generators = new HashMap<>();

        List<ORMCreator> creators = ormResolver.getCreators();
        for (ORMCreator creator : creators) {
            Set<Class<?>> entityClasses = creator.load();
            for (Class<?> entityClass : entityClasses) {
                generators.put(entityClass, new EntityGenerator<>(entityClass, ormResolver));
            }
        }
        entityGeneratorFactory.registerAll(generators);
    }

    public ORMResolver getOrmSelector() {
        return this.ormResolver;
    }
}
