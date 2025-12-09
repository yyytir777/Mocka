package mocka.orm.generator;

import mocka.core.GenerateType;
import mocka.orm.generator.mybatis.AssociationType;

import java.lang.reflect.Field;

public interface AssociationMatcher {

    boolean supports(Field field, GenerateType generateType, ORMType ormType, AssociationType associationType);
}
