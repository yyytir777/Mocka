package mocka.generator.orm.mybatis;

import mocka.annotation.ValueSource;
import mocka.generator.FileSourceCreator;
import mocka.generator.GenerateType;
import mocka.generator.orm.*;
import mocka.generator.orm.hibernate.HibernateLoader;
import mocka.generator.orm.hibernate.VisitedPath;
import mocka.generator.orm.hibernate.association.AssociationMatcherFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * Creates and populates MyBatis entity instances with generated data.
 * This creator handles MyBatis-specific entity creation including:
 * <ul>
 *   <li>Basic field value generation using {@link MyBatisFieldValueGenerator}</li>
 *   <li>xml association handling</li>
 *   <li>Circular reference prevention through visited path tracking</li>
 * </ul>
 *
 * <p><b>Collection Size Configuration:</b></p>
 * The number of elements generated for collection associations
 * is configured through {@link ORMProperties#getAssociationSize()}.
 *
 * @see ORMResolver
 * @see AbstractCreator
 * @see MyBatisFieldValueGenerator
 * @see AssociationMatcherFactory
 */
@Component
public class MyBatisCreator extends AbstractCreator implements ORMResolver {

    private final ORMLoader myBatisLoader;
    private final FieldValueGenerator fieldValueGenerator;
    private final MyBatisMetadata myBatisMetadata;

    /**
     * The number of elements to generate for collection associations (one-to-many, many-to-many).
     * Configured via {@link ORMProperties#getAssociationSize()}.
     */
    private final Integer ASSOCIATION_SIZE;

    public MyBatisCreator(MyBatisLoader myBatisLoader, MyBatisFieldValueGenerator fieldValueGenerator, MyBatisMetadata myBatisMetadata, ORMProperties ormProperties, FileSourceCreator fileSourceCreator) {
        super(fileSourceCreator);
        this.myBatisLoader = myBatisLoader;
        this.fieldValueGenerator = fieldValueGenerator;
        this.myBatisMetadata = myBatisMetadata;
        this.ASSOCIATION_SIZE = ormProperties.getAssociationSize();
    }


    /**
     * Creates an entity instance with the specified generation strategy. (except {@code GenerateType.ALL}
     *
     * @param clazz         the entity class to instantiate
     * @param generateType  the generation strategy determining which associations to populate
     * @param <T>           the type of the entity
     * @return a new entity instance with populated fields
     * @throws RuntimeException if entity instantiation or field access fails
     * @see GenerateType
     * @see AssociationMatcherFactory#support(Field, GenerateType, ORMType, AssociationType associationType)
     */
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if (generateType == null) return null;

        GenerateType nextGenerateType = generateType.next();

        try {
            T instance = initInstance(clazz);

            List<PropertyField> fields = myBatisMetadata.getFields(clazz);
            // iter Fields of the given class
            for(PropertyField field : fields) {
                field.getField().setAccessible(true);
                Object value;

                if(field.getField().get(instance) != null) continue;
                if (field.isId()) continue;

                Class<?> targetType = resolveFieldType(field.getField());
                AssociationType associationType = isAssociations(clazz, targetType);
                if (associationType != null) {
                    if (generateType == GenerateType.SELF) continue;
                    if(!AssociationMatcherFactory.support(field.getField(), generateType, ORMType.MYBATIS, associationType)) continue;

                    // collection
                    if(associationType == AssociationType.ONE_TO_MANY || associationType == AssociationType.MANY_TO_MANY) {
                        value = new ArrayList<>();
                        Class<?> fieldGenericType = (Class<Object>) ((ParameterizedType) field.getField().getGenericType()).getActualTypeArguments()[0];
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(fieldGenericType, nextGenerateType);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else { // association
                        Object parent = create(field.getField().getType(), nextGenerateType);
                        addChildToParent(parent, instance);
                        value = parent;
                    }
                } else {
                    value = generateValue(field);
                }
                field.getField().set(instance, value);
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

            // 클래스 순회
            List<PropertyField> fields = myBatisMetadata.getFields(clazz);
            // MyBatis 순회 때 사용할 PropertyField (Field를 wrapper한 클래스)
            for(PropertyField field : fields) {
                field.getField().setAccessible(true);
                Object value;

                // 해당 필드가 id이면 continue -> id는 자동 증가로 설정
                if (field.isId()) continue;

                //연관관계 있을 때
                Class<?> targetType = resolveFieldType(field.getField());
                AssociationType associationType = isAssociations(clazz, targetType);
                if (associationType != null) {
                    VisitedPath path = VisitedPath.of(clazz, Collection.class.isAssignableFrom(field.getField().getType()) ? getGenericType(field.getField()) : field.getField().getType());

                    // collection
                    if(!AssociationMatcherFactory.support(field.getField(), generateType, ORMType.MYBATIS, associationType)) continue;

                    // 이미 방문 한 필드라면 continue
                    if(visited.contains(path)) continue;
                    visited.add(path);


                    if(associationType == AssociationType.ONE_TO_MANY || associationType == AssociationType.MANY_TO_MANY) {
                        value = new ArrayList<>();
                        Class<?> fieldGenericType = (Class<Object>) ((ParameterizedType) field.getField().getGenericType()).getActualTypeArguments()[0];
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(fieldGenericType, new HashMap<>(), visited);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else { // association
                        Object parent = create(field.getField().getType(), caches, visited);
                        addChildToParent(parent, instance);
                        value = parent;
                    }
                } else { // 일반 필드일 때
                    value = generateValue(field);
                }
                field.getField().set(instance, value);
            }
            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a child entity to a parent's collection field.
     */
    @SuppressWarnings("unchecked")
    private void addChildToParent(Object parent, Object child) throws IllegalAccessException {

        for (Field field : parent.getClass().getDeclaredFields()) {
            if (field.getType().isAssignableFrom(child.getClass())) {
                field.setAccessible(true);
                field.set(parent, child);
                continue;
            }

            // 컬렉션이면,
            if (Collection.class.isAssignableFrom(field.getType())) {
                Class<?> genericType = resolveFieldType(field);
                // 컬렉션의 제네릭 타입고 같은지 확인
                if (genericType != null && genericType.isAssignableFrom(child.getClass())) {
                    field.setAccessible(true);

                    Collection<Object> collection = (Collection<Object>) field.get(parent);
                    if(collection == null) {
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
            if (childField.getType().isAssignableFrom(parent.getClass())) {
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

    /**
     * Generates a value for a non-association field.
     */
    @SuppressWarnings("unchecked")
    private <T> T generateValue(PropertyField field) {
        // @ValueSource 애노테이션이 있으면 해당 파일 경로 key에 대한 generator가 있는지 체크
        if (field.getField().isAnnotationPresent(ValueSource.class)) {
            return handleValueSource(field.getField());
        }

        return (T) fieldValueGenerator.get(field.getField());
    }

    // clazz -> field의 연관관계가 존재하는지
    private AssociationType isAssociations(Class<?> clazz, Class<?> target) {
        return myBatisMetadata.getAssociation(clazz, target);
    }

    @SuppressWarnings("unchecked")
    private Class<?> resolveFieldType(Field field) {
        Class<?> type = field.getType();

        if (!Collection.class.isAssignableFrom(type)) {
            return type;
        } else {
            ParameterizedType genericType = (ParameterizedType) field.getGenericType();
            return (Class<Object>) genericType.getActualTypeArguments()[0];
        }
    }

    /**
     * Loads all Hibernate entity classes from the application context.
     * @return a set of all discovered Hibernate entity classes
     * @see HibernateLoader#load()
     */
    @Override
    public Set<Class<?>> load() {
        return myBatisLoader.load();
    }
}
