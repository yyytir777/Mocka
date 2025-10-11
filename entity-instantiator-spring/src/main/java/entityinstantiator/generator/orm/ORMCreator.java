package entityinstantiator.generator.orm;

import jakarta.persistence.Entity;
import entityinstantiator.exception.GeneratorException;
import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.orm.hibernate.VisitedPath;
import entityinstantiator.generator.orm.hibernate.HibernateCreator;
import entityinstantiator.generator.orm.mybatis.MyBatisCreator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code ORMCreator} is responsible for managing and delegating entity creation
 * to the appropriate {@link ORMResolver} based on the configured ORM type(s).
 * <p>
 * It supports multiple ORM frameworks (Hibernate and MyBatis) and determines
 * which resolver to use either automatically (via entity annotations) or
 * explicitly (via {@link ORMType} parameter).
 * </p>
 *
 * <p>This class is registered as a Spring {@link org.springframework.stereotype.Component}
 * and is constructed with available ORM beans and configuration properties.</p>
 */
@Component
public class ORMCreator {

    /**
     * Spring {@link BeanFactory} used to detect the presence of ORM-related beans.
     */
    private final BeanFactory beanFactory;

    /**
     * A registry mapping {@link ORMType} to the corresponding {@link ORMResolver}.
     */
    private Map<ORMType, ORMResolver> ormResolver = new HashMap<>();

    private static final String MYBATIS_BEAN_NAME = "sqlSessionFactory";
    private static final String HIBERNATE_BEAN_NAME = "entityManagerFactory";

    public ORMCreator(BeanFactory beanFactory, ORMProperties ormProperties, List<ORMResolver>  ormResolvers) {
        this.beanFactory = beanFactory;
        this.ormResolver = resolver(ormProperties, ormResolvers);
    }

    /**
     * Returns all registered {@link ORMResolver} implementations.
     *
     * @return a list of available ORM resolvers
     * @throws GeneratorException if no resolver is available
     */
    public List<ORMResolver> getResolver() {
        if (this.ormResolver.isEmpty()) {
            throw new GeneratorException("ORM resolver is null");
        }
        return new ArrayList<>(ormResolver.values());
    }

