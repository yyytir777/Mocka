package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LongGenerator 테스트")
public class LongGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Long 값 반환")
    void get_long() {
        Long l = primitiveGenerator.getLong();
        assertThat(l).isLessThanOrEqualTo(Long.MAX_VALUE).isGreaterThanOrEqualTo(Long.MIN_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Long 값 반환")
    void get_long_in_range() {
        long min = 0, max = 30;
        Long l = primitiveGenerator.getLong(min, max);
        assertThat(l).isLessThanOrEqualTo(max).isGreaterThanOrEqualTo(min);
    }

    @Test
    @DisplayName("양수 Long 값 반환")
    void get_positive_long() {
        Long l = primitiveGenerator.getPositiveLong();
        assertThat(l).isPositive();
    }

    @Test
    @DisplayName("음수 Long 값 반환")
    void get_negative_long() {
        Long l = primitiveGenerator.getNegativeLong();
        assertThat(l).isNegative();
    }

    @Test
    @DisplayName("짝수 Long 값 반환")
    void get_even_long() {
        Long l = primitiveGenerator.getEvenLong();
        assertThat(l).isEven();
    }

    @Test
    @DisplayName("홀수 Long 값 반환")
    void get_odd_long() {
        Long l = primitiveGenerator.getOddLong();
        assertThat(l).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_long_from_list() {
        List<Long> list = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        Long l = primitiveGenerator.pickFrom(list);

        assertThat(l).isIn(list);
        assertThat(l).isGreaterThanOrEqualTo(Long.MIN_VALUE).isLessThanOrEqualTo(Long.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Long배열에서 선택")
    void get_long_from_array() {
        Long[] longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};
        Long l = primitiveGenerator.pickFrom(longs);

        assertThat(l).isIn(List.of(longs));
        assertThat(l).isGreaterThanOrEqualTo(Long.MIN_VALUE).isLessThanOrEqualTo(Long.MAX_VALUE);
    }
}
