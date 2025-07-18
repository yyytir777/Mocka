package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

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
        return getDouble();
    }

    public Double getDouble() {
        return getDouble(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public Double getDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
