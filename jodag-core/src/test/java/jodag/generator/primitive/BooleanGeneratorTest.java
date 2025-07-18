package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BooleanGenerator 테스트")
public class BooleanGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("PrimitiveGenerator 인스턴스 반환")
    void get_instance() {
        Assertions.assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Boolean 값 반환")
    void get_boolean() {
        Boolean bool = primitiveGenerator.getBoolean();
        Assertions.assertThat(bool).isIn(Boolean.TRUE, Boolean.FALSE);
    }
}
