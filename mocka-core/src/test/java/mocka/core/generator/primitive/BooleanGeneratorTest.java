package mocka.core.generator.primitive;

import mocka.core.generator.factory.GeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BooleanGenerator Test Code")
public class BooleanGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final BooleanGenerator booleanGenerator = generatorFactory.asBoolean();

    @Test
    @DisplayName("BooleanGenerator is managed as a singleton")
    void booleanGenerator_is_singleton() {
        BooleanGenerator newBooleanGenerator = generatorFactory.asBoolean();
        Assertions.assertThat(booleanGenerator).isSameAs(newBooleanGenerator);
    }

    @Test
    @DisplayName("return Boolean value")
    void get_boolean() {
        Boolean bool = booleanGenerator.get();
        Assertions.assertThat(bool).isInstanceOf(Boolean.class);
    }
}
