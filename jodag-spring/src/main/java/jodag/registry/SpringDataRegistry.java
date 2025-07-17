package jodag.registry;

import jodag.generator.EntityGenerator;
import jodag.generator.Generator;
import org.springframework.stereotype.Component;

@Component
public class SpringDataRegistry {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    /**
     * 같은 패키지에서만 호출할 수 있도록 설정
     * @param entity
     * @param <T>
     */
    <T> void add(Class<T> entity) {
        Generator<T> generator = new EntityGenerator<>(entity);
        dataRegistry.add(generator);
    }

    public  <T>Generator<T> getGenerator(Class<T> entityClass) {
        return dataRegistry.getGenerator(entityClass.getName());
    }

    public <T> void delete(Class<T> entity) {
        dataRegistry.delete(entity.getName());
    }
}
