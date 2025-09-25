package jodag.generator;

import jodag.exception.GeneratorException;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.orm.ORMCreator;
import jodag.generator.orm.ORMResolver;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SpringGeneratorFactory extends GeneratorFactory {

    private final EntityInstanceCreator entityInstanceCreator;
    private final ORMCreator ormCreator;
    private static final Map<String, EntityGenerator<?>> entityGenerators = new ConcurrentHashMap<>();
    private final Set<Class<?>> SCANNED_CLASSES = new HashSet<>();

    public SpringGeneratorFactory(EntityInstanceCreator entityInstanceCreator, ORMCreator ormCreator) {
        this.entityInstanceCreator = entityInstanceCreator;
        this.ormCreator = ormCreator;
        load();
        registerClasses();
    }

    private void load() {
        List<ORMResolver> resolvers = ormCreator.getResolver();
        for (ORMResolver resolver : resolvers) {
            Set<Class<?>> load = resolver.load();
            SCANNED_CLASSES.addAll(load);
        }
    }

    private void registerClasses() {
        for (Class<?> clazz : SCANNED_CLASSES) {
            add(clazz);
        }
    }

    public <T> void add(Class<T> clazz) {
        System.out.println("add method invoked");
        entityGenerators.put(clazz.getSimpleName(), new EntityGenerator<>(clazz, entityInstanceCreator));
    }

    @SuppressWarnings("unchecked")
    public <T> EntityGenerator<T> getGenerator(Class<T> clazz) {
        EntityGenerator<?> generator = entityGenerators.get(clazz.getSimpleName());
        if (generator == null) {
            throw new GeneratorException("Cannot find entity class");
        }
        return (EntityGenerator<T>) generator;
    }

    public List<String> getGeneratorNames() {
        return new ArrayList<>(entityGenerators.keySet());
    }

}
