package jodag.generator.common;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("NameGenerator Test")
class NameGeneratorTest {

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 NameGenerator가 해당 클래스인지 확인")
    void get_instance() {
        // given & when
        NameGenerator nameGenerator = GeneratorFactory.name();

        // then
        assertThat(nameGenerator).isInstanceOf(NameGenerator.class);
    }

    @Test
    @DisplayName("NameGenerator는 싱글톤으로 관리")
    void nameGenerator_is_singleton() {
        // given & when
        NameGenerator nameGenerator1 = GeneratorFactory.name();
        NameGenerator nameGenerator2 = GeneratorFactory.name();

        // then
        assertThat(nameGenerator1).isNotNull();
        assertThat(nameGenerator2).isNotNull();
        assertThat(nameGenerator1).isSameAs(nameGenerator2);
    }


    @Test
    @DisplayName("NameGenerator에서 이름 형식의 값을 리턴")
    void get_value_from_emailGenerator() {
        // given & when
        NameGenerator nameGenerator = GeneratorFactory.name();

        // then
        String name = nameGenerator.get();
        assertThat(name).isNotEmpty();
    }

    @Test
    @DisplayName("NameGenerator에서 여러 개의 값 리턴")
    void get_values_from_nameGenerator() {
        // given & when
        NameGenerator nameGenerator = GeneratorFactory.name();

        // then
        for(int i = 0; i < 1000; i++) {
            String name = nameGenerator.get();
            System.out.println("name = " + name);
            assertThat(name).isNotEmpty();
        }
    }
}