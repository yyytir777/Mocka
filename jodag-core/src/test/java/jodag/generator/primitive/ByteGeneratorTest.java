package jodag.generator.primitive;

import jodag.generator.Generator;
import jodag.generator.PrimitiveType;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ByteGenerator 테스트입니다.")
class ByteGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("ByteGenerator 인스턴스를 가져옵니다.")
    void getInstance() {
        // given & when
        Generator<Byte> generator = dataRegistry.getGenerator(PrimitiveType.Byte);

        // then
        assertThat(generator).isInstanceOf(ByteGenerator.class);
    }

    @Test
    @DisplayName("ByteGenerator에서 get()을 호출하여 랜덤한 값을 생성합니다.")
    void generate_different_values_on_each_get_call() {
        // given & when
        Generator<Byte> generator = dataRegistry.getGenerator(PrimitiveType.Byte);

        // then
        Byte b = generator.get();
        assertThat(b).isGreaterThanOrEqualTo((byte) -128).isLessThanOrEqualTo((byte) 127);
    }

    @Test
    @DisplayName("get()메서드를 1000번 호출하여, Byte의 범위를 체크합니다.")
    void check_range_by_invoking_get_method() {
        Generator<Byte> generator = dataRegistry.getGenerator(PrimitiveType.Byte);

        for(int i = 0; i < 1000; i++) {
            assertThat(generator.get()).isGreaterThanOrEqualTo((byte) -128).isLessThanOrEqualTo((byte) 127);
        }
    }
}