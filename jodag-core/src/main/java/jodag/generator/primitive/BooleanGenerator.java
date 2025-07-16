package jodag.generator.primitive;



public class BooleanGenerator extends AbstractPrimitiveGenerator<Boolean> {

    private static BooleanGenerator INSTANCE;

    private BooleanGenerator() {
        super(Boolean.class);
    }

    public static synchronized BooleanGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BooleanGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Boolean get() {
        return randomProvider.nextBoolean();
    }
}
