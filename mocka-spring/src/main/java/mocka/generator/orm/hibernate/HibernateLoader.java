package mocka.generator.orm.hibernate;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import mocka.generator.orm.ORMLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Loads and discovers all Hibernate entity classes in the application.
 *
 * <p>
 * This loader scans the application's base package to find all classes annotated with
 * {@code @Entity} and registers them for entity generation. The scanning process is
 * triggered during Spring's bean initialization phase.
 * </p>
 *
 * <p><b>Execution Flow:</b></p>
 * <ol>
 *   <li>Spring Boot application starts</li>
 *   <li>Spring scans for {@code @Component} classes</li>
 *   <li>HibernateLoader bean is created</li>
 *   <li>{@link #load()} method is called to scan entity classes</li>
 *   <li>Base package is retrieved from {@link AutoConfigurationPackages}</li>
 *   <li>Classpath scanning with {@code @Entity} filter is performed</li>
 *   <li>Discovered entity classes are collected and returned</li>
 * </ol>
 */
@Component
public class HibernateLoader implements BeanFactoryAware, ORMLoader {

    private static final Logger log = LoggerFactory.getLogger(HibernateLoader.class);
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public Set<Class<?>> load() {
        Long startMs = System.currentTimeMillis();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
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
        log.info("Finished scanning entity classes in {}ms. Add {} entities in SpringGeneratorFactory", (endMs - startMs), candidates.size());
        return candidates;
    }
}
