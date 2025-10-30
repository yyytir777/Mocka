package mocka.generator.orm.hibernate;

import mocka.annotation.RegexSource;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mocka.annotation.ValueSource;
import mocka.generator.*;
import mocka.generator.orm.*;
import mocka.generator.orm.association.AssociationMatcherFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Creates and populates Hibernate entity instances with generated data.
 *
 * This creator handles Hibernate-specific entity creation including:
 * <ul>
 *   <li>Basic field value generation using {@link HibernateFieldValueGenerator}</li>
 *   <li>JPA association handling (OneToMany, ManyToOne, OneToOne, ManyToMany)</li>
 *   <li>Circular reference prevention through visited path tracking</li>
 * </ul>
 *
 * <p><b>Collection Size Configuration:</b></p>
 * The number of elements generated for collection associations (OneToMany, ManyToMany)
 * is configured through {@link ORMProperties#getAssociationSize()}.
 *
 * @see ORMResolver
 * @see AbstractCreator
 * @see HibernateFieldValueGenerator
 * @see AssociationMatcherFactory
 */
@Component
public class  HibernateCreator extends AbstractCreator implements ORMResolver {

    private final ORMLoader hibernateLoader;
    private final FieldValueGenerator fieldValueGenerator;

    /**
     * The number of elements to generate for collection associations (OneToMany, ManyToMany).
     * Configured via {@link ORMProperties#getAssociationSize()}.
     */
    private Integer ASSOCIATION_SIZE;

    public HibernateCreator(HibernateLoader hibernateLoader, HibernateFieldValueGenerator fieldValueGenerator, ORMProperties ormProperties, FileSourceCreator fileSourceCreator) {
        super(fileSourceCreator);
        this.hibernateLoader = hibernateLoader;
        this.fieldValueGenerator = fieldValueGenerator;
        this.ASSOCIATION_SIZE =  ormProperties.getAssociationSize();
    }

    private static final Set<Class<? extends Annotation>> ASSOCIATIONS = Set.of(
            OneToMany.class, ManyToOne.class, OneToOne.class, ManyToMany.class
    );

    /**
     * Creates an entity instance with the specified generation strategy. (except {@code GenerateType.ALL}
     *
     * @param clazz         the entity class to instantiate
     * @param generateType  the generation strategy determining which associations to populate
     * @param <T>           the type of the entity
     * @return a new entity instance with populated fields
     * @throws RuntimeException if entity instantiation or field access fails
     * @see GenerateType
     * @see AssociationMatcherFactory#support(Field, GenerateType, ORMType, mocka.generator.orm.mybatis.AssociationType)
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(generateType == null) return null;

        GenerateType nextGenerateType = generateType.next();

        try {
            T instance = initInstance(clazz);

            // iter Fields of the given class
            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value;

                if(field.get(instance) != null) continue;

                if(isAssociations(field)) {
                    if(!AssociationMatcherFactory.support(field, generateType, ORMType.HIBERNATE, null)) continue;

                    if(Collection.class.isAssignableFrom(field.getType())) {
                        value = new ArrayList<>();
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(getGenericType(field), nextGenerateType);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else {
                        Object parent = create(field.getType(), nextGenerateType);
                        addChildToParent(parent, instance); // child -> parent로 갈 때 parent의 children에 child 추가 (컬렉션에 값을 추가해야함)
                        value = parent;
                    }
                } else {
                    value = generateValue(field);
                }
                field.set(instance, value);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates an entity instance with all associated entity. ({@code GenerateType.ALL})
     *
     * @param clazz   the entity class to instantiate (starting point)
     * @param caches  a cache map storing created instances by class name
     * @param visited a set tracking visited relationship paths to prevent circular references
     * @param <T>     the type of the entity
     * @return a new entity instance with the entire relationship graph populated
     * @throws RuntimeException if entity instantiation or field access fails
     * @see VisitedPath
     * @see GenerateType#ALL
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        GenerateType generateType = GenerateType.ALL;
        String className = clazz.getSimpleName();
        if(caches.containsKey(className)) {
            return (T) caches.get(className);
        }

        try {
            T instance = initInstance(clazz);
            caches.put(clazz.getSimpleName(), instance);

            for(Field field : clazz.getDeclaredFields()) {

                field.setAccessible(true);
                Object value;

                if(isAssociations(field)) {
                    VisitedPath path = VisitedPath.of(clazz, Collection.class.isAssignableFrom(field.getType()) ? getGenericType(field) : field.getType());

                    // 연관관계 애노테이션과 필드 타입이 맞지 않을 경우 continue
                    if(!AssociationMatcherFactory.support(field, generateType, ORMType.HIBERNATE, null)) continue;

                    // 이미 방문한 필드라면 continue
                    if(visited.contains(path)) continue;
                    visited.add(path);

                    try {
                        if(Collection.class.isAssignableFrom(field.getType())) {
                            value = new ArrayList<>();
                            for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                                Object child = create(getGenericType(field), new HashMap<>(), visited);
                                addParentToChild(child, instance);
                                ((List<Object>)value).add(child);
                            }
                        } else {
                            Object parent = create(field.getType(), caches, visited);
                            addChildToParent(parent, instance);
                            value = parent;
                        }
                    } finally {
                        visited.remove(path);
                    }
                } else {
                    value = generateValue(field);
                }
                field.set(instance, value);
            }
            return instance;
        } catch(InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a child entity to a parent's collection field.
     */
    @SuppressWarnings("unchecked")
    private void addChildToParent(Object parent, Object child) throws IllegalAccessException {
        for (Field field : parent.getClass().getDeclaredFields()) {
            if (!isAssociations(field)) continue;

            // 컬렉션 필드 확인
            if (Collection.class.isAssignableFrom(field.getType())) {
                Class<?> genericType = getGenericType(field);
                // 컬렉션의 제네릭 타입과 같은지 확인
                if (genericType != null && genericType.isAssignableFrom(child.getClass())) {
                    field.setAccessible(true);

                    Collection<Object> collection = (Collection<Object>) field.get(parent);
                    if (collection == null) {
                        collection = new ArrayList<>();
                        field.set(parent, collection);
                    }

                    collection.add(child);
                }
            }
        }
    }

    /**
     * Sets a parent reference in a child entity's field.
     */
    private void addParentToChild(Object child, Object parent) throws IllegalAccessException {
        for (Field childField : child.getClass().getDeclaredFields()) {
            if (childField.getType().equals(parent.getClass()) && isAssociations(childField)) {
                childField.setAccessible(true);
                childField.set(child, parent);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Class<?> getGenericType(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return (Class<Object>) genericType.getActualTypeArguments()[0];
    }

    private boolean isAssociations(Field field) {
        return ASSOCIATIONS.stream().anyMatch(field::isAnnotationPresent);
    }

    /**
     * Generates a value for a non-association field.
     */
    @SuppressWarnings("unchecked")
    private <T> T generateValue(Field field) {
        if (field.isAnnotationPresent(ValueSource.class)) {
            return handleValueSource(field);
        } else if(field.getType().equals(String.class) && field.isAnnotationPresent(RegexSource.class)) {
            return (T) handleRegexSource(field);
        }
        return (T) fieldValueGenerator.get(field);
    }


    /**
     * Loads all Hibernate entity classes from the application context.
     * @return a set of all discovered Hibernate entity classes
     * @see HibernateLoader#load()
     */
    @Override
    public Set<Class<?>> load() {
        return hibernateLoader.load();
    }

    public void setAssociationSize(int size) {
        this.ASSOCIATION_SIZE = size;
    }
}
