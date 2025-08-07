package jodag.generator;

public enum GenerateType {
    ALL,
    SELF,
    CHILD,
    CHILDREN,
    PARENT,
    PARENTS;

    public GenerateType next() {
            return switch (this) {
                case SELF -> null;
                case ALL -> ALL;
                case CHILD -> SELF;
                case CHILDREN -> CHILDREN;
                case PARENT -> SELF;
                case PARENTS -> PARENTS;
            };
    }
}
