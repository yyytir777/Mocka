package jodag.generator.primitive;

import jodag.generator.Generator;
import jodag.generator.PrimitiveType;
import jodag.registry.DataRegistry;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CharacterGenerator 테스트입니다.")
class CharacterGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("CharacterGenerator 인스턴스를 가져옵니다.")
    void getInstance() {
        // given & when
        Generator<Character> generator = dataRegistry.getGenerator(PrimitiveType.Character);

        // then
        assertThat(generator).isNotNull();
    }

    @Test
    @DisplayName("get()메서드를 호출하여 랜덤한 Character값을 가져옵니다. (알파벳 한정)")
    void generate_random_values_on_each_get_call() {
        // given & when
        Generator<Character> generator = dataRegistry.getGenerator(PrimitiveType.Character);

        // then
        Character c = generator.get();
        assertThat(c).isGreaterThanOrEqualTo('A').isLessThanOrEqualTo('z');
    }

    @Test
    @DisplayName("get()메서드를 1000번 호출하여 리턴된 값 범위 체크")
    void check_range_by_invoking_get_method() {
        Generator<Character> generator = dataRegistry.getGenerator(PrimitiveType.Character);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 1000; i++) {
            Character c = generator.get();
            sb.append(c);
            assertThat(c).isGreaterThanOrEqualTo('A').isLessThanOrEqualTo('z');
        }

        String result = sb.toString();
        assertThat(result).hasSize(1000);
    }
}