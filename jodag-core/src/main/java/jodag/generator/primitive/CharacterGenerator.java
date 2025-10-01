package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

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

    @Override
    public Character get() {
        return getCharacter(Locale.ENGLISH);
    }

    public Character getCharacter(Locale locale) {
        if(locale == Locale.ENGLISH) {
            return randomProvider.getBoolean() ? getCharacter('a', 'z') : getCharacter('A', 'Z');
        } else if(locale == Locale.KOREAN) {
            return getCharacter((char) 0xAC00, (char) 0xD7A3); // 한글 완성형 범위
        } else return getCharacter();
    }

    public Character getCharacter() {
        boolean aBoolean = randomProvider.getBoolean();
        if(aBoolean) {
            return getCharacter('a', 'z');
        } else {
            return getCharacter('A', 'Z');
        }
    }

    public Character getCharacter(char min, char max) {
        int range = (max - min) + 1;
        return (char) (randomProvider.getInt(range) + min);
    }

    public Character pickFrom(List<Character> list) {
        return list.get(randomProvider.getInt(list.size()));
    }

    public Character pickFrom(Character[] characters) {
        return characters[randomProvider.getInt(characters.length)];
    }

    public Character getCharacter(String input) {
        return input.charAt(randomProvider.getInt(input.length()));
    }

    public Character getCharacterNotIn(Set<Character> set) {
        char c;
        do {
            c = (char) (32 + randomProvider.getNextIdx(95));
        } while (set.contains(c));
        return c;
    }

    public String getAllCharacter(int a, int b) {
        StringBuilder sb = new StringBuilder();
        for (int i = a; i <= b; i++) {
            sb.append((char) i);
        }
        return sb.toString();
    }
}
