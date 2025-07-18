package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

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
        return getByte();
    }

    public Byte getByte() {
        return getByte(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    public Byte getByte(byte min, byte max) {
        return (byte) ThreadLocalRandom.current().nextInt(min, max);
    }

    public Byte getEvenByte() {
        int value = getByte();
        if(value == Byte.MAX_VALUE) value--;
        return (byte) ((value % 2 == 0) ? value : value + 1);
    }

    public Byte getOddByte() {
        int value = getByte();
        if(value == Byte.MAX_VALUE) value--;
        return (byte) ((value % 2 != 0) ? value : value + 1);
    }

}
