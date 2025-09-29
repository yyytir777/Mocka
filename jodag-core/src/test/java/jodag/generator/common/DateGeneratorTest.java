package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DisplayName("DateGenerator Test")
class DateGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final DateGenerator dateGenerator = generatorFactory.asDate();

    List<String> formats = List.of("yyyy-MM-dd", "MM/dd/yyyy", "yyMMdd", "yyyy-MM-dd HH:mm:ss",
            "dd-MM-yyyy", "yyyy/MM/dd", "HH:mm", "HH:mm:ss.SSS", "hh:mm a", "yyyyMMddHHmmss", "yyyy-MM-dd'T'HH:mm:ss");

    List<String> regexes = List.of("\\d{4}-\\d{2}-\\d{2}", "\\d{2}/\\d{2}/\\d{4}",
            "\\d{6}", "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}", "\\d{2}-\\d{2}-\\d{4}",
            "\\d{4}/\\d{2}/\\d{2}", "\\d{2}:\\d{2}", "\\d{2}:\\d{2}:\\d{2}\\.\\d{3}",
            "\\d{2}:\\d{2} (AM|PM)", "\\d{14}", "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}");

    @Test
    @DisplayName("DateGenerator is managed as a singleton")
    void dateGenerator_is_singleton() {
        DateGenerator newDateGenerator = generatorFactory.asDate();
        assertThat(newDateGenerator).isSameAs(dateGenerator);
    }

    @Test
    @DisplayName("returns formatted date string based on given pattern")
    void dateGenerator_formatted_date_string_based_on_given_pattern() {
        for (int i = 0; i < formats.size(); i++) {
            String result = dateGenerator.get(formats.get(i));
            System.out.println("result = " + result);
            assertThat(result).matches(regexes.get(i));
        }
    }
}