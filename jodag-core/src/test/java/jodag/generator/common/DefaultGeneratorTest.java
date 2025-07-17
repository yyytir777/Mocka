package jodag.generator.common;

import jodag.generator.GenerateType;
import jodag.generator.Generator;
import jodag.generator.StringGenerator;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("DefaultGenerator 테스트 클래스입니다.")
class DefaultGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("DefaultGenerator 인스턴스를 반환한다")
    void return_default_generator_instance() {
        // given & when
        StringGenerator defaultGenerator = dataRegistry.getGenerator(GenerateType.DEFAULT);

        // then
        assertThat(defaultGenerator).isInstanceOf(DefaultGenerator.class);
    }

    @Test
    @DisplayName("DefaultGenerator에서 get()을 호출하여 랜덤한 값을 반환합니다.")
    void generate_different_values_on_each_get_call() {
        // given
        Generator<String> defaultGenerator = dataRegistry.getGenerator(GenerateType.NAME);

        // when
        String name1 = defaultGenerator.get();
        String name2 = defaultGenerator.get();

        // then
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("랜덤한 길이의 문자열을 반환합니다.")
    void return_random_length_string() {
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.DEFAULT);
        generator.get(10);
    }
}