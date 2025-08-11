package jodag.generator.registable;

import jodag.exception.generator.GeneratorException;
import jodag.generator.Generator;
import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("RegistableGenerator 테스트")
class RegisterableGeneratorTest {

    @Test
    @DisplayName("파일 등록")
    void register_generator() {
        Generator<String> testGenerator = GeneratorFactory.getRegistableGenerator("test", "test.txt", String.class);
        assertThat(testGenerator).isInstanceOf(RegisterableGenerator.class);
    }

    @Test
    @DisplayName("존재하지 않는 파일 등록 시 GeneratorException 에러 발생")
    void throw_exception_when_there_is_no_existed_file() {

        assertThatThrownBy(() -> GeneratorFactory.getRegistableGenerator("fail_test", "fail_test.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    @DisplayName("RegistableGenerator 랜덤 값 리턴")
    void return_random_value_on_registable_generator() {
        Generator<String> generator = GeneratorFactory.getRegistableGenerator("test", "test.txt", String.class);
        String string = generator.get();
        assertThat(string).isInstanceOf(String.class);
    }

    @Test
    @DisplayName("RegistableGenerator 랜덤 값 리턴 (반복문)")
    void return_random_value_on_registable_generator_with_iterator() {
        Generator<String> generator = GeneratorFactory.getRegistableGenerator("test", "test.txt", String.class);
        for(int i = 0; i < 10000; i++) {
            String string = generator.get();
            System.out.println("string = " + string);
            assertThat(string).isInstanceOf(String.class);
        }
    }
}