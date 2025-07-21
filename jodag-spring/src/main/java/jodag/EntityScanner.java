package jodag;

import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import jodag.generator.Generate;
import jodag.generator.SpringGeneratorFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * GenerateAnnotationProcessor 클래스 설명
 *  1. Spring Boot 애플리케이션 실행
 *  2. Spring이 @Component 클래스 스캔
 *  3. GenerateAnnotationProcessor 빈 생성
 *  4. @PostConstruct 메서드 실행
 *  5. beanFactory를 통해 basePackage get
 *  6. basePackage와 scanner를 통해 애노테이션 필터 처리 -> @Entity와 @Generate 붙은 클래스 gets
 */
@Component
public class EntityScanner implements BeanFactoryAware {

    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(@Nonnull BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @PostConstruct
    public void processGenerateAnnotation() {
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

        for(Class<?> clazz : candidates) {
            if(!clazz.isAnnotationPresent(Generate.class)) {
                System.out.println("@generate애노테이션이 아닙니다. = " + clazz.getName());
                continue;
            }
            SpringGeneratorFactory.add(clazz);
        }
    }
}
