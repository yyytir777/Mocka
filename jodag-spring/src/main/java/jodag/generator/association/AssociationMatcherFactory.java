package jodag.generator.association;

import jodag.generator.GenerateType;

import java.lang.reflect.Field;
import java.util.List;

public class AssociationMatcherFactory {

    private static final List<AssociationMatcher> matchers = List.of(
            new ChildMatcher(),
            new ParentMatcher()
    );

    public static boolean support(Field field, GenerateType generateType) {
        return matchers.stream().anyMatch(m ->  m.supports(field, generateType));
    }
}
