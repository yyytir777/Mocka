package mocka.generator.regex;

import mocka.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@DisplayName("RegexGenerator Test")
public class RegexGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final RegexGenerator regexGenerator = generatorFactory.asRegex();
    private static final List<String> LIST_OF_REGEX = List.of(
            "a", "abc", "xyz",
            "\\d", "\\D", "\\w", "\\W", "\\s", "\\S",

            "\\d+", "\\d*", "\\d?",
            "[a-z]{3}", "[A-Z]{2,4}", "\\w{5,}", "\\s{1,3}",
            "[0-9]{2,}",

            "[abc]", "[^abc]", "[a-z]", "[A-Z]", "[0-9]", "[a-zA-Z0-9_]",
            "[a-f0-9]", "[^A-F0-9]",

            "(abc)", "(a|b)", "(foo|bar)", "(\\d{2}|[A-Z]{2})",
            "(hello|world|hi)",

            "((ab)c)", "((1|2)(3|4))",

            "\\d{3}[A-Z]", "[a-z]{2}\\d{2}", "(ab){2}", "([A-Z]\\d){3}",
            "hello\\s+world", "[a-zA-Z]{5}\\d?", "[A-Z][a-z]{2,5}\\d{1,3}",

            "\\.", "\\+", "\\*", "\\?", "\\(", "\\)", "\\[", "\\]", "\\{", "\\}", "\\|", "\\^", "\\-",

            "colou?r", "gr(a|e)y", "(http|https)://[a-z]+\\.com", "[A-Z]{2}\\d{4}[a-z]{2}", "([0-9]{2}-){2}[0-9]{4}",
            "(19|20)\\d{2}", "[가-힣]{2,4}", "[ㄱ-ㅎㅏ-ㅣ가-힣]+", "[一-龯]", "[ぁ-んァ-ン]", "[А-Яа-я]{3}"
    );

    private static final List<String> INVALID_REGEX = List.of(
            "{As", "{10,10,10}"
    );

    @Test
    @DisplayName("return instance of RegexGenerator.class")
    void get_instance() {
        assertThat(regexGenerator).isInstanceOf(RegexGenerator.class);
    }

    @Test
    @DisplayName("RegexGenerator is managed as a singleton")
    void RegexGenerator_is_singleton() {
        RegexGenerator newRegexGenerator = generatorFactory.asRegex();
        assertThat(newRegexGenerator).isSameAs(regexGenerator);
    }

    @Test
    @DisplayName("Generated string matches regex pattern")
    void matches_pattern_with_return_value_of_regexGenerator() {
        for (String regex : LIST_OF_REGEX) {
            Pattern pattern = Pattern.compile(regex);
            String result = regexGenerator.get(regex);
            System.out.println("result = " + result + "  regex = " + regex);
            assertThat(result).matches(pattern);
        }
    }

    @Test
    @DisplayName("invoking RegexGenerator의 get() method throws UnsupportedOperationException()")
    void invoke_get_method_of_regexGenerator_throws_unsupportedOperationException() {
        Assertions.assertThrows(UnsupportedOperationException.class, regexGenerator::get);
    }

    @Test
    @DisplayName("throws IllegalArgumentException via invalid regex grammar")
    void throws_IllegalArgumentException_via_invalid_regex_grammar() {
        for (String invalidRegex : INVALID_REGEX) {
            assertThatThrownBy(() -> regexGenerator.get(invalidRegex))
            .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
