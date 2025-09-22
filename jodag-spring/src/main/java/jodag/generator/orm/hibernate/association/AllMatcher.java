package jodag.generator.orm.hibernate.association;

import jodag.generator.GenerateType;

import java.lang.reflect.Field;

public class AllMatcher implements AssociationMatcher {

    @Override
    public boolean supports(Field field, GenerateType generateType) {
        return generateType == GenerateType.ALL;
    }
}
