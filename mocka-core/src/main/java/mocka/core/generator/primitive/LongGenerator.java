package mocka.core.generator.primitive;

import mocka.core.generator.AbstractGenerator;

import java.util.List;


public class LongGenerator extends AbstractGenerator<Long> {

    private static final LongGenerator INSTANCE = new LongGenerator();

    private LongGenerator() {
        super("long", Long.class);
    }

    public static LongGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random long value. */
    @Override
    public Long get() {
        return getLong();
    }

    /** returns a random long within the full long range [Long.MIN_VALUE, Long.MAX_VALUE]. */
    public Long getLong() {
        return getLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /** returns a random long between the given min and max values [min, max]. */
    public Long getLong(Long min, Long max) {
        return randomProvider.getLong(min, max);
    }

    /** returns a random long between [0, n]. */
    public Long getNextLong(Long n) {
        return randomProvider.getLong(0, n);
    }

    /** returns a random even long within the valid range, avoiding overflow at Long.MAX_VALUE. */
    public Long getEvenLong() {
        long value = getLong();
        if(value == Long.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : value + 1;
    }

    /** returns a random odd long within the valid range, avoiding overflow at Long.MAX_VALUE. */
    public Long getOddLong() {
        long value = getLong();
        if(value == Long.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : value + 1;
    }

    /** returns a random positive long (1L, Long.MAX_VALUE]. */
    public Long getPositiveLong() {
        return getLong(1L, Long.MAX_VALUE);
    }

    /** returns a random negative long [Long.MIN_VALUE, -1L). */
    public Long getNegativeLong() {
        return getLong(Long.MIN_VALUE, -1L);
    }

    /** picks a random long from the given list. */
    public Long pickFrom(List<Long> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    /** picks a random long from the given array. */
    public Long pickFrom(Long[] longs) {
        return longs[randomProvider.getNextIdx(longs.length)];
    }
}
