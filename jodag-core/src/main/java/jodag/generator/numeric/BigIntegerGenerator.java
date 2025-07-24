package jodag.generator.numeric;

import jodag.generator.AbstractGenerator;

import java.math.BigInteger;

public class BigIntegerGenerator extends AbstractGenerator<BigInteger> {

    private static final BigIntegerGenerator instance = new BigIntegerGenerator();

    private BigIntegerGenerator() {
        super("big-integer", BigInteger.class);
    }

    public static BigIntegerGenerator getInstance() {
        return instance;
    }

    @Override
    public BigInteger get() {
        return BigInteger.ONE;
    }
}
