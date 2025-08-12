package jodag.generator;

import jodag.exception.generator.GeneratorException;


public class EntityGenerator<T> extends AbstractGenerator<T> {

    private final EntityInstanceCreator entityInstanceCreator;

    public EntityGenerator(Class<T> entity, EntityInstanceCreator entityInstanceCreator) {
        super(entity.getName(), entity);
        this.entityInstanceCreator = entityInstanceCreator;
    }

    /**
     * 엔티티에 필드에 맞춰 필드값을 랜덤 생성하여 엔티티를 return함
     * @return
     */
    @Override
    public T get() {
        try {
            return entityInstanceCreator.createInstance(type, GenerateType.SELF);
        } catch (IllegalArgumentException iae) {
            throw new GeneratorException("entity의 필드와 생성한 값의 타입이 일치하지 않습니다.", iae);
        }
        catch (Exception e) {
            throw new GeneratorException("entity를 생성할 수 없습니다.", e);
        }
    }

    public T get(GenerateType generateType) {
        try {
            return entityInstanceCreator.createInstance(type, generateType);
        } catch (Exception e) {
            throw new GeneratorException("entity를 생성할 수 없습니다.", e);
        }
    }
}
