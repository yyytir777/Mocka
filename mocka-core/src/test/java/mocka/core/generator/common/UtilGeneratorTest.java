package mocka.core.generator.common;

import mocka.core.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@DisplayName("UtilGenerator Test Code")
class UtilGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the UtilGenerator retreived through GeneratorFactory is the corect class")
    void get_instance() {
        UtilGenerator utilGenerator = generatorFactory.asUtil();
        assertThat(utilGenerator).isInstanceOf(UtilGenerator.class);
    }

    @Test
    @DisplayName("UtilGenerator is manged as a singleton")
    void UtilGenerator_is_Singleton() {
        UtilGenerator utilGenerator1 = generatorFactory.asUtil();
        UtilGenerator utilGenerator2 = generatorFactory.asUtil();
        assertThat(utilGenerator1).isSameAs(utilGenerator2);
    }

    @Test
    @DisplayName("throws UnsupportedException when invoke get()")
    void get_method_throws_unsupported_exception() {
        UtilGenerator utilGenerator = generatorFactory.asUtil();
        assertThatThrownBy(utilGenerator::get)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("getLocale() method returns random Locale value")
    void get_locale_method_returns_random_locale_value() {
        UtilGenerator utilGenerator = generatorFactory.asUtil();
        Locale locale = utilGenerator.getLocale();
        assertThat(locale).isNotNull();
        assertThat(locale).isInstanceOf(Locale.class);
    }

    @Test
    @DisplayName("getCurrency() method returns random Currency value")
    void get_currency_method_returns_random_currency_value() {
        UtilGenerator utilGenerator = generatorFactory.asUtil();
        Currency currency = utilGenerator.getCurrency();
        assertThat(currency).isNotNull();
        assertThat(currency).isInstanceOf(Currency.class);
    }
}
