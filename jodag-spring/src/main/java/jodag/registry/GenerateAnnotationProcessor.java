package jodag.registry;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jodag.generator.Generate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;


@Component
public class GenerateAnnotationProcessor implements BeanFactoryAware {

    private BeanFactory beanFactory;
    private final SpringDataRegistry dataRegistry;

    public GenerateAnnotationProcessor(SpringDataRegistry dataRegistry) {
        this.dataRegistry = dataRegistry;
    }

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void processGenerateAnnotation() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Generate.class));
        String basePackage = AutoConfigurationPackages.get(beanFactory).get(0);
        Set<Class<?>> candidates = scanner.findCandidateComponents(basePackage)
                .stream().map(def -> {
                    try {
                        return Class.forName(def.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

        for(Class<?> clazz : candidates) {
            if(!clazz.isAnnotationPresent(Entity.class)) continue;
            System.out.println("clazz = " + clazz);
            dataRegistry.add(clazz);
        }
    }
}
