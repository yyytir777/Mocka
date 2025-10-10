package entityinstantiator.generator.numeric;

import entityinstantiator.generator.AbstractGenerator;

import java.math.BigInteger;

public class BigIntegerGenerator extends AbstractGenerator<BigInteger> {

    private static final BigIntegerGenerator instance = new BigIntegerGenerator();

    private BigIntegerGenerator() {
        super("big_integer", BigInteger.class);
    }

    public static BigIntegerGenerator getInstance() {
        return instance;
    }

    /**
     * Returns a random BigInteger with a default bit length of 64 bits
     */
    @Override
    public BigInteger get() {
        return getWithBitLength(64);
    }

    /**
     * Returns a random BigInteger within the specified range [min, max].
     */
    public BigInteger get(BigInteger min, BigInteger max) {
        if (min.compareTo(max) > 0) throw new IllegalArgumentException("min must be smaller than max");

        BigInteger range = max.subtract(min).add(BigInteger.ONE);
        BigInteger randomValue;

        do {
            randomValue = getWithBitLength(range.bitLength());
        } while (randomValue.compareTo(range) >= 0);

        return min.add(randomValue);
    }

    /**
     * Returns a random BigInteger with the specified bit length
     */
    public BigInteger getWithBitLength(int bitLength) {
        return new BigInteger(bitLength, randomProvider.getRandom());
    }
}