    /**
     * Creates an entity instance by automatically detecting the ORM type
     * associated with the given entity class. <br>
     * <p>
     * - If the class is annotated with {@link jakarta.persistence.Entity}, Hibernate will be used. <br>
     * - Otherwise, MyBatis is assumed by default. <br>
     * </p>
     * This allows entity creation to be handled differently depending on
     * the ORM framework managing the given entity class.
     *
     * @param clazz        the entity class to instantiate
     * @param generateType the generation strategy to apply ({@link GenerateType} except {@code GenerateType.ALL})
     * @param <T>          the type of the entity
     * @return a new entity instance created using the appropriate ORM
     * @throws GeneratorException if no ORM resolver is available
     */
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(ormResolver.isEmpty()) {
            throw new GeneratorException("ORM resolver is null");
        }
        return ormResolver.get(detectORM(clazz)).create(clazz, generateType);
    }

    /**
     * Creates an entity instance by automatically detecting the ORM type
     * associated with the given entity class. <br>
     *
     * <p>
     * This method generates not only the specified entity but also all related entities
     * (both parent and child relationships) recursively. <br>
     * To prevent infinite recursion and duplicate creations during traversal:
     * </p>
     * <ul>
     *     <li>{@code caches} stores already created instances.
     *       If a field create call is invoked for a class that has already been created,
     *       the cached instance will be reused instead of creating a new one.</li>
     *     <li>{@code visited} keeps track of visited entity paths.</li>
     * </ul>
     *
     * @param clazz   the root entity class to instantiate
     * @param caches  cache map used to store already created instances
     * @param visited set of visited paths to prevent infinite recursion
     * @param <T>     the type of the entity
     * @return a fully populated entity instance created using the appropriate ORM
     * @throws GeneratorException if no ORM resolver is available
     */
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        if(ormResolver.isEmpty()) {
            throw new GeneratorException("ORM resolver is null");
        }
        return ormResolver.get(detectORM(clazz)).create(clazz, caches, visited);
    }

    private <T> ORMType detectORM(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            return ORMType.HIBERNATE;
        }
        return ORMType.MYBATIS;
    }

    /**
     * Creates an entity instance using the explicitly specified {@link ORMType}. <br>
     * Unlike {@link #create(Class, GenerateType)}, this method doesn't attempt to auto-detect thr ORM from the entity class.
     * Instead, the caller must provide the target ORMType directly.
     *
     * <p>
     *     The rest is the same as {@link #create(Class, GenerateType)}.
     * </p>
     *
     * @param ormType      the ORM type to use (e.g., {@link ORMType})
     * @param clazz        the entity class to instantiate
     * @param generateType the generation strategy to apply (e.g., SINGLE, CHILD, PARENT, ALL)
     * @param <T>          the type of the entity
     * @return a new entity instance created using the specified ORM
     * @throws GeneratorException if the given ORM type has no registered resolver
     */
    public <T> T create(ORMType ormType, Class<T> clazz, GenerateType generateType) {
        return ormResolver.get(ormType).create(clazz, generateType);
    }

    /**
     * Creates an entity instance using the generate strategy {@code GenerateType.ALL}. <br>
     * Unlike {@link #create(Class, Map, Set)}, this method doesn't attempt to auto-detect thr ORM from the entity class.
     * Instead, the caller must provide the target ORMType directly.
     *
     * <p>
     *     The rest is the same as {@link #create(Class, Map, Set)}.
     * </p>
     *
     * @param ormType the ORM type to use (e.g., {@link ORMType})
     * @param clazz   the root entity class to instantiate
     * @param caches  cache map used to store already created instances
     * @param visited set of visited paths to prevent infinite recursion
     * @param <T>     the type of the entity
     * @return a fully populated entity instance created using the appropriate ORM
     * @throws GeneratorException if the given ORM type has no registered resolver
     */
    public <T> T create(ORMType ormType, Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        return  ormResolver.get(ormType).create(clazz, caches, visited);
    }

    /**
     * Resolves and creates a map of ORM resolvers based on configuration and available dependencies.
     * This method determines which ORM frameworks to use by following this priority order:
     * <ol>
     *   <li>If multiple ORM types are explicitly configured in application.yaml, creates resolvers for all specified types</li>
     *   <li>If a single ORM type is explicitly configured, creates a resolver for that type only</li>
     *   <li>If no configuration is provided, auto-detects available ORM frameworks by scanning for beans</li>
     * </ol>
     *
     * <p><b>Configuration Example (application.yaml):</b></p>
     * <pre>
     * entity-instantiator:
     *   orm-type:
     *      type: [MYBATIS, HIBERNATE]  # Explicitly specify ORM types
     * </pre>
     *
     * <p><b>Auto-detection:</b></p>
     * When no configuration is provided, the method scans the Spring context for:
     * <ul>
     *   <li>MyBatis: checks for bean named {@value #MYBATIS_BEAN_NAME}</li>
     *   <li>Hibernate: checks for bean named {@value #HIBERNATE_BEAN_NAME}</li>
     * </ul>
     *
     * @param ormProperties      the ORM configuration properties from application.yaml
     * @param ormResolvers  the list of available {@link ORMResolver} implementations
     * @return a map of ORM types to their corresponding resolvers
     * @throws GeneratorException if no ORM dependencies are found in the classpath
     */
    public Map<ORMType, ORMResolver> resolver(ORMProperties ormProperties, List<ORMResolver> ormResolvers) {
        boolean hasMyBatis = beanFactory.containsBean(MYBATIS_BEAN_NAME);
        boolean hasHibernate = beanFactory.containsBean(HIBERNATE_BEAN_NAME);

        // no dependencies of ORM
        if (!hasMyBatis && !hasHibernate) {
            throw new GeneratorException("ORM isn't exists");
        }

        // List<ORMResolver> -> Map<ORMType, ORMResolver>
        Map<ORMType, ORMResolver> resolverMap = ormResolvers.stream()
                .collect(Collectors.toMap(this::resolveType, resolver -> resolver));


        // set ormProperties
        List<ORMType> ormTypes = ormProperties.getOrmType();
        if (ormTypes != null && ormTypes.size() > 1) {
            return ormTypes.stream()
                    .collect(Collectors.toMap(
                            type -> type, resolverMap::get));
        } else if(ormTypes != null && ormTypes.size() == 1) {
            return switch (ormTypes.get(0)) {
                case MYBATIS -> Map.of(ORMType.MYBATIS, resolverMap.get(ORMType.MYBATIS));
                case HIBERNATE -> Map.of(ORMType.HIBERNATE, resolverMap.get(ORMType.HIBERNATE));
            };
        }

        // 변수 설정 X
        if(hasMyBatis && hasHibernate) {
            return Map.of(ORMType.MYBATIS, resolverMap.get(ORMType.MYBATIS), ORMType.HIBERNATE, resolverMap.get(ORMType.HIBERNATE));
        } else {
            return hasMyBatis ?  Map.of(ORMType.MYBATIS, resolverMap.get(ORMType.MYBATIS)) : Map.of(ORMType.HIBERNATE, resolverMap.get(ORMType.HIBERNATE));
        }
    }

    private ORMType resolveType(ORMResolver resolver) {
        if (resolver instanceof HibernateCreator) return ORMType.HIBERNATE;
        if (resolver instanceof MyBatisCreator) return ORMType.MYBATIS;
        throw new IllegalArgumentException("Unsupported ORMResolver: " + resolver);
    }
}
