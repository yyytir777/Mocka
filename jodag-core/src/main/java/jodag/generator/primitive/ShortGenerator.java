package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class ShortGenerator extends AbstractGenerator<Short> {

    private static ShortGenerator INSTANCE;

    private ShortGenerator() {
        super("short", Short.class);
    }

    public static synchronized ShortGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ShortGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Short get() {
        return getShort();
    }

    public Short getShort() {
        return getShort(Short.MIN_VALUE, Short.MAX_VALUE);
    }

    public Short getShort(Short min, Short max) {
        return (short) ThreadLocalRandom.current().nextInt(min, max);
    }

    public Short getEvenShort() {
        short value = getShort();
        if(value == Short.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : (short) (value + 1);
    }

    public Short getOddShort() {
        short value = getShort();
        if(value == Short.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : (short) (value + 1);
    }
}
