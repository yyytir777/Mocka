package mocka.orm.generator;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class EntityGeneratorInitializer {

    private final EntityGeneratorFactory entityGeneratorFactory;
    private final ORMSelector ormSelector;

    public EntityGeneratorInitializer(EntityGeneratorFactory entityGeneratorFactory, ORMSelector ormSelector) {
        this.entityGeneratorFactory = entityGeneratorFactory;
        this.ormSelector = ormSelector;
    }

    @PostConstruct
    public void init() {
        Map<Class<?>, EntityGenerator<?>> generators = new HashMap<>();

        List<ORMCreator> creators = ormSelector.getCreators();
        for (ORMCreator creator : creators) {
            Set<Class<?>> entityClasses = creator.load();
            for (Class<?> entityClass : entityClasses) {
                generators.put(entityClass, new EntityGenerator<>(entityClass, ormSelector));
            }
        }
        entityGeneratorFactory.registerAll(generators);
    }

    public ORMSelector getOrmSelector() {
        return this.ormSelector;
    }
}
