package jodag.valueSourceTest;

import jodag.JodagTestApplication;
import jodag.file.PathResourceLoader;
import jodag.entity.*;
import jodag.exception.GeneratorException;
import jodag.generator.factory.GeneratorRegistry;
import jodag.generator.EntityGenerator;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.*;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@DisplayName("ValueSource Test Code")
@SpringBootTest(classes = JodagTestApplication.class)
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
        EntityGenerator<ValueSource1> generator = springGeneratorFactory.getGenerator(ValueSource1.class);
        ValueSource1 valueSource1 = generator.get();
        System.out.println("valueSource1 = " + valueSource1);

        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
        String name = valueSource1.getName();
        assertThat(test).contains(name);
    }

    @Test
    @DisplayName("throws GeneratorException when key matches no generator")
    void throw_generatorException_when_key_matches_generator() {
        EntityGenerator<ValueSource4> generator = springGeneratorFactory.getGenerator(ValueSource4.class);
        assertThatThrownBy(generator::get).isInstanceOf(GeneratorException.class);
    }

    @Test
    @DisplayName("success generates ValueSource2 instance using path, type value (@ValueSource)")
    void value_source_generate_test_with_key() {
        EntityGenerator<ValueSource2> generator = springGeneratorFactory.getGenerator(ValueSource2.class);
        ValueSource2 valueSource2 = generator.get();
        System.out.println("valueSource2 = " + valueSource2);

        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
        String name = valueSource2.getName();
        assertThat(test).contains(name);
    }

    @Test
    @DisplayName("when path, type, generatorKey is given in @ValueSource annotation, regard generatorKey first. ((path, type) refers test.txt, generatorKey refers test1.txt)")
    void value_source_generate_test_with_key_throw() {
        EntityGenerator<ValueSource3> generator = springGeneratorFactory.getGenerator(ValueSource3.class);
        ValueSource3 valueSource3 = generator.get();
        System.out.println("valueSource3 = " + valueSource3);

        List<String> test1 = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test1.txt"))).lines().toList();
        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
        String name = valueSource3.getName();
        System.out.println("name = " + name);
        assertThat(test).isNotEmpty()
                .doesNotContain(name);
        assertThat(test1).contains(name);
    }

    @Test
    @DisplayName("throws GeneratorException if the file does not exist or is inaccessible")
    void throw_exception_if_the_file_does_not_exist() {
        EntityGenerator<ValueSource4> generator = springGeneratorFactory.getGenerator(ValueSource4.class);
        assertThatThrownBy(generator::get).isInstanceOf(GeneratorException.class);
    }

    @Test
    @DisplayName("success generates ValueSource5 instance using key value (@ValueSource) refers CommonGenerator")
    void value_source_generate_test_with_common_generator() {
        EntityGenerator<ValueSource5> generator = springGeneratorFactory.getGenerator(ValueSource5.class);
        ValueSource5 valueSource5 = generator.get();

        assertThat(valueSource5.getName()).matches("[A-Za-z]+( [A-Za-z]+)*");
        assertThat(valueSource5.getEmail()).matches("[A-Za-z0-9_]{5,10}@[A-Za-z0-9_]{3,7}\\.[a-zA-Z]{2,5}");
        System.out.println("valueSource5 = " + valueSource5);
    }
}
