package mocka.orm.generator.association;

import mocka.core.GenerateType;
import mocka.orm.generator.AssociationMatcher;
import mocka.orm.generator.ORMType;
import mocka.orm.generator.mybatis.AssociationType;

import java.lang.reflect.Field;

public class AllMatcher implements AssociationMatcher {

    @Override
    public boolean supports(Field field, GenerateType generateType, ORMType ormType,  AssociationType associationType) {
        return generateType == GenerateType.ALL;
    }
}
