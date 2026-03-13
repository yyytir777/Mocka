package mocka.odm.generator.mongodb;

import mocka.core.GenerateType;
import mocka.core.VisitedPath;
import mocka.core.annotation.RegexSource;
import mocka.core.annotation.ValueSource;
import mocka.core.generator.FieldValueGenerator;
import mocka.odm.generator.AbstractODMCreator;
import mocka.odm.generator.ODMCreator;
import mocka.odm.generator.ODMLoader;
import mocka.odm.generator.ODMProperties;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

@Component
public class MongodbCreator extends AbstractODMCreator implements ODMCreator {

    private final ODMLoader mongodbLoader;
    private final FieldValueGenerator fieldValueGenerator;

    /**
     * The number of elements to generate for collection associations (OneToMany, ManyToMany).
     * Configured via {@link ODMProperties#getAssociationSize()}.
     */
    private Integer ASSOCIATION_SIZE;

    public MongodbCreator(MongodbLoader mongodbLoader,
                          MongodbFieldValueGenerator fieldValueGenerator,
                          ODMProperties odmProperties) {
        this.mongodbLoader = mongodbLoader;
        this.fieldValueGenerator = fieldValueGenerator;
        this.ASSOCIATION_SIZE = odmProperties.getAssociationSize();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        if(generateType == null) return null;

        GenerateType nextGenerateType = generateType.next();

        try {
            T instance = initInstance(clazz);

            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                Object value;

                if(field.get(instance) != null) continue;

                // 연관관계 필드일 때 (@DBRef 애노테이션으로 구분하기..?)
                if(field.isAnnotationPresent(DBRef.class)) {

                    // 해당 타입이 List같은 Collection일 때 & GenerateType == PARENT || PARENTS 이어야 함 -> ASSOCIATION 개수 만큼 생성하여 넣음
                    if(Collection.class.isAssignableFrom(field.getType())) {
                        value = new ArrayList<>();
                        for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                            Object child = create(getGenericType(field), nextGenerateType);
                            // 두 객체간 양방향 연관관계는 생성하지 않기 (NoSQL 특성)
                            ((List<Object>)value).add(child);
                        }
                    } else {
                        value = create(field.getType(), nextGenerateType);
                    }
                } else {
                    value = generateValue(field);
                }
                field.set(instance, value);
            }
            return instance;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
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

                if(field.get(instance) != null) continue;

                if(field.isAnnotationPresent(DBRef.class)) {
                    VisitedPath path = VisitedPath.of(clazz, Collection.class.isAssignableFrom(field.getType()) ? getGenericType(field) : field.getType());

                    if(visited.contains(path)) continue;
                    visited.add(path);

                    try {
                        if(Collection.class.isAssignableFrom(field.getType())) {
                            value = new ArrayList<>();
                            for(int i = 0; i < ASSOCIATION_SIZE; i++) {
                                Object child = create(getGenericType(field), new HashMap<>(), visited);
                                ((List<Object>)value).add(child);
                            }
                        } else {
                            value = create(field.getType(), caches, visited);

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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<Object> getGenericType(Field field) {
        ParameterizedType genericType = (ParameterizedType) field.getGenericType();
        return (Class<Object>)  genericType.getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    private <T> T generateValue(Field field) {
        if(field.isAnnotationPresent(ValueSource.class)) {
            return handleValueSource(field);
        } else if(field.getType().equals(String.class) && field.isAnnotationPresent(RegexSource.class)) {
            return (T) handleRegexSource(field);
        }
        return (T) fieldValueGenerator.get(field);
    }

    @Override
    public Set<Class<?>> load() {
        return mongodbLoader.load();
    }

    public void setAssociationSize(Integer size) {
        this.ASSOCIATION_SIZE = size;
    }
}
