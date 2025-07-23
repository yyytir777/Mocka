package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("IntegerGenerator 테스트")
public class IntegerGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Integer 값 반환")
    void get_integer() {
        Integer integer = primitiveGenerator.getInteger();
        assertThat(integer).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 Integer 값 반환")
    void get_integer_in_range() {
        int min = 0, max = 30;
        Integer integer = primitiveGenerator.getInteger(min, max);
        assertThat(integer).isBetween(min, max);
    }

    @Test
    @DisplayName("양수 Integer 값 반환")
    void get_positive_integer() {
        Integer integer = primitiveGenerator.getPositiveInteger();
        assertThat(integer).isPositive();
    }

    @Test
    @DisplayName("음수 Integer 값 반환")
    void get_negative_integer() {
        Integer integer = primitiveGenerator.getNegativeInteger();
        assertThat(integer).isNegative();
    }

    @Test
    @DisplayName("짝수 Integer 값 반환")
    void get_even_integer() {
        Integer integer = primitiveGenerator.getEvenInteger();
        assertThat(integer).isEven();
    }

    @Test
    @DisplayName("홀수 Integer 값 반환")
    void get_odd_integer() {
        Integer integer = primitiveGenerator.getOddInteger();
        assertThat(integer).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_integer_from_list() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer integer = primitiveGenerator.pickFrom(list);

        assertThat(integer).isIn(list);
        assertThat(integer).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("주어진 Intger배열에서 선택")
    void get_integer_from_array() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer integer = primitiveGenerator.pickFrom(integers);

        assertThat(integer).isIn(List.of(integers));
        assertThat(integer).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
