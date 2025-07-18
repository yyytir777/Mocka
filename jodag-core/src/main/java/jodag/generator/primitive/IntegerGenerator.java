package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class IntegerGenerator extends AbstractGenerator<Integer> {

    private static IntegerGenerator INSTANCE;

    private IntegerGenerator() {
        super("integer", Integer.class);
    }

    public static synchronized IntegerGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IntegerGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Integer get() {
        return 0;
    }
}
