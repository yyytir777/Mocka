package jodag.generator;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jodag.ValueSource;
import jodag.generator.association.AssociationMatcherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * `@Generate` 애노테이션을 등록한 엔티티에 한해 인스턴스를 생성하는 클래스입니다.
 * `FieldValueGenerator`에 의존하고 있으며, 주어진 클래스에 대한 generator를 넘기므로 싱글톤으로 관리하고 있습니다.
 * @see FieldValueGenerator
 */
public class EntityInstanceCreator {

    private static final EntityInstanceCreator INSTANCE = new EntityInstanceCreator(FieldValueGenerator.getInstance());

    private static final Logger log = LoggerFactory.getLogger(EntityInstanceCreator.class);

    private final FieldValueGenerator fieldValueGenerator;

    private static final Set<Class<? extends Annotation>> ASSOCIATIONS = Set.of(
            OneToMany.class, ManyToOne.class, OneToOne.class, ManyToMany.class
    );

    public static EntityInstanceCreator getInstance() {
        return INSTANCE;
    }

    private EntityInstanceCreator(FieldValueGenerator fieldValueGenerator) {
        this.fieldValueGenerator = fieldValueGenerator;
    }

    /**
     * 주어진 클래스 타입에 대한 `EntityGenerator`를 생성합니다.
     * @param clazz 인스턴스를 생성할 클래스
     * @param <T> 인스턴스 클래스의 타입
     * @return 주어진 타입에 대한 `EntityGenerator` 인스턴스
     */
    public <T> T createInstance(Class<T> clazz, GenerateType generateType) {
        if(generateType.equals(GenerateType.ALL)) {
            return create(clazz, new HashMap<>(), new HashSet<>());
        }
        return create(clazz, generateType);
    }

    /**
     * 엔티티의 타입, 캐시 정보를 통해 해당 엔티티의 인스턴스를 생성합니다. <br>
     * 1. OneToMany, ManyToMany 연관관계 일 때 <br>
     * - caches에 연관관계 엔티티가 존재하는
     * - generateType 조건 체크 (CHILD or CHILDREN 이어야 함) <br>
     *
     *
     * @param clazz 인스턴스를 생성할 클래스
     * @return 주어진 타입에 대한 인스턴스
     * @param <T> 인스턴스 클래스의 타입
     */
    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> clazz, GenerateType generateType) {
        if(generateType == null) return null;

        GenerateType nextGenerateType = generateType.next();

        try {
            // No Argument Constructor로 T 타입의 인스턴스 생성
            T instance = clazz.getDeclaredConstructor().newInstance();

            // 주어진 클래스의 Field 순회
            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value;

                if(isAssociations(field)) {
                    if(!AssociationMatcherFactory.support(field, generateType)) continue;

                    if(Collection.class.isAssignableFrom(field.getType())) {
                        value = new ArrayList<>();
                        for(int i = 0; i < 5; i++) {
                            Object child = create(getGenericType(field), nextGenerateType);
                            addParentToChild(child, instance);
                            ((List<Object>)value).add(child);
                        }
                    } else {
                        Object parent = create(field.getType(), nextGenerateType);
                        addChildToParent(parent, instance); // child -> parent로 갈 때 parent의 children에 child 추가 (컬렉션에 값을 추가해야함)
                        value = parent;
                    }
                } else { // 연관관계가 아닌 일반 필드 생성
                    // 한 필드에 대한 애노테이션 메타데이터
                    // 필드클래스와 애노테이션을 분석하여 해당 필드를 랜덤으로 채움 -> 값이 없는 경우도 존재
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
     * GenerateType.ALL인 전략에서 사용하는 메서드입니다. <br>
     * 주어진 clazz를 시작으로 연결된 모든 엔티티를 생성합니다.
     * @param clazz 생성하고자 하는 클래스 (시작점)
     * @param caches 생성한 클래스의 인스턴스를 담는 캐시
     * @return clazz의 인스턴스
     */
    @SuppressWarnings("unchecked")
    private <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        GenerateType generateType = GenerateType.ALL;
        String className = clazz.getSimpleName();
        if(caches.containsKey(className)) {
            return (T) caches.get(className);
        }

        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            caches.put(clazz.getSimpleName(), instance);

            for(Field field : clazz.getDeclaredFields()) {

                field.setAccessible(true);
                Object value;

                if(isAssociations(field)) {
                    VisitedPath path = VisitedPath.of(clazz, Collection.class.isAssignableFrom(field.getType()) ? getGenericType(field) : field.getType());

                    // 연관관계 애노테이션과 필드 타입이 맞지 않을 경우 continue
                    if(!AssociationMatcherFactory.support(field, generateType)) continue;

                    // 이미 방문한 필드라면 continue
                    if(visited.contains(path)) continue;
                    visited.add(path);

                    try {
                        if(Collection.class.isAssignableFrom(field.getType())) {
                            value = new ArrayList<>();
                            for(int i = 0; i < 5; i++) {
                                Object child = create(getGenericType(field), new HashMap<>(), visited);
                                addParentToChild(child, instance);
//                                System.out.printf("↳ %s → %s%n", child.getClass().getSimpleName(), instance.getClass().getSimpleName());
                                ((List<Object>)value).add(child);
                            }
                        } else {
                            Object parent = create(field.getType(), caches, visited);
                            addChildToParent(parent, instance);
//                            System.out.printf("↳ %s → %s%n", instance.getClass().getSimpleName(), parent.getClass().getSimpleName());
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
     * child를 parent의 컬렉션에 추가
     * @param parent parent
     * @param child child
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
     * parent를 child의 필드에 추가
     * @param child child
     * @param parent parent
     * @throws IllegalAccessException 필드에 값을 넣을 수 없을 때 throw
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
     * 클래스의 필드 값을 분석하여 해당 필드의 랜덤 값을 리턴함
     */
    @SuppressWarnings("unchecked")
    private <T> T generateValue(Field field) {
        try {
            // @ValueSource 애노테이션이 있으면 해당 파일 경로 key에 대한 generator가 있는지 체크
            if(field.isAnnotationPresent(ValueSource.class)) {
                String path =  field.getAnnotation(ValueSource.class).path();
                Class<?> clazz = field.getAnnotation(ValueSource.class).type();
                Generator<?> registableGenerator = GeneratorFactory.getRegistableGenerator(path, path, clazz);
                return (T) registableGenerator.get();
            }
        } catch (Exception e) {
            log.warn("Unable to generate value from file... ", e);
        }

        return (T) fieldValueGenerator.get(field);
    }
}
