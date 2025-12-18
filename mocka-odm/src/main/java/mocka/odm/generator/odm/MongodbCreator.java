package mocka.odm.generator.odm;

import mocka.core.GenerateType;
import mocka.core.VisitedPath;
import mocka.core.generator.FieldValueGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

@Component
public class MongodbCreator implements ODMCreator {

    private final ODMLoader mongodbLoader;
    private final FieldValueGenerator fieldValueGenerator;
    private Integer ASSOCIATION_SIZE;

    public MongodbCreator(MongodbLoader mongodbLoader,
                          MongodbFieldValueGenerator fieldValueGenerator,
                          ODMProperties odmProperties) {
        this.mongodbLoader = mongodbLoader;
        this.fieldValueGenerator = fieldValueGenerator;
        this.ASSOCIATION_SIZE = odmProperties.getAssociationSize();
    }

    @Override
    public <T> T create(Class<T> clazz, GenerateType generateType) {
        return null;
    }

    @Override
    public <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private <T> T generateValue(Field field) {
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
