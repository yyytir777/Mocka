package jodag.generator;

import java.lang.reflect.Field;
import java.util.Objects;

public class VisitedPath {
    private final String ownerClass;
    private final Field field;

    public VisitedPath(Class<?> ownerClass, Field field) {
        this.ownerClass = ownerClass.getSimpleName();
        this.field = field;
    }

    public static VisitedPath of(Class<?> clazz, Field field) {
        return new VisitedPath(clazz, field);
    }

    public String getOwnerClass() {
        return ownerClass;
    }

    public Field getField() {
        return field;
    }

    @Override
    public String toString() {
        return ownerClass + "#" + field.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitedPath target)) return false;
        return field.getName().equals(target.field.getName()) &&
                field.getDeclaringClass().equals(target.field.getDeclaringClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(field.getName(), field.getDeclaringClass());
    }



}
