package mocka.generator;

import mocka.generator.orm.ORMType;


public class EntityGenerator<T> extends AbstractGenerator<T> {

    private final EntityInstanceCreator entityInstanceCreator;

    public EntityGenerator(Class<T> entity, EntityInstanceCreator entityInstanceCreator) {
        super(entity.getName(), entity);
        this.entityInstanceCreator = entityInstanceCreator;
    }

    /**
     * Returns a random entity instance with the default GeneratorType.SELF
     */
    @Override
    public T get() {
        return entityInstanceCreator.createInstance(type, GenerateType.SELF);
    }

    /**
     * Returns a random entity instance with the given GenerateType
     */
    public T get(GenerateType generateType) {
        return entityInstanceCreator.createInstance(type, generateType);
    }

    /**
     * Returns a random entity instance with the given ORMType and the default GeneratorType.SELF
     */
    public T get(ORMType ormType) {
        return entityInstanceCreator.createInstance(ormType, type, GenerateType.SELF);
    }

    /**
     * Returns a random entity instance with the given ORMType and GenerateType
     */
    public T get(ORMType ormType, GenerateType generateType) {
        return entityInstanceCreator.createInstance(ormType, type, generateType);
    }
}
