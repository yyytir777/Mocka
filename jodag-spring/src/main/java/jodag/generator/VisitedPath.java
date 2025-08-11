package jodag.generator;


import java.util.Objects;

public class VisitedPath {
    private final Class<?> classA;
    private final Class<?> classB;

    public VisitedPath(Class<?> classA, Class<?> classB) {
        this.classA = classA;
        this.classB = classB;
    }

    public static VisitedPath of(Class<?> classA, Class<?> classB) {
        return new VisitedPath(
                classA.getName().compareTo(classB.getName()) <= 0 ? classA : classB,
                classA.getName().compareTo(classB.getName()) <= 0 ? classB : classA
        );
    }

    public Class<?> getClassA() {
        return classA;
    }

    public Class<?> getClassB() {
        return classB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitedPath that)) return false;
        return classA.equals(that.getClassA()) && classB.equals(that.getClassB());
    }

    @Override
    public int hashCode() {
        return Objects.hash(classA, classB);
    }

    @Override
    public String toString() {
        return "{" + "classA=" + classA.getSimpleName() + ", classB=" + classB.getSimpleName() + '}';
    }
}
