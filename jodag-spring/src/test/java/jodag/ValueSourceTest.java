package jodag;

import jodag.entity.associations.Parent;
import jodag.generator.EntityGenerator;
import jodag.generator.GeneratorFactory;
import jodag.generator.SpringGeneratorFactory;
import jodag.generator.registable.RegisterableGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ValueSource 테스트")
@Disabled
@SpringBootTest(classes = TestConfig.class)
public class ValueSourceTest {

    @BeforeEach
    public void setUp() {
        GeneratorFactory.register("test-path", "/Users/wonjae/Desktop/text.txt", String.class);
        GeneratorFactory.register("test", "test.txt", String.class);
    }

    @AfterEach
    public void tearDown() {
        GeneratorFactory.clearRegistableGenerator();
    }

    @Test
    @DisplayName("path, type으로 ValueSource를 정의합니다.")
    void value_source_generate_test() {
        EntityGenerator<Parent> generator = SpringGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get();

        List<String> data = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("/Users/wonjae/Desktop/text.txt"))).lines().toList();

        for(int i = 0; i < 10; i++) {
            String name = parent.getName();
            assertThat(data).contains(name);
        }
    }

    @Test
    @DisplayName("key값으로 ValueSource를 정의합니다.")
    void value_source_generate_test_with_key() {
        EntityGenerator<Parent> generator = SpringGeneratorFactory.getGenerator(Parent.class);

        Parent parent = generator.get();

        List<String> testList = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();

        for(int i = 0; i < 10; i++) {
            String test = parent.getTest();
            assertThat(testList).contains(test);
        }
    }
}
