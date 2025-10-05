package entityinstantiator.generator.primitive;

import entityinstantiator.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LongGenerator Test Code")
public class LongGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final LongGenerator longGenerator = generatorFactory.asLong();

    @Test
    @DisplayName("LongGenerator is managed as a singleton")
    void LongGenerator_is_singleton() {
        LongGenerator newLongGenerator = generatorFactory.asLong();
        assertThat(newLongGenerator).isSameAs(longGenerator);
    }

    @Test
    @DisplayName("returns Long value")
    void get_long() {
        Long l = longGenerator.get();
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    @DisplayName("returns Long value between (min, max)")
    void get_long_in_range() {
        long min = 0, max = 30;
        Long l = longGenerator.getLong(min, max);
        assertThat(l).isBetween(min, max);
    }

    @Test
    @DisplayName("returns positive Long value")
    void get_positive_long() {
        Long l = longGenerator.getPositiveLong();
        assertThat(l).isPositive();
    }

    @Test
    @DisplayName("returns negative Long value")
    void get_negative_long() {
        Long l = longGenerator.getNegativeLong();
        assertThat(l).isNegative();
    }

    @Test
    @DisplayName("returns even Long value")
    void get_even_long() {
        Long l = longGenerator.getEvenLong();
        assertThat(l).isEven();
    }

    @Test
    @DisplayName("returns odd Long value")
    void get_odd_long() {
        Long l = longGenerator.getOddLong();
        assertThat(l).isOdd();
    }

    @Test
    @DisplayName("returns Long element of given Long list")
    void get_long_from_list() {
        List<Long> list = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        Long l = longGenerator.pickFrom(list);

        assertThat(l).isIn(list);
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    @Test
    @DisplayName("returns Long element of given Long array")
    void get_long_from_array() {
        Long[] longs = {1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L};
        Long l = longGenerator.pickFrom(longs);

        assertThat(l).isIn(List.of(longs));
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);
    }
}
