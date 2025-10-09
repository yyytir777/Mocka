package entityinstantiator.valueSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.valuesource.*;
import entityinstantiator.file.PathResourceLoader;
import entityinstantiator.exception.GeneratorException;
import entityinstantiator.generator.factory.GeneratorRegistry;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("ValueSource Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class ValueSourceTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @BeforeEach
    @DisplayName("put RegistrableGenerator in GeneratorRegistry")
    public void setUp() {
        GeneratorRegistry.putGenerator("test", "test.txt", String.class);
        GeneratorRegistry.putGenerator("test1", "test1.txt", String.class);
    }

    @AfterEach
    @DisplayName("Clear RegistrableGenerator each after test")
    public void tearDown() {
        GeneratorRegistry.clearRegistrableGenerator();
    }

    @Test
    @DisplayName("success generates ValueSource1 instance using key value (@ValueSource)")
    void value_source_generate_test() {
        EntityGenerator<ValueSourceSuccess> generator = springGeneratorFactory.getGenerator(ValueSourceSuccess.class);
        ValueSourceSuccess valueSourceSuccess = generator.get();
        System.out.println("valueSource1 = " + valueSourceSuccess);

        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
        String name = valueSourceSuccess.getName();
        assertThat(test).contains(name);
    }

    @Test
    @DisplayName("throws GeneratorException when key matches no generator")
    void throw_generatorException_when_key_matches_generator() {
        EntityGenerator<ValueSourceInvalidMatchFail> generator = springGeneratorFactory.getGenerator(ValueSourceInvalidMatchFail.class);
        assertThatThrownBy(generator::get).isInstanceOf(GeneratorException.class);
    }

    @Test
    @DisplayName("success generates ValueSource2 instance using path, type value (@ValueSource)")
    void value_source_generate_test_with_key() {
        EntityGenerator<ValueSourceUsingKeySuccess> generator = springGeneratorFactory.getGenerator(ValueSourceUsingKeySuccess.class);
        ValueSourceUsingKeySuccess valueSourceUsingKeySuccess = generator.get();
        System.out.println("valueSource2 = " + valueSourceUsingKeySuccess);

        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
        String name = valueSourceUsingKeySuccess.getName();
        assertThat(test).contains(name);
    }

    @Test
    @DisplayName("when path, type, generatorKey is given in @ValueSource annotation, regard generatorKey first. ((path, type) refers test.txt, generatorKey refers test1.txt)")
    void value_source_generate_test_with_key_throw() {
        EntityGenerator<ValueSourceKeyFirstSuccess> generator = springGeneratorFactory.getGenerator(ValueSourceKeyFirstSuccess.class);
        ValueSourceKeyFirstSuccess valueSourceKeyFirstSuccess = generator.get();
        System.out.println("valueSource3 = " + valueSourceKeyFirstSuccess);

        List<String> test1 = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test1.txt"))).lines().toList();
        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
        String name = valueSourceKeyFirstSuccess.getName();
        System.out.println("name = " + name);
        assertThat(test).isNotEmpty()
                .doesNotContain(name);
        assertThat(test1).contains(name);
    }

    @Test
    @DisplayName("success generates instance using key value (@ValueSource) refers CommonGenerator")
    void value_source_generate_test_with_common_generator() {
        EntityGenerator<ValueSourceUsingDefaultSuccess> generator = springGeneratorFactory.getGenerator(ValueSourceUsingDefaultSuccess.class);
        ValueSourceUsingDefaultSuccess valueSourceUsingDefaultSuccess = generator.get();

        assertThat(valueSourceUsingDefaultSuccess.getName()).matches("[A-Za-z]+( [A-Za-z]+)*");
        assertThat(valueSourceUsingDefaultSuccess.getEmail()).matches("[A-Za-z0-9_]{5,10}@[A-Za-z0-9_]{3,7}\\.[a-zA-Z]{2,5}");
        System.out.println("valueSource5 = " + valueSourceUsingDefaultSuccess);
    }
}
