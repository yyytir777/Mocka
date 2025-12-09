package mocka.core.generator.primitive;

import mocka.core.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CharacterGenerator Test Code")
public class CharacterGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();
    private final CharacterGenerator characterGenerator = generatorFactory.asCharacter();

    @Test
    @DisplayName("CharacterGenerator is managed as a singleton")
    void characterGenerator_is_singleton() {
        CharacterGenerator newCharacterGenerator = generatorFactory.asCharacter();
        assertThat(newCharacterGenerator).isSameAs(characterGenerator);
    }

    @Test
    @DisplayName("returns Character value")
    void get_character() {
        Character c = characterGenerator.get();
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("returns character value between (min, max)")
    void get_character_in_range() {
        char min = 'a', max = 'z';
        Character c = characterGenerator.getCharacter(min, max);
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("returns character element of given character list")
    void get_character_from_list() {
        List<Character> list = List.of('a', 'b', 'c', 'd');
        Character c = characterGenerator.pickFrom(list);

        assertThat(c).isIn(list);
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("returns character element of given character array")
    void get_character_from_array() {
        Character[] characters = {'a', 'b', 'c', 'd'};
        Character c = characterGenerator.pickFrom(characters);

        assertThat(c).isIn(List.of(characters));
        assertThat(Character.isLetter(c)).isTrue();
    }

    @Test
    @DisplayName("returns a character for each locale and range")
    void get_english_character() {
        Character c = characterGenerator.getCharacter(Locale.ENGLISH);
        assertThat(Character.isAlphabetic(c)).isTrue();

        Character c1 = characterGenerator.getCharacter(Locale.KOREAN);
        assertThat(c1).isBetween('가', '힣');

        Character c2 = characterGenerator.getCharacter((char) 0x0000, (char) 0xFFFF);
        assertThat(c2).isInstanceOf(Character.class).isBetween((char) 0x0000, (char) 0xFFFF);
    }

    @Test
    @DisplayName("returns Character value in String")
    void get_character_value_in_string() {
        String input = "characterGenerator";
        Character character = characterGenerator.getCharacter(input);
        assertThat(character).isInstanceOf(Character.class).isIn(input.chars().mapToObj(c -> (char) c).toList());
    }

    @Test
    @DisplayName("returns Character value not in given set")
    void get_character_value_not_in_given_set() {
        String input = "characterGenerator";
        Set<Character> set = input.chars().mapToObj(c -> (char) c).collect(Collectors.toSet());
        Character character = characterGenerator.getCharacterNotIn(set);
        System.out.println("character = " + character);
        assertThat(character).isInstanceOf(Character.class).isNotIn(set);
    }
}
