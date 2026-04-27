package mocka.odm.regexSourceTest;

import mocka.core.generator.Generator;
import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.regexsource.RegexSourceDocument;
import mocka.odm.document.regexsource.RegexSourceFailDocument;
import mocka.odm.generator.DocumentGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@DisplayName("RegexSource Test Code")
@SpringBootTest(classes = MockaOdmTestApplication.class)
public class RegexSourceTest {

    @Autowired
    DocumentGeneratorFactory generatorFactory;

    @Test
    @DisplayName("returns matching string of the given pattern")
    public void returns_matching_string_of_given_pattern() {
        Generator<RegexSourceDocument> generator = generatorFactory.getGenerator(RegexSourceDocument.class);
        assertThat(generator.get().getDescription()).matches("[0-9]{10}");
    }

    @Test
    @DisplayName("throw IllegalArgumentException via invalid regex grammar")
    public void throw_exception_when_regex_generator_throws_IllegalArgumentException() {
        Generator<RegexSourceFailDocument> generator = generatorFactory.getGenerator(RegexSourceFailDocument.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
