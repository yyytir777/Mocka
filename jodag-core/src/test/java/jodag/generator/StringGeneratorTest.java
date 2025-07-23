package jodag.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StringGeneratorTest {

    @Test
    void get() {
        StringGenerator stringGenerator = GeneratorFactory.string();
        for(int i = 0; i < 10; i++) {
            String result = stringGenerator.get();
            assertThat(result).isNotNull();
        }
    }
}