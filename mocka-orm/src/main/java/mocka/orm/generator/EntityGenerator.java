package mocka.orm.generator;

import mocka.core.GenerateType;
import mocka.core.generator.AbstractGenerator;

import java.util.HashMap;
import java.util.HashSet;

/**
 * {@code EntityGenerator} is a concrete generator responsible for creating
 * ORM-managed entity instances via {@link ORMResolver}.
 *
 * <p>
 * Actual entity creation logic is delegated to {@link ORMResolver},
 * which resolves and invokes the appropriate {@code ORMCreator}.
 * </p>
 *
 * @param <T> the entity type to generate
 */
public class EntityGenerator<T> extends AbstractGenerator<T> {

    private final ORMResolver ormResolver;
    private GenerateType generateType = GenerateType.SELF;
    private ORMType ormType = ORMType.HIBERNATE;

    public EntityGenerator(Class<T> entity, ORMResolver ormResolver) {
        super(entity.getName(), entity);
        this.ormResolver = ormResolver;
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
            return ormResolver.create(ormType, type, new HashMap<>(), new HashSet<>());
        }
        return ormResolver.create(ormType, type, generateType);
    }
}
