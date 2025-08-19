package jodag.generator;

import jodag.exception.GeneratorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class GeneratorFactoryTest {

    @BeforeEach
    void setUp() {
        GeneratorFactory.clearRegistableGenerator();
    }

    @Test
    void getCommonGenerator() {
        String key = "name";
        Generator<String> namegenerator = GeneratorFactory.getCommonGenerator(key);
        assertNotNull(namegenerator);
        String name = namegenerator.get();
        System.out.println("name = " + name);
        assertNotNull(name);

    }

    @Test
    void getRegistableGenerator() {
        GeneratorFactory.register("test", "name.txt", String.class);
        Generator<String> test = GeneratorFactory.getRegistableGenerator("test");
        String string = test.get();
        System.out.println("string = " + string);
        assertNotNull(string);
    }

    @Test
    void throwExceptionGetRegistableGenerator() {
        GeneratorFactory.register("test1", "name.txt", String.class);
        assertThatThrownBy(() -> GeneratorFactory.getRegistableGenerator("test"))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    void throwExceptionGetRegistableGeneratorWhenCannotFindPath() {
        GeneratorFactory.register("test1", "name.txt", String.class);
        assertThatThrownBy(() -> GeneratorFactory.getRegistableGenerator("/temp/name.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    void testGetRegistableGenerator() {
        GeneratorFactory.register("test", "name.txt", String.class);
        Generator<String> registableGenerator = GeneratorFactory.getRegistableGenerator("name.txt", String.class);
        assertNotNull(registableGenerator);
    }

    @Test
    void register() {
        GeneratorFactory.register("test", "name.txt", String.class);
        Generator<String> test = GeneratorFactory.getRegistableGenerator("test");
        assertNotNull(test);
    }

    @Test
    void throwExceptionWhenDuplicateGenerator() {
        GeneratorFactory.register("test", "name.txt", String.class);
        assertThatThrownBy(() -> GeneratorFactory.register("test", "name.txt", String.class))
                .isInstanceOf(GeneratorException.class);
    }

    @Test
    void getCommonGeneratorKeys() {
        List<String> commonGeneratorKeys = GeneratorFactory.getCommonGeneratorKeys();
        assertThat(commonGeneratorKeys).isInstanceOf(List.class);
    }

    @Test
    void getRegistableGeneratorNames() {
        List<String> registableGeneratorNames = GeneratorFactory.getRegistableGeneratorNames();
        assertThat(registableGeneratorNames).isInstanceOf(List.class);
    }

    @Test
    void existsGenerator() {
        GeneratorFactory.register("test", "name.txt", String.class);
        boolean test = GeneratorFactory.existsGenerator("test");
        assertThat(test).isTrue();

        boolean name = GeneratorFactory.existsGenerator("name");
        assertThat(name).isTrue();

        boolean tmp = GeneratorFactory.existsGenerator("tmp");
        assertThat(tmp).isFalse();
    }
}