package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ByteGenerator 테스트")
public class ByteGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Byte 값 반환")
    void get_byte() {
        Byte b = primitiveGenerator.getByte();
        assertThat(b).isLessThanOrEqualTo(Byte.MAX_VALUE).isGreaterThanOrEqualTo(Byte.MIN_VALUE);
    }

    @Test
    @DisplayName("min ~ max 사이 byte 값 반환")
    void get_byte_in_range() {
        byte min = 0, byte max = 30;
        Byte b = primitiveGenerator.getByte(min, max);
        assertThat(b).isLessThanOrEqualTo(min).isGreaterThanOrEqualTo(max);
    }

    @Test
    @DisplayName("양수 byte값 반환")
    void get_positive_byte() {
        Byte b = primitiveGenerator.getPositiveByte();
        assertThat(b).isPositive();
    }

    @Test
    @DisplayName("음수 byte값 반환")
    void get_negative_byte() {
        Byte b = primitiveGenerator.getNegativeByte();
        assertThat(b).isNegative();
    }

    @Test
    @DisplayName("짝수 byte값 반환")
    void get_even_byte() {
        Byte b = primitiveGenerator.getEvenByte();
        assertThat(b).isEven();
    }

    @Test
    @DisplayName("홀수 byte값 반환")
    void get_odd_byte() {
        Byte b = primitiveGenerator.getOddByte();
        assertThat(b).isOdd();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_byte_from_list() {
        List<Byte> list = List.of((byte) 1, (byte) 2, (byte) 3);
        Byte b = primitiveGenerator.pickFrom(list);

        assertThat(b).isIn(list);
        assertThat(b).isLessThanOrEqualTo(Byte.MAX_VALUE).isGreaterThanOrEqualTo(Byte.MIN_VALUE);
    }

    @Test
    @DisplayName("주어진 Byte... 에서 선택")
    void get_byte_from_array() {
        Byte[] bytes = {(byte) 1, (byte) 2, (byte) 3};
        Byte b = primitiveGenerator.pickFrom(bytes);

        assertThat(b).isIn(bytes);
        assertThat(b).isLessThanOrEqualTo(Byte.MAX_VALUE).isGreaterThanOrEqualTo(Byte.MIN_VALUE);
    }
}
