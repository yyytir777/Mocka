package jodag.generator.primitive;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ByteGenerator Test Code")
public class ByteGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final ByteGenerator byteGenerator = generatorFactory.asByte();

    @Test
    @DisplayName("ByteGenerator is managed as a singleton")
    void ByteGenerator_is_singleton() {
        ByteGenerator newByteGenerator = generatorFactory.asByte();
        assertThat(newByteGenerator).isSameAs(byteGenerator);
    }

    @Test
    @DisplayName("returns Byte value")
    void get_byte() {
        Byte b = byteGenerator.get();
        assertThat(b).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Test
    @DisplayName("returns byte value between (min, max)")
    void get_byte_in_range() {
        byte min = 0, max = 30;
        Byte b = byteGenerator.getByte(min, max);
        assertThat(b).isBetween(min, max);
    }

    @Test
    @DisplayName("returns positive Byte value")
    void get_positive_byte() {
        Byte b = byteGenerator.getPositiveByte();
        assertThat(b).isPositive();
    }

    @Test
    @DisplayName("returns negative Byte value")
    void get_negative_byte() {
        Byte b = byteGenerator.getNegativeByte();
        assertThat(b).isNegative();
    }

    @Test
    @DisplayName("returns even Byte value")
    void get_even_byte() {
        Byte b = byteGenerator.getEvenByte();
        assertThat(b).isEven();
    }

    @Test
    @DisplayName("returns odd Byte value")
    void get_odd_byte() {
        Byte b = byteGenerator.getOddByte();
        assertThat(b).isOdd();
    }

    @Test
    @DisplayName("returns byte element of given Byte list")
    void get_byte_from_list() {
        List<Byte> list = List.of((byte) 1, (byte) 2, (byte) 3);
        Byte b = byteGenerator.pickFrom(list);

        assertThat(b).isIn(list).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Test
    @DisplayName("returns byte element of given Byte array")
    void get_byte_from_array() {
        Byte[] bytes = {(byte) 1, (byte) 2, (byte) 3};
        Byte b = byteGenerator.pickFrom(bytes);

        assertThat(b).isIn(List.of(bytes)).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);
    }
}
