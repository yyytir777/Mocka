package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class FloatGenerator extends AbstractGenerator<Float> {

    private static FloatGenerator INSTANCE;

    private FloatGenerator() {
        super("float", Float.class);
    }

    public static synchronized FloatGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FloatGenerator();
        }
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
        return ThreadLocalRandom.current().nextFloat(min, max);
    }
}
