package jodag.generator.numeric;


import java.math.BigDecimal;
import java.math.BigInteger;

public class NumericGenerator {

    private static final NumericGenerator instance = new NumericGenerator();

    private final BigIntegerGenerator bigIntegerGenerator = BigIntegerGenerator.getInstance();
    private final BigDecimalGenerator bigDecimalGenerator = BigDecimalGenerator.getInstance();

    private NumericGenerator() {}

    public static NumericGenerator getInstance() {
        return instance;
    }

    public BigInteger getBigInteger() {
        return bigIntegerGenerator.get();
    }

    public BigDecimal getBigDecimal() {
        return bigDecimalGenerator.get();
    }
}
