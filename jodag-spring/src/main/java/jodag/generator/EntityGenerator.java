package jodag.generator;

import jodag.exception.generator.GeneratorException;


public class EntityGenerator<T> extends AbstractGenerator<T> {

    public EntityGenerator(Class<T> entity) {
        super(entity.getName(), entity);
    }

    /**
     * 엔티티에 필드에 맞춰 필드값을 랜덤 생성하여 엔티티를 return함
     * @return
     */
    @Override
    public T get() {
        try {
            return EntityInstanceCreator.create(type);
        } catch (Exception e) {
            throw new GeneratorException("entity를 생성할 수 없습니다.", e);
        }
    }
}
