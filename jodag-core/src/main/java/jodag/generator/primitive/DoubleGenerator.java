package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class DoubleGenerator extends AbstractGenerator<Double> {

    private static DoubleGenerator INSTANCE;

    private DoubleGenerator() {
        super("double", Double.class);
    }

    public static synchronized DoubleGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DoubleGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Double get() {
        return randomProvider.nextDouble();
    }
}
