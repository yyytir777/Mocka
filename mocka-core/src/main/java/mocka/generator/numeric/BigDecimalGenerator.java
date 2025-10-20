package mocka.generator.numeric;

import mocka.generator.AbstractGenerator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class BigDecimalGenerator extends AbstractGenerator<BigDecimal> {

    private static final BigDecimalGenerator INSTANCE = new BigDecimalGenerator();

    private BigDecimalGenerator() {
        super("big_decimal", BigDecimal.class);
    }

    public static BigDecimalGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public BigDecimal get() {
        return BigDecimal.valueOf(randomProvider.getDouble(0, 1)).setScale(18, RoundingMode.HALF_UP);
    }

    public BigDecimal get(int scale) {
        BigInteger bigInteger = new BigInteger(scale, randomProvider.getRandom());
        return new BigDecimal(bigInteger, scale);
    }

    public BigDecimal get(BigDecimal min, BigDecimal max, int scale) {
        if (min.compareTo(max) > 0) throw new IllegalArgumentException("min must be smaller than max");

        BigDecimal range = max.subtract(min);
        BigDecimal randomValue = range.multiply(BigDecimal.valueOf(randomProvider.getDouble(0, 1)));
        return min.add(randomValue).setScale(scale, RoundingMode.HALF_UP);
    }

    public BigDecimal getWithBitLength(int bitLength, int scale) {
        BigInteger bigInt = BigIntegerGenerator.getInstance().getWithBitLength(bitLength);
        return new BigDecimal(bigInt, scale);
    }
}
