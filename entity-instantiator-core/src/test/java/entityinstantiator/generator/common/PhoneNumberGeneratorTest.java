package entityinstantiator.generator.common;

import entityinstantiator.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("PhoneNumberGenerator Test Code")
class PhoneNumberGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the PhoneNumberGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        PhoneNumberGenerator phoneNumberGenerator = generatorFactory.asPhoneNumber();
        assertThat(phoneNumberGenerator).isInstanceOf(PhoneNumberGenerator.class);
    }

    @Test
    @DisplayName("PhoneNumberGenerator is managed as a singleton")
    void phoneNumberGenerator_is_singleton() {
        PhoneNumberGenerator phoneNumberGenerator1 = generatorFactory.asPhoneNumber();
        PhoneNumberGenerator phoneNumberGenerator2 = generatorFactory.asPhoneNumber();

        assertThat(phoneNumberGenerator1).isNotNull();
        assertThat(phoneNumberGenerator2).isNotNull();
        assertThat(phoneNumberGenerator1).isSameAs(phoneNumberGenerator2);
    }


    @Test
    @DisplayName("PhoneNumberGenerator returns a value in name format")
    void get_value_from_phoneNumberGenerator() {
        PhoneNumberGenerator phoneNumberGenerator = generatorFactory.asPhoneNumber();

        String phoneNumber = phoneNumberGenerator.get();
        assertThat(phoneNumber).isNotEmpty();
    }

    @Test
    @DisplayName("PhoneNumberGenerator returns values in name format")
    void get_values_from_phoneNumberGenerator() {
        PhoneNumberGenerator phoneNumberGenerator = generatorFactory.asPhoneNumber();

        for(int i = 0; i < 1000; i++) {
            assertThat(phoneNumberGenerator.get()).isNotEmpty();
        }
    }
}