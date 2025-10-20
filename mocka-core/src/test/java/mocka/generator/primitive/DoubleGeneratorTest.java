package mocka.generator.primitive;

import mocka.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DoubleGenerator Test Code")
public class DoubleGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final DoubleGenerator doubleGenerator = generatorFactory.asDouble();

    @RepeatedTest(10)
    @DisplayName("DoubleGenerator is managed as a singleton")
    void DoubleGenerator_is_singleton() {
        DoubleGenerator newDoubleGenerator = generatorFactory.asDouble();
        assertThat(newDoubleGenerator).isSameAs(doubleGenerator);
    }

    @RepeatedTest(10)
    @DisplayName("return Double value")
    void get_double() {
        Double d = doubleGenerator.get();
        assertThat(d).isInstanceOf(Double.class);
    }

    @RepeatedTest(10)
    @DisplayName("returns Double value between (min, max)")
    void get_double_in_range() {
        double min = 0, max = 30;
        Double d = doubleGenerator.getDouble(min, max);
        assertThat(d).isInstanceOf(Double.class).isBetween(min, max);
    }

    @RepeatedTest(10)
    @DisplayName("returns positive double value")
    void get_positive_double() {
        Double d = doubleGenerator.getPositiveDouble();
        assertThat(d).isInstanceOf(Double.class).isPositive();
    }

    @RepeatedTest(10)
    @DisplayName("returns negative double value")
    void get_negative_double() {
        Double d = doubleGenerator.getNegativeDouble();
        assertThat(d).isInstanceOf(Double.class).isNegative();
    }

    @RepeatedTest(10)
    @DisplayName("returns double element of given double list")
    void get_double_from_list() {
        List<Double> list = List.of(1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D);
        Double d = doubleGenerator.pickFrom(list);

        assertThat(d).isInstanceOf(Double.class).isIn(list);
    }

    @RepeatedTest(10)
    @DisplayName("returns double element of given double array")
    void get_double_from_array() {
        Double[] doubles = {1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D};
        Double d = doubleGenerator.pickFrom(doubles);

        assertThat(d).isInstanceOf(Double.class).isIn(List.of(doubles));
    }
}
