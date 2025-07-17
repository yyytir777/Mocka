package jodag.generator.primitive;


public class ShortGenerator extends AbstractPrimitiveGenerator<Short> {

    private static ShortGenerator INSTANCE;

    private ShortGenerator() {
        super(Short.class);
    }

    public static synchronized ShortGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShortGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Short get() {
        return randomProvider.nextShort();
    }
}
