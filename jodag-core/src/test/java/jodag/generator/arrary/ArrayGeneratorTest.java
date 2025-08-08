package jodag.generator.arrary;

import jodag.generator.GeneratorFactory;
import jodag.generator.array.ArrayGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ArrayGenerator 테스트")
public class ArrayGeneratorTest {

    @Test
    @DisplayName("GeneratorFactory를 통해 가져온 ArrayGenerator가 해당 클래스인지 확인")
    public void get_instance() {
        ArrayGenerator arrayGenerator = GeneratorFactory.array();

        assertThat(arrayGenerator).isInstanceOf(ArrayGenerator.class);
    }

    @Test
    @DisplayName("ArrayGenerator는 싱글톤으로 관리")
    void arrayGenerator_is_singleton() {
        ArrayGenerator arrayGenerator = GeneratorFactory.array();
        ArrayGenerator arrayGenerator1 = GeneratorFactory.array();

        assertThat(arrayGenerator).isSameAs(arrayGenerator1);
    }

    @Test
    @DisplayName("ArrayGenerator에서 배열 형태의 랜덤배열 값 리턴")
    void get_value_from_arrayGenerator() {
        ArrayGenerator arrayGenerator = GeneratorFactory.array();

        byte[] byteArray = arrayGenerator.getPrimitiveByteArray();
        assertThat(byteArray).isNotNull();

        char[] characterArray = arrayGenerator.getPrimitiveCharArray();
        assertThat(characterArray).isNotNull();
    }

    @Test
    @DisplayName("ArrayGenerator에서 일정 크기의 랜덤배열 값 리턴")
    void get_value_from_arrayGenerator_when_given_length() {
        ArrayGenerator arrayGenerator = GeneratorFactory.array();

        byte[] byteArray = arrayGenerator.getPrimitiveByteArray(10);
        assertThat(byteArray.length).isEqualTo(10);

        char[] characterArray = arrayGenerator.getPrimitiveCharArray(20);
        assertThat(characterArray.length).isEqualTo(20);
    }
}
