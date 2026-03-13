package mocka.odm.generator.mongodb;

import mocka.core.generator.FieldValueGenerator;
import mocka.core.generator.factory.GeneratorFactory;
import mocka.core.random.RandomProvider;
import mocka.odm.generator.factory.BsonGenerator;
import mocka.odm.generator.factory.ODMGeneratorFactory;
import mocka.odm.generator.factory.GeoGenerator;
import mocka.odm.generator.factory.GeoJsonGenerator;
import org.bson.types.*;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.geo.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.springframework.util.ClassUtils.isPrimitiveOrWrapper;

@Component
public class MongodbFieldValueGenerator implements FieldValueGenerator {

    private final RandomProvider randomProvider;
    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final ODMGeneratorFactory odmGeneratorFactory = new ODMGeneratorFactory();

    public MongodbFieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();
    }

    private final List<Predicate<Field>> notGenerableConditions = List.of(
            field -> field.isAnnotationPresent(Transient.class)
    );

    private final Map<Class<?>, Supplier<?>> primitiveGeneratorMap = Map.ofEntries(
            Map.entry(int.class, () -> generatorFactory.asInteger().get()),
            Map.entry(Integer.class, () ->  generatorFactory.asInteger().get()),
            Map.entry(long.class, () -> generatorFactory.asLong().get()),
            Map.entry(Long.class, () -> generatorFactory.asLong().get()),
            Map.entry(float.class, () -> generatorFactory.asFloat().get()),
            Map.entry(Float.class, () -> generatorFactory.asFloat().get()),
            Map.entry(double.class, () -> generatorFactory.asDouble().get()),
            Map.entry(Double.class, () -> generatorFactory.asDouble().get()),
            Map.entry(boolean.class, () -> generatorFactory.asBoolean().get()),
            Map.entry(Boolean.class, () -> generatorFactory.asBoolean().get()),
            Map.entry(char.class, () -> generatorFactory.asCharacter().get()),
            Map.entry(Character.class, () -> generatorFactory.asCharacter().get()),
            Map.entry(String.class, () -> generatorFactory.asString().get())
    );


    /**
     * <a href="https://docs.spring.io/spring-data/mongodb/reference/mongodb/mapping/mapping-schema.html#mongo.jsonSchema.types">supported json types</a>
     * @param field
     * @return Object
     */
    @Override
    public Object get(Field field) {
        Class<?> type = field.getType();

        // no need to generate
        if(isNotGenerable(field)) {
            return null;
        }

        // string
        if(field.getGenericType().equals(String.class)) {
            return generatorFactory.asString().getUpTo(20);
        }

        // primitive
        if(isPrimitiveOrWrapper(type)) {
            Supplier<?> generator = primitiveGeneratorMap.get(type);
            if(generator != null ) return generator.get();
        }

        // enum
        if(type.isEnum()) {
            Object[] enumConstants = type.getEnumConstants();
            if(enumConstants.length == 0) return null;
            return enumConstants[randomProvider.getInt(enumConstants.length)];
        }

        // array
        if (type.isArray() && (isPrimitiveOrWrapper(type.getComponentType()) || type.getComponentType().equals(String.class))) {
            Class<?> componentType = type.getComponentType();
            int size = randomProvider.getNextIdx(10);
            Object arrayInstance = Array.newInstance(componentType, size);

            try {
                Supplier<?> generator = primitiveGeneratorMap.get(componentType);
                for (int i = 0; i < size; i++) {
                    Array.set(arrayInstance, i, generator.get());
                }

            } catch (NullPointerException e) {
                throw new RuntimeException(e);
            }

            return arrayInstance;
        }

        // Collection
        if (Collection.class.isAssignableFrom(type)) {
            Type genericType = field.getGenericType();
            if (genericType instanceof ParameterizedType parameterizedType
                    && parameterizedType.getActualTypeArguments()[0] instanceof Class<?> element) {
                // type : List / element : ElementType
                return createCollections(type, element);
            }
        }

        // java.math
        if(type.equals(BigDecimal.class)) {
            return generatorFactory.asBigDecimal().get();
        } else if(type.equals(BigInteger.class)) {
            return generatorFactory.asBigInteger().get();
        }

        // java.util, java.time, java.sql
        if (type.getPackageName().startsWith("java.time")) {
            return generatorFactory.asDateTime().get(type);
        } else if (type.getPackageName().startsWith("java.sql")) {
            return generatorFactory.asSqlDate().get(type);
        } else if (type.getPackageName().startsWith("java.util")) {
            if (type.equals(UUID.class)) {
                return UUID.randomUUID();
            }
            return generatorFactory.asLegacyDate().get(type);
        }

        // MongoDB 특수 타입
        if(type.getPackageName().startsWith("org.bson")) {
            BsonGenerator bsonGenerator = odmGeneratorFactory.asBson();

            if(type.equals(ObjectId.class)) {
                return bsonGenerator.getObjectId();
            } else if(type.equals(Binary.class)) {
                return bsonGenerator.getBinary();
            } else if(type.equals(Decimal128.class)) {
                return bsonGenerator.getDecimal128();
            } else if(type.equals(BSONTimestamp.class)) {
                return bsonGenerator.getBsonTimestamp();
            } else {
                throw new UnsupportedOperationException("Unsupported type: " + type);
            }
        }

        if(type.getPackageName().startsWith("org.springframework.data.geo")) {
            GeoGenerator geoGenerator = odmGeneratorFactory.asGeo();

            if(type.equals(Point.class)) {
                return geoGenerator.getPoint();
            } else if(type.equals(Box.class)) {
                return geoGenerator.getBox();
            } else if(type.equals(Circle.class)) {
                return geoGenerator.getCircle();
            } else if(type.equals(Polygon.class)) {
                return geoGenerator.getPolygon();
            } else throw new UnsupportedOperationException("Unsupported type: " + type);
        }


        if(type.getPackageName().startsWith("org.springframework.data.mongodb.core.geo")) {
            GeoJsonGenerator geoJsonGenerator = odmGeneratorFactory.asGeoJson();

            if(type.equals(GeoJsonPoint.class)) {
                return geoJsonGenerator.getGeoJsonPoint();
            } else if(type.equals(GeoJsonPolygon.class)) {
                return geoJsonGenerator.getGeoJsonPolygon();
            } else if(type.equals(GeoJsonLineString.class)) {
                return geoJsonGenerator.getGeoJsonLineString();
            } else if(type.equals(GeoJsonMultiPoint.class)) {
                return geoJsonGenerator.getGeoJsonMultiPoint();
            } else if(type.equals(GeoJsonMultiPolygon.class)) {
                return geoJsonGenerator.getGeoJsonMultiPolygon();
            } else if(type.equals(GeoJsonMultiLineString.class)) {
                return geoJsonGenerator.getGeoJsonMultiLineString();
            } else throw new UnsupportedOperationException("Unsupported type: " + type);
        }

        if (isEmbedded(type)) {
            return createEmbeddableInstance(type);
        }

        return null;
    }

    private Boolean isEmbedded(Class<?> type) {
        if(type.equals(Object.class)) return false;

        if (type.isAnnotationPresent(Document.class)) {
            return false;
        }

        // 기본 타입, Wrapper, String 등은 embedded 아님
        if (isPrimitiveOrWrapper(type)) {
            return false;
        }

        // 배열은 embedded 아님
        if (type.isArray()) {
            return false;
        }

        // BSON 타입들은 embedded 아님
        if (isBsonType(type)) {
            return false;
        }

        // 컬렉션, 맵 등은 embedded 아님
        if (Collection.class.isAssignableFrom(type) || Map.class.isAssignableFrom(type)) {
            return false;
        }

        // 나머지는 embedded 객체
        return true;
    }

    private Object createEmbeddableInstance(Class<?> type) {
        try {
            Object instance = type.getDeclaredConstructor().newInstance();
            for (Field field : type.getDeclaredFields()) {
                field.setAccessible(true);
                field.set(instance, get(field));
            }

            return instance;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    // type : List, Set, Collection / element : elementType
    @SuppressWarnings("unchecked")
    private <T> Collection<T> createCollections(Class<?> type, Class<?> element) {
        int size = randomProvider.getNextIdx(10);
        Collection<T> collection;
        if (Set.class.isAssignableFrom(type)) {
            collection = new HashSet<>(size);
        } else { // List & Collection
            collection = new ArrayList<>(size);
        }

        Supplier<?> generator = primitiveGeneratorMap.get(element);

        for (int i = 0; i < size; i++) {
            collection.add((T) generator.get());
        }
        return collection;
    }

    private Boolean isBsonType(Class<?> type) {
        String packageName = type.getPackageName();
        return packageName.startsWith("org.bson")
                || packageName.startsWith("org.springframework.data.mongodb.core.geo");
    }

    private boolean hasAnnotation(Field field, Class<? extends Annotation> clazz) {
        return field.isAnnotationPresent(clazz);
    }

    private boolean isNotGenerable(Field field) {
        return notGenerableConditions.stream().anyMatch(p -> p.test(field));
    }
}
