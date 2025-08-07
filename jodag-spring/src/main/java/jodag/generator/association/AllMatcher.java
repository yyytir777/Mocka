package jodag.generator.association;

import jodag.generator.GenerateType;

import java.lang.reflect.Field;

public class AllMatcher implements AssociationMatcher {

    @Override
    public boolean supports(Field field, GenerateType generateType) {
        return generateType == GenerateType.ALL;
    }
}
