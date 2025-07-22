package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;


public class LongGenerator extends AbstractGenerator<Long> {

    private static final LongGenerator INSTANCE = new LongGenerator();

    private LongGenerator() {
        super("long", Long.class);
    }

    public static LongGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Long get() {
        return getLong();
    }

    public Long getLong() {
        return getLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public Long getLong(Long min, Long max) {
        return randomProvider.getLong(min, max);
    }

    public Long getNextLong(Long l) {
        return randomProvider.getLong(l);
    }

    public Long getEvenLong() {
        long value = getLong();
        if(value == Long.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : value + 1;
    }

    public Long getOddLong() {
        long value = getLong();
        if(value == Long.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : value + 1;
    }
}
