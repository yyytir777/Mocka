package jodag.generator.orm.hibernate.association;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jodag.generator.GenerateType;

import java.lang.reflect.Field;

public class ParentMatcher implements AssociationMatcher {

    public boolean supports(Field field, GenerateType generateType) {
        if(!(generateType.equals(GenerateType.PARENTS) || generateType.equals(GenerateType.PARENT))) return false;

        if (field.isAnnotationPresent(ManyToOne.class)) return true;
        if (field.isAnnotationPresent(OneToOne.class) && !field.getAnnotation(OneToOne.class).mappedBy().isEmpty()) return true;
        if (field.isAnnotationPresent(ManyToMany.class) && !field.getAnnotation(ManyToMany.class).mappedBy().isEmpty()) return true;

        return false;
    }
}
