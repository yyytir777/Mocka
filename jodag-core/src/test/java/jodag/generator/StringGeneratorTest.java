package jodag.generator;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StringGeneratorTest {

    @Test
    void get() {
        StringGenerator stringGenerator = GeneratorFactory.string();
        for(int i = 0; i < 10; i++) {
            String result = stringGenerator.get();
            System.out.println("result.length() = " + result.length());
            System.out.println("result = " + result);
            assertThat(result).isNotNull();
        }
    }
}