package jodag.generator.primitive;


public class LongGenerator extends AbstractPrimitiveGenerator<Long> {

    private static LongGenerator INSTANCE;

    private LongGenerator() {
        super(Long.class);
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
