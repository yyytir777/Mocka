package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;


public class BooleanGenerator extends AbstractGenerator<Boolean> {

    private static final BooleanGenerator INSTANCE =  new BooleanGenerator();

    private BooleanGenerator() {
        super("boolean", Boolean.class);
    }

    public static BooleanGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Boolean get() {
        return randomProvider.getBoolean();
    }
}
