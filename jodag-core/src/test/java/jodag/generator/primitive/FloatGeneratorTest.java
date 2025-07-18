package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FloatGenerator 테스트")
public class FloatGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Float 값 반환")
    void get_float() {
        Float f = primitiveGenerator.getFloat();
        assertThat(f).isLessThanOrEqualTo(Float.MAX_VALUE).isGreaterThanOrEqualTo(Float.MIN_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Float 값 반환")
    void get_float_in_range() {
        float min = 0, max = 30;
        Float f = primitiveGenerator.getFloat(min, max);
        assertThat(f).isLessThanOrEqualTo(max).isGreaterThanOrEqualTo(min);
    }

    @Test
    @DisplayName("양수 Float 값 반환")
    void get_positive_float() {
        Float f = primitiveGenerator.getPositiveFloat();
        assertThat(f).isPositive();
    }

    @Test
    @DisplayName("음수 Float 값 반환")
    void get_negative_float() {
        Float f = primitiveGenerator.getNegativeFloat();
        assertThat(f).isNegative();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_float_from_list() {
        List<Float> list = List.of(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F);
        Float f = primitiveGenerator.pickFrom(list);

        assertThat(f).isIn(list);
        assertThat(f).isGreaterThanOrEqualTo(Float.MIN_VALUE).isLessThanOrEqualTo(Float.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Float 배열에서 선택")
    void get_float_from_array() {
        Float[] floats = {1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F};
        Float f = primitiveGenerator.pickFrom(floats);

        assertThat(f).isIn(List.of(floats));
        assertThat(f).isGreaterThanOrEqualTo(Float.MIN_VALUE).isLessThanOrEqualTo(Float.MAX_VALUE);
    }
}
