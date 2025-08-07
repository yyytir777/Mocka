package jodag.generator.association;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jodag.generator.GenerateType;

import java.lang.reflect.Field;

public class ChildMatcher implements AssociationMatcher {

    public boolean supports(Field field, GenerateType generateType) {
        if(!(generateType.equals(GenerateType.CHILDREN) || generateType.equals(GenerateType.CHILD))) return false;

        if (field.isAnnotationPresent(OneToMany.class)) return true;
        if (field.isAnnotationPresent(OneToOne.class) && field.getAnnotation(OneToOne.class).mappedBy().isEmpty()) return true;
        if (field.isAnnotationPresent(ManyToMany.class) && field.getAnnotation(ManyToMany.class).mappedBy().isEmpty()) return true;

        return false;
    }
}
