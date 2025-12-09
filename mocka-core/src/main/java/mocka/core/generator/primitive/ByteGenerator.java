package mocka.core.generator.primitive;

import mocka.core.generator.AbstractGenerator;

import java.util.List;


public class ByteGenerator extends AbstractGenerator<Byte> {

    private static final ByteGenerator INSTANCE = new ByteGenerator();

    private ByteGenerator() {
        super("byte", Byte.class);
    }

    public static ByteGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random byte value. */
    @Override
    public Byte get() {
        return getByte();
    }

    /** returns a random byte within the full byte range [-128, 127]. */
    public Byte getByte() {
        return getByte(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    /** returns a random byte between the given min and max values [min, max]. */
    public Byte getByte(Byte min, Byte max) {
        return (byte) randomProvider.getInt(min, max);
    }

    /** returns a random even byte within the valid range, avoiding overflow at 127. */
    public Byte getEvenByte() {
        int value = getByte();
        if(value == Byte.MAX_VALUE) value--;
        return (byte) ((value % 2 == 0) ? value : value + 1);
    }

    /** returns a random odd byte within the valid range, avoiding overflow at 127. */
    public Byte getOddByte() {
        int value = getByte();
        if(value == Byte.MAX_VALUE) value--;
        return (byte) ((value % 2 != 0) ? value : value + 1);
    }

    /** returns a random positive byte (0, 127]. */
    public Byte getPositiveByte() {
        return getByte((byte) 1, Byte.MAX_VALUE);
    }

    /** returns a random negative byte [-128 ~ 0). */
    public Byte getNegativeByte() {
        return getByte(Byte.MIN_VALUE, (byte) -1);
    }

    /** picks a random byte from the given list. */
    public Byte pickFrom(List<Byte> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    /** picks a random byte from the given array. */
    public Byte pickFrom(Byte[] bytes) {
        return bytes[randomProvider.getNextIdx(bytes.length)];
    }
}
