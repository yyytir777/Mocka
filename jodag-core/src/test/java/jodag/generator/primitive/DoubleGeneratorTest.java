package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DoubleGenerator Test Code")
public class DoubleGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final DoubleGenerator doubleGenerator = generatorFactory.asDouble();

    @Test
    @DisplayName("DoubleGenerator is managed as a singleton")
    void DoubleGenerator_is_singleton() {
        DoubleGenerator newDoubleGenerator = generatorFactory.asDouble();
        assertThat(newDoubleGenerator).isSameAs(doubleGenerator);
    }

    @Test
    @DisplayName("return Double value")
    void get_double() {
        Double d = doubleGenerator.get();
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    @DisplayName("returns Double value between (min, max)")
    void get_double_in_range() {
        double min = 0, max = 30;
        Double d = doubleGenerator.getDouble(min, max);
        assertThat(d).isBetween(min, max);
    }

    @Test
    @DisplayName("returns positive double value")
    void get_positive_double() {
        Double d = doubleGenerator.getPositiveDouble();
        assertThat(d).isPositive();
    }

    @Test
    @DisplayName("returns negative double value")
    void get_negative_double() {
        Double d = doubleGenerator.getNegativeDouble();
        assertThat(d).isNegative();
    }

    @Test
    @DisplayName("returns double element of given double list")
    void get_double_from_list() {
        List<Double> list = List.of(1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D);
        Double d = doubleGenerator.pickFrom(list);

        assertThat(d).isIn(list);
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    @DisplayName("returns double element of given double array")
    void get_double_from_array() {
        Double[] doubles = {1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D};
        Double d = doubleGenerator.pickFrom(doubles);

        assertThat(d).isIn(List.of(doubles));
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }
}
