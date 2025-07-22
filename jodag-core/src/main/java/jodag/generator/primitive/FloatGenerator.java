package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;


public class FloatGenerator extends AbstractGenerator<Float> {

    private static final FloatGenerator INSTANCE = new FloatGenerator();

    private FloatGenerator() {
        super("float", Float.class);
    }

    public static FloatGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Float get() {
        return getFloat();
    }

    public Float getFloat() {
        return getFloat(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public Float getFloat(float min, float max) {
        return randomProvider.getFloat(min, max);
    }
}
