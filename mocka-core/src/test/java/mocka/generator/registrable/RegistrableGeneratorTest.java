package mocka.generator.registrable;

import mocka.exception.GeneratorException;
import mocka.generator.Generator;
import mocka.generator.factory.GeneratorFactory;
import mocka.generator.factory.GeneratorRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


@DisplayName("RegisterableGenerator Test Code")
class RegistrableGeneratorTest {

    @BeforeEach
    void setUp() {
        GeneratorFactory.clearAllRegistrableGenerator();
    }

    @Test
    @DisplayName("path type supports classpath")
    void get_path_with_classpath() {
        GeneratorFactory.putGenerator(new RegistrableGenerator<>("test", "test.txt", String.class));

        Generator<String> generator = GeneratorRegistry.getGenerator("test");
        String string = generator.get();
        assertThat(string).isNotNull();
    }

    @Test
    @Disabled
    @DisplayName("path type supports absolute path but couldn't test in github")
    void get_path_with_absolute_path() {
        Generator<String> generator = GeneratorRegistry.getGenerator("test", "/Users/wonjae/Desktop/text.txt", String.class);
        String string = generator.get();
        assertThat(string).isNotNull();
    }

    @Test
    @DisplayName("throws GeneratorException when no files in the path")
    void get_path_with_classpath_exception() {
        assertThatThrownBy(() -> GeneratorRegistry.getGenerator("test", "asdf.txt", String.class))
                .isInstanceOf(GeneratorException.class);

        assertThatThrownBy(() -> GeneratorRegistry.putGenerator(new RegistrableGenerator<>("test", "asdf.txt", String.class)))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    @Disabled
    @DisplayName("path type supports absolute path. throws GeneratorException when no files in the path. but couldn't test in github")
    void get_path_with_absolute_path_throw_exception() {
        assertThatThrownBy(() -> GeneratorRegistry.getGenerator("test", "/Users/wonjae/Desktop/asdf.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }
}