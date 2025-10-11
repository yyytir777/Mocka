package entityinstantiator.regexSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.regexsource.RegexSourceEntity;
import entityinstantiator.entity.regexsource.RegexSourceFailEntity;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("ValueSource Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class RegexSourceTest {

    @Autowired
    SpringGeneratorFactory  springGeneratorFactory;

    @Test
    @DisplayName("returns matching string of the given pattern")
    public void returns_matching_string_of_given_pattern() {
        EntityGenerator<RegexSourceEntity> generator = springGeneratorFactory.getGenerator(RegexSourceEntity.class);
        assertThat(generator.get().getDescription()).matches("[0-9]{10}");
    }

    @Test
    @DisplayName("throw IllegalArgumentException via invalid regex grammar")
    public void throw_exception_when_regex_generator_throws_IllegalArgumentException() {
        EntityGenerator<RegexSourceFailEntity> generator = springGeneratorFactory.getGenerator(RegexSourceFailEntity.class);
        assertThatThrownBy(generator::get)
                .isInstanceOf(IllegalArgumentException.class);
    }
}
