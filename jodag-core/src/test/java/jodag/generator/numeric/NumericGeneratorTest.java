package jodag.generator.numeric;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.*;

@DisplayName("NumericGenerator 테스트")
public class NumericGeneratorTest {

    private final NumericGenerator numericGenerator = GeneratorFactory.numeric();

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 NumericGenerator가 해당 클래스인지 확인")
    void get_instance() {

        assertThat(numericGenerator).isInstanceOf(NumericGenerator.class);
    }

    @Test
    @DisplayName("BigInteger 반환")
    void get_bigInteger() {
        BigInteger bigInteger = numericGenerator.getBigInteger();
        System.out.println("bigInteger = " + bigInteger);
        assertThat(bigInteger).isInstanceOf(BigInteger.class);
    }

    @Test
    @DisplayName("BigDecimal 반환")
    void get_bigDecimal() {
        BigDecimal bigDecimal = numericGenerator.getBigDecimal();
        System.out.println("bigDecimal = " + bigDecimal);
        assertThat(bigDecimal).isInstanceOf(BigDecimal.class);
    }
}
