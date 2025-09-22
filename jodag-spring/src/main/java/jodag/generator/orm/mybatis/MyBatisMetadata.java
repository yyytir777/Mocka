package jodag.generator.orm.mybatis;

import ch.qos.logback.core.joran.sanity.Pair;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class MyBatisMetadata {

    // XML설정파일
    private final Map<String, Resource> RESOURCE_MAP = new HashMap<>();

    // resultMapId -> 매핑 클래스
    private final Map<String, Class<?>> INSTANCE_MAP = new HashMap<>();

    // 매핑 클래스 -> 매핑된 필드들
    private final Map<Class<?>, Set<PropertyField>> INSTANCE_FIELD_MAP = new HashMap<>();

    // {부모 클래스, 자식 클래스}, 연관관계 타입 정보 맵
    private final Map<Pair<Class<?>, Class<?>>, AssociationType> ASSOCIATION_TYPE_MAP = new HashMap<>();

    // 순환 참조 방지 Set
    private final Set<Class<?>> VISITED_MAP = new HashSet<>();

    // resultId로 class정보 return
    public void addClass(String resultId, Class<?> clazz) {
        INSTANCE_MAP.computeIfAbsent(resultId, k -> clazz);
    }

    private Class<?> getClass(String resultId) {
        return INSTANCE_MAP.get(resultId);
    }

    // Field 추가
    public void addFieldToClass(Class<?> clazz, PropertyField field) {
        INSTANCE_FIELD_MAP.computeIfAbsent(clazz, k -> new HashSet<>()).add(field);
    }

    // resultId로 필드 정보 return
    public Set<PropertyField> getFields(String resultId) {
        Class<?> clazz = getClass(resultId);
        return INSTANCE_FIELD_MAP.get(clazz);
    }

    public void printClass() {
        for (Class<?> value : INSTANCE_MAP.values()) {
            System.out.println(value);
        }
    }

    public void printFields() {
        for (Map.Entry<Class<?>, Set<PropertyField>> classSetEntry : INSTANCE_FIELD_MAP.entrySet()) {
            System.out.println(classSetEntry.getKey().getSimpleName() + " = " + classSetEntry.getValue().toString());
        }
    }

    public void addResources(Resource[] resources) {
        for (Resource resource : resources) {
            RESOURCE_MAP.put(resource.getFilename(), resource);
        }
    }

    public void printResources() {
        for (Map.Entry<String, Resource> entry : RESOURCE_MAP.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    public int getResourceCount() {
        return RESOURCE_MAP.size();
    }
}
