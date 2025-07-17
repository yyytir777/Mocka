package jodag.generator.primitive;

import jodag.generator.Generator;
import jodag.generator.PrimitiveType;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("BooleanGenerator 테스트입니다.")
class BooleanGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("BooleanGenerator 인스턴스를 가져옵니다.(dataRegistry에 기본으로 생성되어 있습니다.)")
    void return_boolean_generator_instance() {
        // given & when
        Generator<Boolean> generator = dataRegistry.getGenerator(PrimitiveType.Boolean);

        // then
        assertThat(generator).isInstanceOf(BooleanGenerator.class);
    }

    @Test
    @DisplayName("BooleanGenerator에서 get()을 호출하여 랜덤한 값을 생성합니다.")
    void generate_different_values_on_each_get_call() {
        // given & when
        Generator<Boolean> generator = dataRegistry.getGenerator(PrimitiveType.Boolean);

        // then
        Boolean bool = generator.get();
        assertThat(bool).isIn(Boolean.TRUE, Boolean.FALSE);
    }

    @Test
    @DisplayName("get()메서드 1000번 호출하여 ture, false 생성 횟수를 확인합니다.")
    void check_count_of_true_or_false_by_invoking_get_method() {
        Generator<Boolean> generator = dataRegistry.getGenerator(PrimitiveType.Boolean);

        int trueCnt  = 0, falseCnt = 0;
        for(int i = 0; i < 1000; i++) {
            if(generator.get()) {
                trueCnt++;
            } else {
                falseCnt++;
            }
        }

        assertThat(trueCnt + falseCnt).isEqualTo(1000);
        assertThat(trueCnt).isGreaterThan(0);
        assertThat(falseCnt).isGreaterThan(0);
    }
}