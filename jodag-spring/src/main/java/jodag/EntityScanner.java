package jodag;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
public class EntityScanner {

    @PostConstruct
    public List<Map<String, String>> analyzeEntity() {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));

        String basePackage = EntityScanner.class.getPackage().getName();

        Set<Class<?>> entityClasses = scanner.findCandidateComponents(basePackage)
                .stream().map(def -> {
                    try {
                        return Class.forName(def.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toSet());

        List<Map<String, String>> result = new ArrayList<>();
        for (Class<?> entityClass : entityClasses) {
            System.out.println("Entity = " + entityClass.getSimpleName());
            for (Field field : entityClass.getDeclaredFields()) {
                result.add(Map.of(field.getName(), field.getType().getSimpleName()));
                System.out.println("  Field: " + field.getName() + " (" + field.getType().getSimpleName() + ")");            }
        }
        return result;
    }
}
