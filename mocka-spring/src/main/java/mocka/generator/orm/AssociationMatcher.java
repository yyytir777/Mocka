package mocka.generator.orm;

import mocka.generator.GenerateType;
import mocka.generator.orm.mybatis.AssociationType;

import java.lang.reflect.Field;

public interface AssociationMatcher {

    boolean supports(Field field, GenerateType generateType, ORMType ormType, AssociationType associationType);
}
