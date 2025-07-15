package jodag.generator;

import jodag.generator.common.DefaultAbstractGenerator;

public class GeneratorInfo<T> {

    private final String name;
    private final Class<T> type;

    public GeneratorInfo(String name, Class<T> type) {
        this.name = name;
        this.type = type;
    }

    public static GeneratorInfo<?> from(Generator<?> generator) {
        if (generator instanceof DefaultAbstractGenerator<?> g) {
            return new GeneratorInfo<>(g.getName(), g.getType());
        }
        throw new IllegalArgumentException("Generator는 DefaultAbstractGenerator 기반이어야 합니다.");
    }

    @Override
    public String toString() {
        return "name = " + name + ", type = " + type.getSimpleName();
    }
}


