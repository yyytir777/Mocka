package mocka.odm.generator;

import mocka.core.exception.GeneratorException;
import mocka.core.generator.factory.GeneratorFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DocumentGeneratorFactory extends GeneratorFactory {

    private static final Map<Class<?>, DocumentGenerator<?>> ODM_GENERATORS = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> DocumentGenerator<T> getGenerator(Class<T> clazz) {
        DocumentGenerator<?> generator = ODM_GENERATORS.get(clazz);
        if(generator == null){
            throw new GeneratorException("Cannot find document class");
        }
        return (DocumentGenerator<T>) generator;
    }

    public List<Class<?>> getDocumentGeneratorNames() {
        return new ArrayList<>(ODM_GENERATORS.keySet());
    }

    public void registerAll(Map<Class<?>, DocumentGenerator<?>> generators) {
        ODM_GENERATORS.putAll(generators);
    }
}
