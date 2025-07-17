package jodag.generator.primitive;


public class FloatGenerator extends AbstractPrimitiveGenerator<Float> {

    private static FloatGenerator INSTANCE;

    private FloatGenerator() {
        super(Float.class);
    }

    public static FloatGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FloatGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Float get() {
        return randomProvider.nextFloat();
    }
}
