package jodag.generator.arrary;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArrayGenerator Test Code")
public class ArrayGeneratorTest {

    private static final Integer LENGTH = 20;
    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("return random primitive array from arrayGenerator")
    void get_primitive_value_from_arrayGenerator() {

        byte[] byteArray = generatorFactory.asByteArray().get();
        System.out.println("byteArray = " + Arrays.toString(byteArray));
        assertThat(byteArray).isNotNull();

        char[] characterArray = generatorFactory.asCharacterArray().get();
        System.out.println("characterArray = " + Arrays.toString(characterArray));
        assertThat(characterArray).isNotNull();
    }

    @Test
    @DisplayName("return random primitive array of a specified size from ArrayGenerator")
    void get_primitive_value_from_arrayGenerator_when_given_length() {
        byte[] byteArray = generatorFactory.asByteArray().get(LENGTH);
        assertThat(byteArray.length).isEqualTo(LENGTH);

        char[] characterArray = generatorFactory.asCharacterArray().get(LENGTH);
        assertThat(characterArray.length).isEqualTo(LENGTH);
    }

    @Test
    @DisplayName("return random wrapper array from arrayGenerator")
    void get_wrapper_value_from_arrayGenerator() {

        Byte[] byteArray = generatorFactory.asByteArray().getByte();
        System.out.println("byteArray = " + Arrays.toString(byteArray));
        assertThat(byteArray).isNotNull();

        Character[] characterArray = generatorFactory.asCharacterArray().getCharacter();
        System.out.println("characterArray = " + Arrays.toString(characterArray));
        assertThat(characterArray).isNotNull();
    }

    @Test
    @DisplayName("return random wrapper array of a specified size from ArrayGenerator")
    void get_wrapper_value_from_arrayGenerator_when_given_length() {
        Byte[] byteArray = generatorFactory.asByteArray().getByte(LENGTH);
        assertThat(byteArray.length).isEqualTo(LENGTH);

        Character[] characterArray = generatorFactory.asCharacterArray().getCharacter(LENGTH);
        assertThat(characterArray.length).isEqualTo(LENGTH);
    }
}
