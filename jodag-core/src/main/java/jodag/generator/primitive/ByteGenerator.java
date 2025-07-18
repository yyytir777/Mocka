package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class ByteGenerator extends AbstractGenerator<Byte> {

    private static ByteGenerator INSTANCE;

    private ByteGenerator() {
        super("byte", Byte.class);
    }

    public static synchronized ByteGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ByteGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Byte get() {
        return null;
    }
}
