package mocka.odm.generator;

import mocka.core.exception.GeneratorException;
import mocka.odm.generator.mongodb.MongodbCreator;
import mocka.core.GenerateType;
import mocka.core.VisitedPath;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ODMResolver {

    private final BeanFactory beanFactory;

    private Map<ODMType, ODMCreator> odmCreators = new HashMap<>();

    private static final String MONGODB_BEAN_NAME = "mongoTemplate";
    // TODO : Redis
//    private static final String REDIS_BEAN_NAME = "redisTemplate";

    public ODMResolver(BeanFactory beanFactory, ODMProperties odmProperties, List<ODMCreator> odmCreators) {
        this.beanFactory = beanFactory;
        this.odmCreators = resolver(odmProperties, odmCreators);
    }

    public List<ODMCreator> getCreators() {
        if(odmCreators.isEmpty()) {
            throw new GeneratorException("ODM Creator is null");
        }
        return new ArrayList<>(odmCreators.values());
    }

    public <T> T create(ODMType odmType, Class<T> clazz, GenerateType generateType) {
        if(odmCreators.isEmpty()) {
            throw new GeneratorException("ODM Creator is null");
        }
        return odmCreators.get(odmType).create(clazz, generateType);
    }

    public <T> T create(ODMType odmType, Class<T> clazz, HashMap<String, Object> caches, HashSet<VisitedPath> visited) {
        return odmCreators.get(odmType).create(clazz, caches, visited);
    }

    private Map<ODMType, ODMCreator> resolver(ODMProperties odmProperties, List<ODMCreator> odmCreators) {
        boolean hasMongoDB = beanFactory.containsBean(MONGODB_BEAN_NAME);

        if(!hasMongoDB) {
            throw new GeneratorException("ODM isn't exists");
        }

        List<ODMType> odmTypes = odmProperties.getOdmType();

        Map<ODMType, ODMCreator> creatorMap = odmCreators.stream()
                .collect(Collectors.toMap(this::resolveType, creator -> creator));

        if(odmTypes != null && odmTypes.size() > 1) {
            return odmTypes.stream()
                    .collect(Collectors.toMap(
                            type -> type, creatorMap::get));
        } else if(odmTypes != null && odmTypes.size() == 1) {
            return switch (odmTypes.get(0)) {
                case MONGDODB -> Map.of(ODMType.MONGDODB, creatorMap.get(ODMType.MONGDODB));
            };
        }
        return Map.of(ODMType.MONGDODB, creatorMap.get(ODMType.MONGDODB));
    }

    private ODMType resolveType(ODMCreator odmCreator) {
        if(odmCreator instanceof MongodbCreator) return ODMType.MONGDODB;
        throw new IllegalArgumentException("Unsupported ODMCreator: " + odmCreator);
    }
}
