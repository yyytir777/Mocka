package entityinstantiator.generator;

import entityinstantiator.generator.orm.ORMCreator;
import entityinstantiator.generator.orm.ORMType;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * {@code EntityInstanceCreator} is responsible for creating entity instances
 * through the configured {@link ORMCreator}. <br>
 * It supports multiple ORM implementations and allows instance creation
 * with different {@link GenerateType} strategies.
 *
 * <p>This class is managed as a Spring {@link Component} and is typically
 * injected into factories or services that need entity instance generation.</p>
 */
@Component
public class EntityInstanceCreator {

    /**
     * The {@link ORMCreator} responsible for delegating entity creation logic.
     */
    private final ORMCreator ormCreator;

    private EntityInstanceCreator(ORMCreator ormCreator) {
        this.ormCreator = ormCreator;
    }

    /**
     * Creates an entity instance for the given class using the default ORM. <br>
     * This method should be used when there is only one ORM implementation configured.
     * <p>
     * If the {@link GenerateType} is {@code ALL}, all related entities
     * (both parent and child relationships) are generated recursively.
     * Otherwise, the instance is created according to the specified strategy.
     * </p>
     *
     * @param clazz        the class type of the entity to generate
     * @param generateType the generation strategy (e.g., {@link GenerateType})
     * @param <T>          the type of the entity
     * @return a new instance of the given entity class
     */
    public <T> T createInstance(Class<T> clazz, GenerateType generateType) {
        if(generateType.equals(GenerateType.ALL)) {
            return ormCreator.create(clazz, new HashMap<>(), new HashSet<>());
        }
        return ormCreator.create(clazz, generateType);
    }

    /**
     * Creates an entity instance for the given class using a specific ORM implementation.<br>
     * This method should be used when multiple ORM implementations are configured,
     * and you want to explicitly select which {@link ORMType} should be used for entity creation.
     *
     * <p>
     * If the {@link GenerateType} is {@code ALL}, all related entities
     * (both parent and child relationships) are generated recursively.
     * Otherwise, the instance is created according to the specified strategy.
     * </p>
     *
     * @param ormType      the ORM implementation type to use
     * @param clazz        the class type of the entity to generate
     * @param generateType the generation strategy (e.g., SINGLE, CHILD, PARENT, ALL)
     * @param <T>          the type of the entity
     * @return a new instance of the given entity class
     */
    public <T> T createInstance(ORMType ormType, Class<T> clazz, GenerateType generateType) {
        if(generateType.equals(GenerateType.ALL)) {
            return ormCreator.create(ormType, clazz, new HashMap<>(), new HashSet<>());
        }
        return ormCreator.create(ormType, clazz, generateType);
    }
}
