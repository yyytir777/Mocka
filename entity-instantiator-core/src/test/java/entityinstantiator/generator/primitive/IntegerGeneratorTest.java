package entityinstantiator.generator.primitive;

import entityinstantiator.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("IntegerGenerator Test Code")
public class IntegerGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final IntegerGenerator integerGenerator = generatorFactory.asInteger();

    @Test
    @DisplayName("IntegerGenerator is managed as a singleton")
    void IntegerGenerator_is_singleton() {
        IntegerGenerator newIntegerGenerator = generatorFactory.asInteger();
        assertThat(newIntegerGenerator).isSameAs(integerGenerator);
    }

    @Test
    @DisplayName("returns Integer value")
    void get_integer() {
        Integer integer = integerGenerator.get();
        assertThat(integer).isInstanceOf(Integer.class);
    }

    @Test
    @DisplayName("returns Integer value between (min, max)")
    void get_integer_in_range() {
        int min = 0, max = 30;
        Integer integer = integerGenerator.getInteger(min, max);
        assertThat(integer).isInstanceOf(Integer.class).isBetween(min, max);
    }

    @Test
    @DisplayName("returns positive Integer value")
    void get_positive_integer() {
        Integer integer = integerGenerator.getPositiveInteger();
        assertThat(integer).isInstanceOf(Integer.class).isPositive();
    }

    @Test
    @DisplayName("returns negative Integer value")
    void get_negative_integer() {
        Integer integer = integerGenerator.getNegativeInteger();
        assertThat(integer).isInstanceOf(Integer.class).isNegative();
    }

    @Test
    @DisplayName("returns even Integer value")
    void get_even_integer() {
        Integer integer = integerGenerator.getEvenInteger();
        assertThat(integer).isInstanceOf(Integer.class).isEven();
    }

    @Test
    @DisplayName("returns odd Integer value")
    void get_odd_integer() {
        Integer integer = integerGenerator.getOddInteger();
        assertThat(integer).isInstanceOf(Integer.class).isOdd();
    }

    @Test
    @DisplayName("returns Integer element of given Integer list")
    void get_integer_from_list() {
        List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer integer = integerGenerator.pickFrom(list);

        assertThat(integer).isInstanceOf(Integer.class).isIn(list);
    }

    @Test
    @DisplayName("returns Integer element of given Integer array")
    void get_integer_from_array() {
        Integer[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer integer = integerGenerator.pickFrom(integers);

        assertThat(integer).isInstanceOf(Integer.class).isIn(List.of(integers));
    }
}
