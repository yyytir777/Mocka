package jodag.generator.primitive;



public class ByteGenerator extends AbstractPrimitiveGenerator<Byte> {

    private static ByteGenerator INSTANCE;

    private ByteGenerator() {
        super(Byte.class);
    }

    public static synchronized ByteGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ByteGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Byte get() {
        return randomProvider.nextByte();
    }
}
