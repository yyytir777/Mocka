package mocka.odm;

import jakarta.annotation.PostConstruct;
import mocka.odm.generator.DocumentGenerator;
import mocka.odm.generator.DocumentGeneratorFactory;
import mocka.odm.generator.ODMResolver;
import mocka.odm.generator.odm.ODMCreator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DocumentGeneratorInitializer {

    private final DocumentGeneratorFactory documentGeneratorFactory;
    private final ODMResolver odmResolver;

    public DocumentGeneratorInitializer(DocumentGeneratorFactory documentGeneratorFactory, ODMResolver odmResolver) {
        this.documentGeneratorFactory = documentGeneratorFactory;
        this.odmResolver = odmResolver;
    }

    @PostConstruct
    public void init() {
        Map<Class<?>, DocumentGenerator<?>> generators = new HashMap<>();

        List<ODMCreator> creators = odmResolver.getCreators();
        for (ODMCreator creator : creators) {
            Set<Class<?>> documentClasses = creator.load();
            for (Class<?> documentClass : documentClasses) {
                generators.put(documentClass, new DocumentGenerator<>(documentClass, odmResolver));
            }
        }
        documentGeneratorFactory.registerAll(generators);
    }

    public ODMResolver getOdmSelector() {
        return this.odmResolver;
    }
}
