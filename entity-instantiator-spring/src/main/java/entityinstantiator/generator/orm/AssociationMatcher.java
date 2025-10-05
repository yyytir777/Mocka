package entityinstantiator.generator.orm;

import entityinstantiator.generator.GenerateType;

import java.lang.reflect.Field;

public interface AssociationMatcher {

    boolean supports(Field field, GenerateType generateType, ORMType ormType);
}
