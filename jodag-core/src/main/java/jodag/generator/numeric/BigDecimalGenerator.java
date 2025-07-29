package jodag.generator.numeric;

import jodag.generator.AbstractGenerator;

import java.math.BigDecimal;

public class BigDecimalGenerator extends AbstractGenerator<BigDecimal> {

    private static final BigDecimalGenerator INSTANCE = new BigDecimalGenerator();

    private BigDecimalGenerator() {
        super("big-decimal", BigDecimal.class);
    }

    public static BigDecimalGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public BigDecimal get() {
        return BigDecimal.TEN;
    }
}
