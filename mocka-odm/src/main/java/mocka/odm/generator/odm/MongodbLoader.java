package mocka.odm.generator.odm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MongodbLoader implements BeanFactoryAware, ODMLoader {

    private static final Logger log = LoggerFactory.getLogger(MongodbLoader.class);
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Set<Class<?>> load() {
        Long startMs = System.currentTimeMillis();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Document.class));
        String basePackage = AutoConfigurationPackages.get(beanFactory).get(0);
        Set<Class<?>> candidates = scanner.findCandidateComponents(basePackage)
                .stream().map(def -> {
                    try {
                        return Class.forName(def.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

        Long endMs = System.currentTimeMillis();
        log.info("Finished scanning document classes in {}ms. Add {} documents in DocumentGeneratorFactory", (endMs - startMs), candidates.size());
        return candidates;
    }
}
