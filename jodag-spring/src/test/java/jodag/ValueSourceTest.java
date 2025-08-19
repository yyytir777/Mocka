package jodag;

import jodag.entity.ValueSourceEntity;
import jodag.generator.EntityGenerator;
import jodag.generator.GeneratorFactory;
import jodag.generator.SpringGeneratorFactory;
import jodag.generator.registable.RegisterableGenerator;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ValueSource 테스트")
@SpringBootTest(classes = TestConfig.class)
public class ValueSourceTest {

    @BeforeEach
    public void setUp() {
//        GeneratorFactory.register("test-path", "/Users/wonjae/Desktop/text.txt", String.class);
        RegisterableGenerator<String> generator = GeneratorFactory.register("test", "test.txt", String.class);
    }

    @AfterEach
    public void tearDown() {
        GeneratorFactory.clearRegistableGenerator();
    }

    @Test
    @Disabled
    @DisplayName("path, type으로 ValueSource를 정의합니다.")
    void value_source_generate_test() {
        EntityGenerator<ValueSourceEntity> generator = SpringGeneratorFactory.getGenerator(ValueSourceEntity.class);

        ValueSourceEntity valueSourceEntity = generator.get();

        List<String> data = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("/Users/wonjae/Desktop/text.txt"))).lines().toList();

        for(int i = 0; i < 10; i++) {
            String name = valueSourceEntity.getName();
            assertThat(data).contains(name);
        }
    }

    @Test
    @DisplayName("key값으로 ValueSource를 정의합니다.")
    void value_source_generate_test_with_key() {
        EntityGenerator<ValueSourceEntity> generator = SpringGeneratorFactory.getGenerator(ValueSourceEntity.class);

        List<String> testList = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();

        for(int i = 0; i < 10; i++) {
            ValueSourceEntity valueSourceEntity = generator.get();
            String test = valueSourceEntity.getName();
            assertThat(testList).contains(test);
        }
    }
}
