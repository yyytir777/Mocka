package mocka.generator.primitive;

import mocka.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("StringGenerator Test Code")
class StringGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final StringGenerator stringGenerator = generatorFactory.asString();

    @Test
    @DisplayName("Verify the StringGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        StringGenerator stringGenerator = generatorFactory.asString();
        assertThat(stringGenerator).isInstanceOf(StringGenerator.class);
    }

    @Test
    @DisplayName("StringGenerator is managed as a singleton")
    void stringGenerator_is_singleton() {
        StringGenerator newStringGenerator = generatorFactory.asString();
        assertThat(newStringGenerator).isSameAs(stringGenerator);
    }

    @Test
    @DisplayName("get() returns a string length follows Gaussian distribution")
    void get_string_length_follows_gaussian_distribution() {
        StringGenerator stringGenerator = generatorFactory.asString();
        String string = stringGenerator.get();
        System.out.println("string = " + string);
        assertThat(string).isNotNull().isNotEmpty().isInstanceOf(String.class);
    }

    @Test
    @DisplayName("get(length) returns a string of the specified length")
    void get_string_specified_length() {
        StringGenerator stringGenerator = generatorFactory.asString();
        String string = stringGenerator.get(50);
        assertThat(string.length()).isEqualTo(50);
    }

    @Test
    @DisplayName("get(min, max) returns a string with length between min and max")
    void get_min_max_length() {
        StringGenerator stringGenerator = generatorFactory.asString();
        String string = stringGenerator.get(10, 20);
        assertThat(string.length()).isLessThanOrEqualTo(20).isGreaterThanOrEqualTo(10);
    }

    @Test
    @DisplayName("getUpTo(max) returns a string with length upto max")
    void get_max_length() {
        StringGenerator stringGenerator = generatorFactory.asString();
        String string = stringGenerator.get(100);
        assertThat(string.length()).isLessThanOrEqualTo(100);
    }

    @Test
    @DisplayName("returns String containing characters from given range")
    void get_string_containing_characters_from_given_range() {
        char start = 'a', end = 'z';
        String characters = stringGenerator.getAllCharacter(start, end);
        for(char c = start ; c <= end; c++) {
            assertThat(characters.contains(String.valueOf(c))).isTrue();
        }
    }
}