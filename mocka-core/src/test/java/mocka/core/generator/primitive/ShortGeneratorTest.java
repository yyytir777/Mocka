package mocka.core.generator.primitive;

import mocka.core.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ShortGenerator Test Code")
public class ShortGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final ShortGenerator shortGenerator = generatorFactory.asShort();

    @Test
    @DisplayName("ShortGenerator is managed as a singleton")
    void ShortGenerator_is_singleton() {
        ShortGenerator newShortGenerator = generatorFactory.asShort();
        assertThat(newShortGenerator).isSameAs(shortGenerator);
    }

    @Test
    @DisplayName("returns Short value")
    void get_short() {
        Short s = generatorFactory.asShort().get();
        assertThat(s).isInstanceOf(Short.class);
    }

    @Test
    @DisplayName("returns Short value between (min, max)")
    void get_short_in_range() {
        short min = 0, max = 30;
        Short s = generatorFactory.asShort().getShort(min, max);
        assertThat(s).isInstanceOf(Short.class).isBetween(min, max);
    }

    @Test
    @DisplayName("returns positive Short value")
    void get_positive_short() {
        Short s = generatorFactory.asShort().getPositiveShort();
        assertThat(s).isInstanceOf(Short.class).isPositive();
    }

    @Test
    @DisplayName("returns negative Short value")
    void get_negative_short() {
        Short s = generatorFactory.asShort().getNegativeShort();
        assertThat(s).isInstanceOf(Short.class).isNegative();
    }

    @Test
    @DisplayName("returns even Short value")
    void get_even_short() {
        Short s = generatorFactory.asShort().getEvenShort();
        assertThat(s).isInstanceOf(Short.class).isEven();
    }

    @Test
    @DisplayName("returns odd Short value")
    void get_odd_short() {
        Short s = generatorFactory.asShort().getOddShort();
        assertThat(s).isInstanceOf(Short.class).isOdd();
    }

    @Test
    @DisplayName("returns Short element of given Short list")
    void get_short_from_list() {
        List<Short> list = List.of((short) 1, (short) 2, (short) 3);
        Short s = generatorFactory.asShort().pickFrom(list);

        assertThat(s).isInstanceOf(Short.class).isIn(list);
    }

    @Test
    @DisplayName("returns Short element of given Short array")
    void get_short_from_array() {
        Short[] shorts = {(short) 1, (short) 2, (short) 3};
        Short s = generatorFactory.asShort().pickFrom(shorts);

        assertThat(s).isInstanceOf(Short.class).isIn(List.of(shorts));
    }
}
