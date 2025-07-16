package jodag.generator.common;

import jodag.generator.GenerateType;
import jodag.generator.Generator;
import jodag.registry.DataRegistry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LoremIpsumGenerator 테스트 클래스입니다.")
class LoremIpsumGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("LoremIpsumGenerator 인스턴스를 반환한다.")
    void return_lorem_ipsum_generator_instance() {
        // given & when
        Generator<String> loremIpsumGenerator = dataRegistry.getGenerator(GenerateType.LOREM_IPSUM);

        // then
        assertThat(loremIpsumGenerator).isInstanceOf(LoremIpsumGenerator.class);
    }

    @Test
    @DisplayName("LoremIpsumGenerator에서 get()을 호출하여 랜덤한 값을 반환합니다.")
    void generate_different_values_on_each_get_call() {
        // given
        Generator<String> loremIpsumGenerator = dataRegistry.getGenerator(GenerateType.LOREM_IPSUM);

        // when
        String loremIpsum1 = loremIpsumGenerator.get();
        String loremIpsum2 = loremIpsumGenerator.get();

        assertThat(loremIpsum1).isNotNull();
        assertThat(loremIpsum2).isNotNull();
        assertThat(loremIpsum1).isNotEqualTo(loremIpsum2);
    }
}