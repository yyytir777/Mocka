package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DoubleGenerator 테스트")
public class DoubleGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Double 값 반환")
    void get_double() {
        Double d = primitiveGenerator.getDouble();
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Double 값 반환")
    void get_double_in_range() {
        double min = 0, max = 30;
        Double d = primitiveGenerator.getDouble(min, max);
        assertThat(d).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 Double 값 반환")
    void get_positive_double() {
        Double d = primitiveGenerator.getPositiveDouble();
        assertThat(d).isPositive();
    }

    @Test
    @DisplayName("음수 Double 값 반환")
    void get_negative_double() {
        Double d = primitiveGenerator.getNegativeDouble();
        assertThat(d).isNegative();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_double_from_list() {
        List<Double> list = List.of(1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D);
        Double d = primitiveGenerator.pickFrom(list);

        assertThat(d).isIn(list);
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Double 배열에서 선택")
    void get_double_from_array() {
        Double[] doubles = {1D, 2D, 3D, 4D, 5D, 6D, 7D, 8D, 9D, 10D};
        Double d = primitiveGenerator.pickFrom(doubles);

        assertThat(d).isIn(List.of(doubles));
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);
    }
}
