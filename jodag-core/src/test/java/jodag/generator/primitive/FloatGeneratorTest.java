package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FloatGenerator Test Code")
public class FloatGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final FloatGenerator floatGenerator = generatorFactory.asFloat();

    @Test
    @DisplayName("DoubleGenerator is managed as a singleton")
    void DoubleGenerator_is_singleton() {
        FloatGenerator newFloatGenerator = generatorFactory.asFloat();
        assertThat(newFloatGenerator).isSameAs(floatGenerator);
    }

    @Test
    @DisplayName("returns Float value")
    void get_float() {
        Float f = floatGenerator.get();
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    @Test
    @DisplayName("returns Float value between (min, max)")
    void get_float_in_range() {
        float min = 0, max = 30;
        Float f = floatGenerator.getFloat(min, max);
        assertThat(f).isBetween(min, max);
    }

    @Test
    @DisplayName("returns positive Float value")
    void get_positive_float() {
        Float f = floatGenerator.getPositiveFloat();
        assertThat(f).isPositive();
    }

    @Test
    @DisplayName("returns negative Float value")
    void get_negative_float() {
        Float f = floatGenerator.getNegativeFloat();
        assertThat(f).isNegative();
    }

    @Test
    @DisplayName("returns Float element of given Float list")
    void get_float_from_list() {
        List<Float> list = List.of(1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F);
        Float f = floatGenerator.pickFrom(list);

        assertThat(f).isIn(list);
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    @Test
    @DisplayName("returns Float element of given Float array")
    void get_float_from_array() {
        Float[] floats = {1F, 2F, 3F, 4F, 5F, 6F, 7F, 8F, 9F, 10F};
        Float f = floatGenerator.pickFrom(floats);

        assertThat(f).isIn(List.of(floats));
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);
    }
}
