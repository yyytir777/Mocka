package jodag.generator.factory;

import jodag.exception.GeneratorException;
import jodag.generator.Generator;
import jodag.generator.primitive.StringGenerator;
import jodag.generator.registrable.RegistrableGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GeneratorRegistry Test Code")
class GeneratorRegistryTest {

    private static final String GENERATOR_KEY = "get_test";
    private static final String GENERATOR_PATH = "test.txt";

    @BeforeEach
    void setUp() {
        GeneratorRegistry.putGenerator(GENERATOR_KEY, "test.txt", String.class);
    }

    @AfterEach
    void tearDown() {
        GeneratorRegistry.clearRegistrableGenerator();
    }

    @Test
    @DisplayName("put RegistrableGenerator")
    void put_RegistrableGenerator_or_AbstractGenerator() {
        RegistrableGenerator<String> registrableGenerator = new RegistrableGenerator<>("test", "test.txt", String.class);
        GeneratorRegistry.putGenerator(registrableGenerator);

        Generator<Object> targetGenerator = GeneratorRegistry.getGenerator("test");
        assertThat(registrableGenerator).isSameAs(targetGenerator);
    }

    @Test
    @DisplayName("put RegistrableGenerator with key, path, type")
    void put_RegistrableGenerator_with_key_path_and_type() {
        String key = "test";
        String path = "test.txt";
        Class<String> type = String.class;

        GeneratorRegistry.putGenerator(key, path, type);

        Generator<String> targetGenerator = GeneratorRegistry.getGenerator(key);
        RegistrableGenerator<String> registrableGenerator = (RegistrableGenerator<String>) targetGenerator;

        assertThat(registrableGenerator.getKey()).isEqualTo(key);
        assertThat(registrableGenerator.getPath()).isEqualTo(path);
        assertThat(registrableGenerator.getType()).isEqualTo(type);
    }

    @Test
    @DisplayName("get Generator by given key")
    void get_Generator_by_given_key() {
        Generator<String> generator = GeneratorRegistry.getGenerator(GENERATOR_KEY);
        assertThat(generator).isNotNull().isInstanceOf(Generator.class);
    }

    @Test
    @DisplayName("creates and returns a new generator when key is not already registered")
    void returns_new_generator_when_key_not_registered() {
        String newKey = GENERATOR_KEY + "1";
        Generator<String> generator = GeneratorRegistry.getGenerator(newKey, GENERATOR_PATH, String.class);
        assertThat(generator).isNotNull().isInstanceOf(Generator.class);
    }

    @Test
    @DisplayName("clears registrableGenerator")
    void clear_registrableGenerator() {
        Generator<Object> generator = GeneratorRegistry.getGenerator(GENERATOR_KEY);
        assertThat(generator).isNotNull().isInstanceOf(Generator.class);

        GeneratorRegistry.clearRegistrableGenerator();

        assertThatThrownBy(() -> GeneratorRegistry.getGenerator(GENERATOR_KEY))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    @DisplayName("returns generator names as List<String>")
    void returns_generator_names_as_list() {
        List<String> generatorNames = GeneratorRegistry.getGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat(generatorNames).isNotEmpty();
    }

    @Test
    @DisplayName("checks whether a Generator with the given key exists")
    void checks_whether_a_Generator_with_key_exists() {
        // there is generator key is "get_test"
        Boolean isExisted = GeneratorRegistry.existsRegistrableGenerator(GENERATOR_KEY);
        assertThat(isExisted).isTrue();

        // no generator key "get_test1"
        isExisted = GeneratorRegistry.existsRegistrableGenerator(GENERATOR_KEY + "1");
        assertThat(isExisted).isFalse();
    }
}