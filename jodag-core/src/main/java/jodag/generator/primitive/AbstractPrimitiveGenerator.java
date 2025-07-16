package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.Random;

public abstract class AbstractPrimitiveGenerator<T> extends AbstractGenerator<T> {

    protected AbstractPrimitiveGenerator(Class<T> type) {
        super(type.getName().toLowerCase(), type);
    }

    protected AbstractPrimitiveGenerator(String name, Class<T> type) {
        super(name, type);
    }

    protected AbstractPrimitiveGenerator(String name, Class<T> type, Random random) {
        super(name, type, random);
    }

    protected AbstractPrimitiveGenerator(String name, Class<T> type, long seed) {
        super(name, type, seed);
    }
}
