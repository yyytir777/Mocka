package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class BooleanGenerator extends AbstractGenerator<Boolean> {

    private static BooleanGenerator INSTANCE;

    private BooleanGenerator() {
        super("boolean", Boolean.class);
    }

    public static synchronized BooleanGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BooleanGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Boolean get() {
        return randomProvider.nextBoolean();
    }

    public Byte getByte(Byte min, Byte max) {
        return null;
    }
}
