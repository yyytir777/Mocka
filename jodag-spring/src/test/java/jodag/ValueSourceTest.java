package jodag;

import jodag.entity.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.SpringGeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@DisplayName("ValueSource 테스트")
@SpringBootTest(classes = TestConfig.class)
public class ValueSourceTest {

    private List<String> data;

    @BeforeEach
    public void setUp() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("test.txt");
        if(is == null) {
            throw new IllegalStateException();
        }
        this.data = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.toList());
    }

    @Test
    @DisplayName("asdf")
    void value_source_generate_test() {
        EntityGenerator<Parent> generator = SpringGeneratorFactory.getGenerator(Parent.class);
        Parent parent = generator.get();

        String name = parent.getName();
        Assertions.assertThat(data).contains(name);
    }
}
