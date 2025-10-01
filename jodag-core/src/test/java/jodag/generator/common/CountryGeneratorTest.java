package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CountryGenerator Test")
class CountryGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the CountryGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        // given & when
        CountryGenerator countryGenerator = generatorFactory.asCountry();

        // then
        assertThat(countryGenerator).isInstanceOf(CountryGenerator.class);
    }

    @Test
    @DisplayName("CountryGenerator is managed as a singleton")
    void countryGenerator_is_singleton() {
        CountryGenerator countryGenerator1 = generatorFactory.asCountry();
        CountryGenerator countryGenerator2 = generatorFactory.asCountry();

        assertThat(countryGenerator1).isNotNull();
        assertThat(countryGenerator2).isNotNull();
        assertThat(countryGenerator1).isSameAs(countryGenerator2);
    }


    @Test
    @DisplayName("CountryGenerator returns random country")
    void get_value_from_countryGenerator() {
        CountryGenerator countryGenerator = generatorFactory.asCountry();
        String name = countryGenerator.get();
        assertThat(name).isNotEmpty();
    }

    @Test
    @DisplayName("CountryGenerator returns random countries")
    void get_values_from_countryGenerator() {
        CountryGenerator countryGenerator = generatorFactory.asCountry();

        for(int i = 0; i < 1000; i++) {
            String country = countryGenerator.get();
            System.out.println("country = " + country);
            assertThat(country).isNotEmpty();
        }
    }}