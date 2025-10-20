package mocka.generator.primitive;

import mocka.generator.AbstractGenerator;

import java.util.List;


public class IntegerGenerator extends AbstractGenerator<Integer> {

    private static final IntegerGenerator INSTANCE = new IntegerGenerator();

    private IntegerGenerator() {
        super("integer", Integer.class);
    }

    public static IntegerGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random integer value. */
    @Override
    public Integer get() {
        return getInteger();
    }

    /** returns a random integer within the full integer range [Integer.MIN_VALUE, Integer.MAX_VALUE]. */
    public Integer getInteger() {
        return getInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /** returns a random integer between the given min and max values [min, max]. */
    public Integer getInteger(Integer min, Integer max) {
        return randomProvider.getInt(min, max);
    }

    /** returns a random integer between [0, n]. */
    public Integer getNextInteger(Integer n) {
        return getInteger(0, n);
    }

    /** returns a random even integer within the valid range, avoiding overflow at Integer.MAX_VALUE. */
    public Integer getEvenInteger() {
        int value = getInteger();
        if(value == Integer.MAX_VALUE) value--;
        return (value % 2 == 0) ? value : value + 1;
    }

    /** returns a random odd integer within the valid range, avoiding overflow at Integer.MAX_VALUE. */
    public Integer getOddInteger() {
        int value = getInteger();
        if(value == Integer.MAX_VALUE) value--;
        return (value % 2 != 0) ? value : value + 1;
    }

    /** returns a random positive integer (0, Integer.MAX_VALUE]. */
    public Integer getPositiveInteger() {
        return getInteger(1, Integer.MAX_VALUE);
    }

    /** returns a random negative integer [Integer.MIN_VALUE, 0). */
    public Integer getNegativeInteger() {
        return getInteger(Integer.MIN_VALUE, -1);
    }

    /** picks a random integer from the given list. */
    public Integer pickFrom(List<Integer> list) {
        return list.get(randomProvider.getInt(list.size()));
    }

    /** picks a random integer from the given array. */
    public Integer pickFrom(Integer[] integers) {
        return integers[randomProvider.getInt(integers.length)];
    }
}
