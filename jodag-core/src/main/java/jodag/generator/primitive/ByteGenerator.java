package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.Random;

public class ByteGenerator extends AbstractGenerator<Byte> {

    private static ByteGenerator INSTANCE;

    private ByteGenerator() {
        super("byte", Byte.class);
    }

    private ByteGenerator(Random random) {
        super("byte", Byte.class, random);
    }

    private ByteGenerator(long seed) {
        super("byte", Byte.class, seed);
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
