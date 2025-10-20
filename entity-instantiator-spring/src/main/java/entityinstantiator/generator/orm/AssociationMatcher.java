package entityinstantiator.generator.orm;

import entityinstantiator.generator.GenerateType;
import entityinstantiator.generator.orm.mybatis.AssociationType;

import java.lang.reflect.Field;

public interface AssociationMatcher {

    boolean supports(Field field, GenerateType generateType, ORMType ormType, AssociationType associationType);
}
