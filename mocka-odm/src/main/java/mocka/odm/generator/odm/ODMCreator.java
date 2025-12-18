package mocka.odm.generator.odm;

import mocka.core.GenerateType;
import mocka.core.VisitedPath;

import java.util.Map;
import java.util.Set;

public interface ODMCreator {

    <T> T create(Class<T> clazz, GenerateType generateType);

    <T> T create(Class<T> clazz, Map<String, Object> caches, Set<VisitedPath> visited);

    Set<Class<?>> load();
}
