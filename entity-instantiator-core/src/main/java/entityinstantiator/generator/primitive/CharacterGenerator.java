package entityinstantiator.generator.primitive;

import entityinstantiator.generator.AbstractGenerator;

import java.util.List;
import java.util.Locale;
import java.util.Set;

public class CharacterGenerator extends AbstractGenerator<Character> {

    private static final CharacterGenerator INSTANCE = new CharacterGenerator();

    private CharacterGenerator() {
        super("character", Character.class);
    }

    public static CharacterGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random English alphabet character (a-z or A-Z) */
    @Override
    public Character get() {
        return getCharacter();
    }

    /** returns a random character based on the given locale. (english or korean) */
    public Character getCharacter(Locale locale) {
        if(locale == Locale.ENGLISH) {
            return getCharacter();
        } else if(locale == Locale.KOREAN) {
            return getCharacter((char) 0xAC00, (char) 0xD7A3); // 한글 완성형 범위
        } else return getCharacter();
    }

    /** returns a random English alphabet character ([a-z] or [A-Z])*/
    public Character getCharacter() {
        return randomProvider.getBoolean() ? getCharacter('a', 'z') : getCharacter('A', 'Z');
    }

    /** returns a random character between the given min and max values [min, mix] */
    public Character getCharacter(Character min, Character max) {
        int range = (max - min) + 1;
        return (char) (randomProvider.getInt(range) + min);
    }

    /** picks a random character from the given list */
    public Character pickFrom(List<Character> list) {
        return list.get(randomProvider.getInt(list.size()));
    }

    /** picks a random character from the given array */
    public Character pickFrom(Character[] characters) {
        return characters[randomProvider.getInt(characters.length)];
    }

    /** picks a random character from the given string */
    public Character getCharacter(String input) {
        return input.charAt(randomProvider.getInt(input.length()));
    }

    /** picks a random character not contained in the given set */
    public Character getCharacterNotIn(Set<Character> set) {
        char c;
        do {
            c = (char) (32 + randomProvider.getNextIdx(95));
        } while (set.contains(c));
        return c;
    }
}
