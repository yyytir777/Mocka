package jodag.generator.primitive;


public class IntegerGenerator extends AbstractPrimitiveGenerator<Integer> {

    private static IntegerGenerator INSTANCE;

    private IntegerGenerator() {
        super(Integer.class);
    }

    public static synchronized IntegerGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IntegerGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Integer get() {
        return randomProvider.nextInteger();
    }
}
