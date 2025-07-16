package jodag;

import jodag.generator.Generator;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("테스트_주도_개발_V2")
public class JodagV2Test {

    private final static String key = "name";
    private final static Path path = Path.of("src/main/resources/test.txt");

    @BeforeEach
    public void setUp() {
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.clearAll();
    }

    @Test
    @DisplayName("테스트_코드를_시작합니다")
    public void start_test() {
        String args = "테스트 코드 시작";
        System.out.println(args);
        assertThat(args).isNotNull();
    }

    @Test
    @DisplayName("Custom_Generator를_등록합니다.")
    public void register_generator() {
        // given & when
        DataRegistry dataRegistry = DataRegistry.getInstance();

        // then
        assertThatCode(() -> dataRegistry.add(key, path, String.class))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Custom_Generator를_가져옵니다.")
    public void get_generator() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // when
        Generator<?> generator = dataRegistry.getGenerator(key);

        // then
        assertThat(generator).isNotNull();
    }

    @Test
    @DisplayName("key값에_해당하는_Generator가_없을_때_예외가_발생합니다.")
    public void get_generator_throw_exception() {
        // given
        DataRegistry dataRegistry = DataRegistry.getInstance();
        dataRegistry.add(key, path, String.class);

        // when
        Executable executable = () -> dataRegistry.getGenerator("another");

        // then
        assertThrows(RuntimeException.class, executable);
    }
}
