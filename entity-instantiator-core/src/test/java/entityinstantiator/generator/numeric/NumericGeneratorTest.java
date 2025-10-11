package entityinstantiator.generator.numeric;

import entityinstantiator.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.*;

@DisplayName("NumericGenerator Test Code")
public class NumericGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verity the BigIntegerGenerator & BigDecimalGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        BigIntegerGenerator bigIntegerGenerator = generatorFactory.asBigInteger();
        assertThat(bigIntegerGenerator).isNotNull().isInstanceOf(BigIntegerGenerator.class);

        BigDecimalGenerator bigDecimalGenerator = generatorFactory.asBigDecimal();
        assertThat(bigDecimalGenerator).isNotNull().isInstanceOf(BigDecimalGenerator.class);
    }

    @Test
    @DisplayName("BigIntegerGenerator & BigDecimalGenerator is managed as singleton")
    void generator_is_singleton() {
        BigIntegerGenerator bigIntegerGenerator1 = generatorFactory.asBigInteger();
        BigIntegerGenerator bigIntegerGenerator2 = generatorFactory.asBigInteger();
        assertThat(bigIntegerGenerator1).isSameAs(bigIntegerGenerator2);

        BigDecimalGenerator bigDecimalGenerator1 = generatorFactory.asBigDecimal();
        BigDecimalGenerator bigDecimalGenerator2 = generatorFactory.asBigDecimal();
        assertThat(bigDecimalGenerator1).isSameAs(bigDecimalGenerator2);
    }

    @Test
    @DisplayName("returns BigInteger")
    void get_bigInteger() {
        BigInteger bigInteger = generatorFactory.asBigInteger().get();
        System.out.println("bigInteger = " + bigInteger);
        assertThat(bigInteger).isInstanceOf(BigInteger.class);
    }

    @Test
    @DisplayName("returns BigInteger value between the given min and max")
    void get_value_between_min_max() {
        BigInteger min = new BigInteger(String.valueOf(1));
        BigInteger max = new BigInteger(String.valueOf(1000));
        BigInteger bigInteger = generatorFactory.asBigInteger().get(min, max);
        System.out.println("bigInteger = " + bigInteger);
        assertThat(bigInteger).isInstanceOf(BigInteger.class).isBetween(min, max);
    }

    @Test
    @DisplayName("returns BigInteger with given bit length")
    void get_bigInteger_with_bit_length() {
        int bitLength = 64;
        BigInteger bigInteger = generatorFactory.asBigInteger().getWithBitLength(bitLength);
        System.out.println("bigInteger = " + bigInteger);

        assertThat(bigInteger).isInstanceOf(BigInteger.class);
        System.out.println("bigInteger = " + bigInteger.bitLength());
        assertThat(bigInteger.bitLength()).isLessThanOrEqualTo(bitLength);
    }

    @Test
    @DisplayName("returns BigDecimal")
    void get_bigDecimal() {
        BigDecimal bigDecimal = generatorFactory.asBigDecimal().get();
        System.out.println("bigDecimal = " + bigDecimal);
        assertThat(bigDecimal).isInstanceOf(BigDecimal.class);
    }

    @Test
    @DisplayName("returns BigDecimal with given bit length")
    void get_bigDecimal_with_bit_length() {
        int bitLength = 64;
        int scale = 64;
        BigDecimal bigDecimal = generatorFactory.asBigDecimal().getWithBitLength(bitLength, scale);
        System.out.println("bigDecimal = " + bigDecimal);

        assertThat(bigDecimal).isInstanceOf(BigDecimal.class);

        BigInteger unscaled = bigDecimal.unscaledValue();
        assertThat(unscaled.bitLength()).isLessThanOrEqualTo(bitLength);

        assertThat(bigDecimal.scale()).isEqualTo(scale);
    }


    @Test
    @DisplayName("returns BigDecimal with given scale")
    void get_bigDecimal_with_scale() {
        int scale = 10;
        BigDecimal bigDecimal = generatorFactory.asBigDecimal().get(scale);
        System.out.println("bigDecimal = " + bigDecimal);

        assertThat(bigDecimal).isInstanceOf(BigDecimal.class);
        assertThat(bigDecimal.scale()).isEqualTo(scale);
    }

    @Test
    @DisplayName("returns BigDecimal with given min, max, scale")
    void get_bigDecimal_with_min_max_scale() {
        int scale = 10;
        BigDecimal min = BigDecimal.valueOf(1.5);
        BigDecimal max = BigDecimal.valueOf(3.5);
        BigDecimal bigDecimal = generatorFactory.asBigDecimal().get(min, max, scale);
        System.out.println("bigDecimal = " + bigDecimal);

        assertThat(bigDecimal).isInstanceOf(BigDecimal.class).isLessThanOrEqualTo(max).isGreaterThanOrEqualTo(min);
        assertThat(bigDecimal.scale()).isEqualTo(scale);
    }

}
