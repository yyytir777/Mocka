package mocka.generator.primitive;

import mocka.generator.AbstractGenerator;

import java.util.List;


public class ShortGenerator extends AbstractGenerator<Short> {

    private static final ShortGenerator INSTANCE = new ShortGenerator();

    private ShortGenerator() {
        super("short", Short.class);
    }

    public static ShortGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random short value. */
    @Override
    public Short get() {
        return getShort();
    }

    /** returns a random short within the full short range [Short.MIN_VALUE, Short.MAX_VALUE]. */
    public Short getShort() {
        return getShort(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    /** returns a random short between the given min and max values [min, max]. */
    public Short getShort(Short min, Short max) {
        return (short) randomProvider.getInt(min, max);
    }

    /** returns a random even short within the valid range, avoiding overflow at Short.MAX_VALUE. */
    public Short getEvenShort() {
        short value = getShort();
        if(value == Short.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : (short) (value + 1);
    }

    /** returns a random odd short within the valid range, avoiding overflow at Short.MAX_VALUE. */
    public Short getOddShort() {
        short value = getShort();
        if(value == Short.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : (short) (value + 1);
    }

    /** returns a random positive short (0, Short.MAX_VALUE]. */
    public Short getPositiveShort() {
        return getShort((short) 1, Short.MAX_VALUE);
    }

    /** returns a random negative short [Short.MIN_VALUE, 0). */
    public Short getNegativeShort() {
        return  getShort(Short.MIN_VALUE, (short) -1);
    }

    /** picks a random short from the given list. */
    public Short pickFrom(List<Short> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    /** picks a random short from the given array. */
    public Short pickFrom(Short[] shorts) {
        return shorts[randomProvider.getNextIdx(shorts.length)];
    }
}
