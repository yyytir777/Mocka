package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;


public abstract class AbstractPrimitiveGenerator<T> extends AbstractGenerator<T> {

    protected AbstractPrimitiveGenerator(Class<T> type) {
        super(type.getName().toLowerCase(), type);
    }
}
