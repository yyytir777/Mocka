package jodag.generator.arrary;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArrayGenerator Test Code")
public class ArrayGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("return random byte array from arrayGenerator")
    void get_value_from_arrayGenerator() {

        byte[] byteArray = generatorFactory.asByteArray().get();
        System.out.println("byteArray = " + Arrays.toString(byteArray));
        assertThat(byteArray).isNotNull();

        char[] characterArray = generatorFactory.asCharacterArray().get();
        System.out.println("characterArray = " + Arrays.toString(characterArray));
        assertThat(characterArray).isNotNull();
    }

    @Test
    @DisplayName("return random byte array of a specified size from ArrayGenerator")
    void get_value_from_arrayGenerator_when_given_length() {
        byte[] byteArray = generatorFactory.asByteArray().get(10);
        assertThat(byteArray.length).isEqualTo(10);

        char[] characterArray = generatorFactory.asCharacterArray().get(20);
        assertThat(characterArray.length).isEqualTo(20);
    }
}
