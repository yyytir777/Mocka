package mocka.generator.orm.hibernate.association;

import mocka.generator.GenerateType;
import mocka.generator.orm.AssociationMatcher;
import mocka.generator.orm.ORMType;
import mocka.generator.orm.mybatis.AssociationType;

import java.lang.reflect.Field;

public class AllMatcher implements AssociationMatcher {

    @Override
    public boolean supports(Field field, GenerateType generateType, ORMType ormType,  AssociationType associationType) {
        return generateType == GenerateType.ALL;
    }
}
