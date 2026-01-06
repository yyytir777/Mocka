package mocka.orm.generator;

import jakarta.persistence.Entity;
import mocka.core.GenerateType;
import mocka.core.exception.GeneratorException;
import mocka.core.VisitedPath;
import mocka.orm.generator.hibernate.HibernateCreator;
import mocka.orm.generator.mybatis.MyBatisCreator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * {@code ORMSelector} is a facade component responsible for selecting
 * the appropriate {@link ORMCreator} and delegating entity creation to it.
 *
 * <p>
 * It supports multiple ORM frameworks (currently Hibernate and MyBatis) and
 * determines which {@link ORMCreator} to use either:
 * </p>
 * <ul>
 *   <li>automatically, based on entity annotations</li>
 *   <li>explicitly, based on a provided {@link ORMType}</li>
 * </ul>
 *
 * <p>
 * This class does not create entities itself.
 * It only coordinates and delegates creation requests to the resolved {@link ORMCreator}.
 * </p>
 */
@Component
public class ORMResolver {

    /**
     * Spring {@link BeanFactory} used to detect the presence of ORM-related beans.
     */
    private final BeanFactory beanFactory;

    /**
     * A registry mapping {@link ORMType} to the corresponding {@link ORMCreator}.
     */
    private Map<ORMType, ORMCreator> ormCreators = new HashMap<>();

    private static final String MYBATIS_BEAN_NAME = "sqlSessionFactory";
    private static final String HIBERNATE_BEAN_NAME = "entityManagerFactory";

    public ORMResolver(BeanFactory beanFactory, ORMProperties ormProperties, List<ORMCreator> ormCreators) {
        this.beanFactory = beanFactory;
        this.ormCreators = resolver(ormProperties, ormCreators);
    }

    /**
     * Returns all registered {@link ORMCreator} instances.
     *
     * @return a list of available ORM creators
     * @throws GeneratorException if no ORM creators are registered
     */
    public List<ORMCreator> getCreators() {
        if (this.ormCreators.isEmpty()) {
            throw new GeneratorException("ORM creator is null");
        }
        return new ArrayList<>(ormCreators.values());
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
     * @param generateType the generation strategy (excluding {@code GenerateType.ALL})
     * @param <T>          the entity type
     * @return a new entity instance created using the appropriate ORM
     * @throws GeneratorException if no ORM resolver is available
     */
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(ormCreators.isEmpty()) {
            throw new GeneratorException("ORM creator is null");
        }
        return ormCreators.get(detectORM(clazz)).create(clazz, generateType);
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
     * @param <T>     the entity type
     * @return a fully populated entity instance created using the appropriate ORM
     * @throws GeneratorException if no ORM resolver is available
     */
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        if(ormCreators.isEmpty()) {
            throw new GeneratorException("ORM creator is null");
        }
        return ormCreators.get(detectORM(clazz)).create(clazz, caches, visited);
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
        return ormCreators.get(ormType).create(clazz, generateType);
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
        return  ormCreators.get(ormType).create(clazz, caches, visited);
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
     * mocka:
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
     * @param ormCreators  the list of available {@link ORMCreator} implementations
     * @return a map of ORM types to their corresponding resolvers
     * @throws GeneratorException if no ORM dependencies are found in the classpath
     */
    public Map<ORMType, ORMCreator> resolver(ORMProperties ormProperties, List<ORMCreator> ormCreators) {
        boolean hasMyBatis = beanFactory.containsBean(MYBATIS_BEAN_NAME);
        boolean hasHibernate = beanFactory.containsBean(HIBERNATE_BEAN_NAME);

        // no dependencies of ORM
        if (!hasMyBatis && !hasHibernate) {
            throw new GeneratorException("ORM isn't exists");
        }

        // List<ORMResolver> -> Map<ORMType, ORMResolver>
        Map<ORMType, ORMCreator> creatorMap = ormCreators.stream()
                .collect(Collectors.toMap(this::resolveType, creator -> creator));


        // set ormProperties
        List<ORMType> ormTypes = ormProperties.getOrmType();
        if (ormTypes != null && ormTypes.size() > 1) {
            return ormTypes.stream()
                    .collect(Collectors.toMap(
                            type -> type, creatorMap::get));
        } else if(ormTypes != null && ormTypes.size() == 1) {
            return switch (ormTypes.get(0)) {
                case MYBATIS -> Map.of(ORMType.MYBATIS, creatorMap.get(ORMType.MYBATIS));
                case HIBERNATE -> Map.of(ORMType.HIBERNATE, creatorMap.get(ORMType.HIBERNATE));
            };
        }

        // 변수 설정 X
        if(hasMyBatis && hasHibernate) {
            return Map.of(ORMType.MYBATIS, creatorMap.get(ORMType.MYBATIS), ORMType.HIBERNATE, creatorMap.get(ORMType.HIBERNATE));
        } else {
            return hasMyBatis ?  Map.of(ORMType.MYBATIS, creatorMap.get(ORMType.MYBATIS)) : Map.of(ORMType.HIBERNATE, creatorMap.get(ORMType.HIBERNATE));
        }
    }

    private ORMType resolveType(ORMCreator creator) {
        if (creator instanceof HibernateCreator) return ORMType.HIBERNATE;
        if (creator instanceof MyBatisCreator) return ORMType.MYBATIS;
        throw new IllegalArgumentException("Unsupported ORMCreator: " + creator);
    }
}
