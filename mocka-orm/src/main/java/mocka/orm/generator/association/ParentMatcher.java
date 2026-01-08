package mocka.orm.generator.association;

import mocka.orm.generator.mybatis.AssociationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import mocka.core.GenerateType;
import mocka.orm.generator.AssociationMatcher;
import mocka.orm.generator.ORMType;

import java.lang.reflect.Field;

public class ParentMatcher implements AssociationMatcher {

    public boolean supports(Field field, GenerateType generateType, ORMType ormType, AssociationType associationType) {
        if(!(generateType.equals(GenerateType.PARENTS) || generateType.equals(GenerateType.PARENT))) return false;

        if(ormType.equals(ORMType.HIBERNATE)) {
            if (field.isAnnotationPresent(ManyToOne.class)) return true;
            if (field.isAnnotationPresent(OneToOne.class) && !field.getAnnotation(OneToOne.class).mappedBy().isEmpty()) return true;
            if (field.isAnnotationPresent(ManyToMany.class) && !field.getAnnotation(ManyToMany.class).mappedBy().isEmpty()) return true;
        } else if(ormType.equals(ORMType.MYBATIS)) {
            if (associationType.equals(AssociationType.MANY_TO_ONE)) return true;
        }
        return false;
    }
}
