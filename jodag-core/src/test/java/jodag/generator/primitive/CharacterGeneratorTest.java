package jodag.generator.primitive;

import jodag.generator.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CharacterGenerator 테스트")
public class CharacterGeneratorTest {

    private final PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

    @Test
    @DisplayName("Primitive 인스턴스 반환")
    void get_instance() {
        assertThat(primitiveGenerator).isInstanceOf(PrimitiveGenerator.class);
    }

    @Test
    @DisplayName("Character 값 반환")
    void get_character() {
        Character c = primitiveGenerator.getCharacter();
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("min ~ max 사이 Character 값 반환")
    void get_character_in_range() {
        char min = 0x0000, max = 0xFFFF;
        Character c = primitiveGenerator.getCharacter(min, max);
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("주어진 List에서 선택")
    void get_character_from_list() {
        List<Character> list = List.of('a', 'b', 'c', 'd');
        Character c = primitiveGenerator.pickFrom(list);

        assertThat(c).isIn(list);
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("주어진 Character 배열에서 선택")
    void get_character_from_array() {
        Character[] characters = {'a', 'b', 'c', 'd'};
        Character c = primitiveGenerator.pickFrom(characters);

        assertThat(c).isIn(characters);
        assertThat(Character.isLetter(c)).isTrue();
    }
}
