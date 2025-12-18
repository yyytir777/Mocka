package mocka.odm;

import jakarta.annotation.PostConstruct;
import mocka.odm.generator.DocumentGenerator;
import mocka.odm.generator.DocumentGeneratorFactory;
import mocka.odm.generator.ODMSelector;
import mocka.odm.generator.odm.ODMCreator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DocumentGeneratorInitializer {

    private final DocumentGeneratorFactory documentGeneratorFactory;
    private final ODMSelector odmSelector;

    public DocumentGeneratorInitializer(DocumentGeneratorFactory documentGeneratorFactory, ODMSelector odmSelector) {
        this.documentGeneratorFactory = documentGeneratorFactory;
        this.odmSelector = odmSelector;
    }

    @PostConstruct
    public void init() {
        Map<Class<?>, DocumentGenerator<?>> generators = new HashMap<>();

        List<ODMCreator> creators = odmSelector.getCreators();
        for (ODMCreator creator : creators) {
            Set<Class<?>> documentClasses = creator.load();
            for (Class<?> documentClass : documentClasses) {
                generators.put(documentClass, new DocumentGenerator<>(documentClass, odmSelector));
            }
        }
        documentGeneratorFactory.registerAll(generators);
    }

    public ODMSelector getOdmSelector() {
        return this.odmSelector;
    }
}
