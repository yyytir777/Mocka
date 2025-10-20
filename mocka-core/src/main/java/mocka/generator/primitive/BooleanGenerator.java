package mocka.generator.primitive;

import mocka.generator.AbstractGenerator;


public class BooleanGenerator extends AbstractGenerator<Boolean> {

    private static final BooleanGenerator INSTANCE =  new BooleanGenerator();

    private BooleanGenerator() {
        super("boolean", Boolean.class);
    }

    public static BooleanGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random boolean value. */
    @Override
    public Boolean get() {
        return randomProvider.getBoolean();
    }
}
