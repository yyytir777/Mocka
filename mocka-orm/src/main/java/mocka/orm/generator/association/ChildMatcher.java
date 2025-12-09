package mocka.orm.generator.association;

import mocka.orm.generator.mybatis.AssociationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mocka.core.GenerateType;
import mocka.orm.generator.AssociationMatcher;
import mocka.orm.generator.ORMType;

import java.lang.reflect.Field;

public class ChildMatcher implements AssociationMatcher {

    public boolean supports(Field field, GenerateType generateType, ORMType ormType, AssociationType associationType) {
        if(!(generateType.equals(GenerateType.CHILDREN) || generateType.equals(GenerateType.CHILD))) return false;

        if(ormType.equals(ORMType.HIBERNATE)) {
            if (field.isAnnotationPresent(OneToMany.class)) return true;
            if (field.isAnnotationPresent(OneToOne.class) && field.getAnnotation(OneToOne.class).mappedBy().isEmpty()) return true;
            if (field.isAnnotationPresent(ManyToMany.class) && field.getAnnotation(ManyToMany.class).mappedBy().isEmpty()) return true;
        } else if(ormType.equals(ORMType.MYBATIS)) {
            if(associationType.equals(AssociationType.ONE_TO_MANY)) return true;
        }
        return false;
    }
}
