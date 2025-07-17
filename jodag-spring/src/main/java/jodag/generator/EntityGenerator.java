package jodag.generator;

import jodag.exception.generator.GeneratorException;

import java.util.Random;

public class EntityGenerator<T> extends AbstractGenerator<T> {

    public EntityGenerator(Class<T> entity) {
        super(entity.getName(), entity);
    }

    public EntityGenerator(Class<T> entity, Random random) {
        super(entity.getName(), entity, random);
    }

    public EntityGenerator(Class<T> entity, long seed) {
        super(entity.getName(), entity, seed);
    }

    @Override
    public T get() {
        try {
            // TODO 엔티티 랜덤 생성 로직 작성
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new GeneratorException("entity를 생성할 수 없습니다.", e);
        }
    }
}
