package jodag.generator.regex;

import jodag.generator.GeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

public class RegexGeneratorTest {

    @Test
    public void test() {
        RegexGenerator generator = GeneratorFactory.regex();
        Pattern pattern = Pattern.compile("\\w+");
        String regex = generator.get(pattern);
        Assertions.assertThat(regex).isNotNull().matches(pattern);
    }
}
