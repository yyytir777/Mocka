package entityinstantiator.generator.orm.hibernate.association;

import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.orm.AssociationMatcher;
import entityinstantiator.generator.orm.ORMType;
import entityinstantiator.generator.orm.mybatis.AssociationType;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Factory class that manages a collection of {@link AssociationMatcher} implementations.
 * <p>
 * This class centralizes the logic for determining whether a given field,
 * together with its generation type and ORM type, is supported by one of the
 * available {@code AssociationMatcher}s.
 * </p>
 */
public class AssociationMatcherFactory {

    private static final List<AssociationMatcher> matchers = List.of(
            new ChildMatcher(),
            new ParentMatcher(),
            new AllMatcher()
    );

    public static boolean support(Field field, GenerateType generateType, ORMType ormType, AssociationType associationType) {
        return matchers.stream().anyMatch(m ->  m.supports(field, generateType, ormType, associationType));
    }
}
