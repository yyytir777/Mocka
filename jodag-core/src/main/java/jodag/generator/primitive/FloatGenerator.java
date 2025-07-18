package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

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
        return 0f;
    }
}
