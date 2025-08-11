package jodag.generator.common;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CountryGenerator Test")
class CountryGeneratorTest {

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 CountryGenerator가 해당 클래스인지 확인")
    void get_instance() {
        // given & when
        CountryGenerator countryGenerator = GeneratorFactory.country();

        // then
        assertThat(countryGenerator).isInstanceOf(CountryGenerator.class);
    }

    @Test
    @DisplayName("CountryGenerator는 싱글톤으로 관리")
    void countryGenerator_is_singleton() {
        // given & when
        CountryGenerator countryGenerator1 = GeneratorFactory.country();
        CountryGenerator countryGenerator2 = GeneratorFactory.country();

        // then
        assertThat(countryGenerator1).isNotNull();
        assertThat(countryGenerator2).isNotNull();
        assertThat(countryGenerator1).isSameAs(countryGenerator2);
    }


    @Test
    @DisplayName("CountryGenerator에서 랜덤 값을 리턴")
    void get_value_from_countryGenerator() {
        // given & when
        CountryGenerator countryGenerator = GeneratorFactory.country();

        // then
        String name = countryGenerator.get();
        assertThat(name).isNotEmpty();
    }

    @Test
    @DisplayName("CountryGenerator에서 여러 개의 값 리턴")
    void get_values_from_countryGenerator() {
        // given & when
        CountryGenerator countryGenerator = GeneratorFactory.country();

        // then
        for(int i = 0; i < 1000; i++) {
            String country = countryGenerator.get();
            System.out.println("country = " + country);
            assertThat(country).isNotEmpty();
        }
    }}