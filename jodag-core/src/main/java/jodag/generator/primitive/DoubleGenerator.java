package jodag.generator.primitive;


public class DoubleGenerator extends AbstractPrimitiveGenerator<Double> {

    private static DoubleGenerator INSTANCE;

    private DoubleGenerator() {
        super(Double.class);
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
