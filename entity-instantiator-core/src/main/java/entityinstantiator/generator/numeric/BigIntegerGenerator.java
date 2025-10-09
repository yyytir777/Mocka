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

    @Override
    public BigInteger get() {
        return getWithBitLength(64);
    }

    public BigInteger getWithBitLength(int bitLength) {
        return new BigInteger(bitLength, randomProvider.getRandom());
    }
}
