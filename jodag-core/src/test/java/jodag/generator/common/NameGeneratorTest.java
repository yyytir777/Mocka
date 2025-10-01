package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NameGenerator Test Code")
class NameGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the NameGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        NameGenerator nameGenerator = generatorFactory.asName();
        assertThat(nameGenerator).isInstanceOf(NameGenerator.class);
    }

    @Test
    @DisplayName("NameGenerator is managed as a singleton")
    void nameGenerator_is_singleton() {
        NameGenerator nameGenerator1 = generatorFactory.asName();
        NameGenerator nameGenerator2 = generatorFactory.asName();

        assertThat(nameGenerator1).isNotNull();
        assertThat(nameGenerator2).isNotNull();
        assertThat(nameGenerator1).isSameAs(nameGenerator2);
    }


    @Test
    @DisplayName("NameGenerator returns a value in name format")
    void get_value_from_emailGenerator() {
        NameGenerator nameGenerator = generatorFactory.asName();

        String name = nameGenerator.get();
        assertThat(name).isNotEmpty();
    }

    @Test
    @DisplayName("NameGenerator returns values in name format")
    void get_values_from_nameGenerator() {
        NameGenerator nameGenerator = generatorFactory.asName();

        for(int i = 0; i < 1000; i++) {
            String name = nameGenerator.get();
            System.out.println("name = " + name);
            assertThat(name).isNotEmpty();
        }
    }
}