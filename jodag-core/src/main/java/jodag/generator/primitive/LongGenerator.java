package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class LongGenerator extends AbstractGenerator<Long> {

    private static LongGenerator INSTANCE;

    private LongGenerator() {
        super("long", Long.class);
    }

    public static synchronized LongGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LongGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Long get() {
        return randomProvider.nextLong();
    }
}
