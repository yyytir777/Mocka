package mocka.generator;

import mocka.generator.orm.ORMCreator;
import mocka.generator.orm.ORMType;


public class EntityGenerator<T> extends AbstractGenerator<T> {

    private final ORMCreator ormCreator;

    public EntityGenerator(Class<T> entity, ORMCreator ormCreator) {
        super(entity.getName(), entity);
        this.ormCreator = ormCreator;
    }

    /**
     * Returns a random entity instance with the default GeneratorType.SELF
     */
    @Override
    public T get() {
        return ormCreator.create(type, GenerateType.SELF);
    }

    /**
     * Returns a random entity instance with the given GenerateType
     */
    public T get(GenerateType generateType) {
        return ormCreator.create(type, generateType);
    }

    /**
     * Returns a random entity instance with the given ORMType and the default GeneratorType.SELF
     */
    public T get(ORMType ormType) {
        return ormCreator.create(ormType, type, GenerateType.SELF);
    }

    /**
     * Returns a random entity instance with the given ORMType and GenerateType
     */
    public T get(ORMType ormType, GenerateType generateType) {
        return ormCreator.create(ormType, type, generateType);
    }
}
