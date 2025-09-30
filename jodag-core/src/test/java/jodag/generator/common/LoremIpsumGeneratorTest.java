package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("LoremIpsumGenerator Test")
class LoremIpsumGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the LoremIpsumGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        // given & when
        LoremIpsumGenerator loremIpsumGenerator = generatorFactory.asLoremIpsum();

        // then
        assertThat(loremIpsumGenerator).isInstanceOf(LoremIpsumGenerator.class);
    }

    @Test
    @DisplayName("LoremIpsumGenerator is managed as a singleton")
    void loremIpsumGenerator_is_singleton() {
        // given & when
        LoremIpsumGenerator loremIpsumGenerator1 = generatorFactory.asLoremIpsum();
        LoremIpsumGenerator loremIpsumGenerator2 = generatorFactory.asLoremIpsum();

        // then
        assertThat(loremIpsumGenerator1).isNotNull();
        assertThat(loremIpsumGenerator2).isNotNull();
        assertThat(loremIpsumGenerator1).isSameAs(loremIpsumGenerator2);
    }


    @Test
    @DisplayName("LoremIpsumGenerator returns loremIpsum")
    void get_value_from_loremIpsumGenerator() {
        // given & when
        LoremIpsumGenerator loremIpsumGenerator = generatorFactory.asLoremIpsum();

        // then
        String loremIpsum = loremIpsumGenerator.get();
        System.out.println("loremIpsum = " + loremIpsum);
        assertThat(loremIpsum).isNotEmpty();
    }

    @Test
    @DisplayName("LoremIpsumGenerator returns loremIpsums")
    void get_values_from_loremIpsumGenerator() {
        // given & when
        LoremIpsumGenerator loremIpsumGenerator = generatorFactory.asLoremIpsum();

        // then
        for(int i = 0; i < 1000; i++) {
            String loremIpsum = loremIpsumGenerator.get();
            System.out.println("loremIpsum = " + loremIpsum);
            assertThat(loremIpsum).isNotEmpty().isInstanceOf(String.class);
        }
    }}