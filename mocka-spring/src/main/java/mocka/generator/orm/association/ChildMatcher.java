package mocka.generator.orm.association;

import mocka.generator.orm.mybatis.AssociationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import mocka.generator.GenerateType;
import mocka.generator.orm.AssociationMatcher;
import mocka.generator.orm.ORMType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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

    private Class<?> resolveFieldType(Field field) {
        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType parameterizedType) {
            Type actualType = parameterizedType.getActualTypeArguments()[0];
            if (actualType instanceof Class<?> clazz) {
                return clazz;
            }
        }
        return null;
    }
}
