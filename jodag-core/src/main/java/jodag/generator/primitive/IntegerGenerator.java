package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class IntegerGenerator extends AbstractGenerator<Integer> {

    private static IntegerGenerator INSTANCE;

    private IntegerGenerator() {
        super("integer", Integer.class);
    }

    public static synchronized IntegerGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IntegerGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Integer get() {
        return getInteger();
    }

    public Integer getInteger() {
        return getInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public Integer getInteger(Integer min, Integer max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public Integer getEvenInteger() {
        int value = getInteger();
        if(value == Integer.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : value + 1;
    }

    public Integer getOddInteger() {
        int value = getInteger();
        if(value == Integer.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : value + 1;
    }
}
