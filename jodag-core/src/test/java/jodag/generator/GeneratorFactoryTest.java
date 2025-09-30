package jodag.generator;

import jodag.PathResourceLoader;
import jodag.exception.GeneratorException;
import jodag.generator.factory.GeneratorFactory;
import jodag.generator.factory.GeneratorRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class GeneratorFactoryTest {

    @BeforeEach
    void setUp() {
        GeneratorRegistry.putGenerator("test", "test.txt", String.class);
        GeneratorRegistry.putGenerator("test1", "test1.txt", String.class);
    }

    @AfterEach
    void tearDown() {
        GeneratorRegistry.clearRegistrableGenerator();
    }

    @Test
    void getRegistrableGenerator() {
        Generator<String> generator = GeneratorRegistry.getGenerator("test");
        String string = generator.get();
        System.out.println("string = " + string);

        List<String> test = new BufferedReader(new InputStreamReader(PathResourceLoader.getPath("test.txt"))).lines().toList();
    }

    @Test
    void throwExceptionGetRegistrableGenerator() {
        GeneratorRegistry.putGenerator("test1", "name.txt", String.class);
        assertThatThrownBy(() -> GeneratorRegistry.getGenerator("test"))
                .isInstanceOf(GeneratorException.class);
    }

//    @Test
//    void throwExceptionGetRegistrableGeneratorWhenCannotFindPath() {
//        generatorFactory.addRegistrableGenerator("test1", "name.txt", String.class);
//        assertThatThrownBy(() -> generatorFactory.getRegistrableGeneratorByPath("/temp/name.txt"))
//                .isInstanceOf(GeneratorException.class);
//    }
//
//    @Test
//    void testGetRegistrableGenerator() {
//        generatorFactory.addRegistrableGenerator("test", "name.txt", String.class);
//        Generator<String> registrableGenerator = generatorFactory.getRegistrableGeneratorByPath("name.txt");
//        assertNotNull(registrableGenerator);
//    }

    @Test
    void register() {
        GeneratorRegistry.putGenerator("test", "name.txt", String.class);
        Generator<String> test = GeneratorRegistry.getGenerator("test");
        assertNotNull(test);
    }

    @Test
    void throwExceptionWhenDuplicateGenerator() {
        GeneratorRegistry.putGenerator("test", "name.txt", String.class);
        assertThatThrownBy(() -> GeneratorRegistry.putGenerator("test", "name.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    void getRegistrableGeneratorNames() {
        List<String> registrableGeneratorNames = GeneratorRegistry.getGeneratorNames();
        assertThat(registrableGeneratorNames).isInstanceOf(List.class);
    }

    @Test
    void existsGenerator() {
        GeneratorRegistry.putGenerator("test", "name.txt", String.class);
        boolean test = GeneratorRegistry.existsRegistrableGenerator("test");
        assertThat(test).isTrue();

        boolean name = GeneratorRegistry.existsRegistrableGenerator("name");
        assertThat(name).isTrue();

        boolean tmp = GeneratorRegistry.existsRegistrableGenerator("tmp");
        assertThat(tmp).isFalse();
    }
}