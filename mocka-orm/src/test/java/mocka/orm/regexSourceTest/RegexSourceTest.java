package mocka.orm.regexSourceTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.entity.regexsource.RegexSourceEntity;
import mocka.orm.entity.regexsource.RegexSourceFailEntity;
import mocka.core.generator.Generator;
import mocka.orm.generator.EntityGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("ValueSource Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class RegexSourceTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("returns matching string of the given pattern")
    public void returns_matching_string_of_given_pattern() {
        Generator<RegexSourceEntity> generator = generatorFactory.getGenerator(RegexSourceEntity.class);
        assertThat(generator.get().getDescription()).matches("[0-9]{10}");
    }

    @Test
    @DisplayName("throw IllegalArgumentException via invalid regex grammar")
    public void throw_exception_when_regex_generator_throws_IllegalArgumentException() {
        Generator<RegexSourceFailEntity> generator = generatorFactory.getGenerator(RegexSourceFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
