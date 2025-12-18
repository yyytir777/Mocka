package mocka.orm.generator;

import mocka.core.GenerateType;
import mocka.core.generator.AbstractGenerator;

import java.util.HashMap;
import java.util.HashSet;


public class EntityGenerator<T> extends AbstractGenerator<T> {

    private final ORMSelector ormSelector;
    private GenerateType generateType = GenerateType.SELF;
    private ORMType ormType = ORMType.HIBERNATE;

    public EntityGenerator(Class<T> entity, ORMSelector ormSelector) {
        super(entity.getName(), entity);
        this.ormSelector = ormSelector;
    }

    public EntityGenerator<T> generateType(GenerateType generateType) {
        this.generateType = generateType;
        return this;
    }

    public EntityGenerator<T> ormType(ORMType ormType) {
        this.ormType = ormType;
        return this;
    }

    @Override
    public T get() {
        if (generateType.equals(GenerateType.ALL)) {
            return ormSelector.create(ormType, type, new HashMap<>(), new HashSet<>());
        }
        return ormSelector.create(ormType, type, generateType);
    }
}
