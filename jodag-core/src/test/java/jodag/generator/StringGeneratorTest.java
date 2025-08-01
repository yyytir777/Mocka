package jodag.generator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StringGeneratorTest {

    @Test
    @DisplayName("StringGenerator에서 get()메서드를 호출합니다.")
    void get() {
        StringGenerator stringGenerator = GeneratorFactory.string();
        for(int i = 0; i < 10; i++) {
            String result = stringGenerator.get();
            assertThat(result).isNotNull();
        }
    }

    @Test
    @DisplayName("StringGenerator에서 get(int lenght)메서드를 호출합니다.")
    void getLength() {
        StringGenerator stringGenerator = GeneratorFactory.string();
        String string = stringGenerator.get(50);
        assertThat(string.length()).isEqualTo(50);
    }
}