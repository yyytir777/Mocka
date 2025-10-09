package entityinstantiator.generator.orm.hibernate.association;

import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.orm.AssociationMatcher;
import entityinstantiator.generator.orm.ORMType;

import java.lang.reflect.Field;

public class AllMatcher implements AssociationMatcher {

    @Override
    public boolean supports(Field field, GenerateType generateType, ORMType ormType) {
        return generateType == GenerateType.ALL;
    }
}
