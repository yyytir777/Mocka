package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@DisplayName("EmailGenerator Test code")
class EmailGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the EmailGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        // given & when
        EmailGenerator emailGenerator = generatorFactory.asEmail();

        // then
        assertThat(emailGenerator).isInstanceOf(EmailGenerator.class);
    }

    @Test
    @DisplayName("EmailGenerator is managed as a singleton")
    void emailGenerator_is_singleton() {
        // given & when
        EmailGenerator emailGenerator = generatorFactory.asEmail();
        EmailGenerator emailGenerator1 = generatorFactory.asEmail();

        // then
        assertThat(emailGenerator).isSameAs(emailGenerator1);
    }


    @Test
    @DisplayName("EmailGenerator returns a value in email format")
    void get_value_from_emailGenerator() {
        // given
        EmailGenerator emailGenerator = generatorFactory.asEmail();
        Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");

        // when & then
        String email = emailGenerator.get();
        assertThat(email).matches(pattern);
    }

    @Test
    @DisplayName("EmailGenerator returns values in email format")
    void get_values_from_emailGenerator() {
        EmailGenerator emailGenerator = generatorFactory.asEmail();

        for(int i = 0; i < 1000; i++) {
            String email = emailGenerator.get();
            System.out.println("email = " + email);
            assertThat(email).matches("\\w+@\\w+\\.\\w+");
        }
    }
}