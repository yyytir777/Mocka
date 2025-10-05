package entityinstantiator.generator.array;

import entityinstantiator.generator.AbstractGenerator;
import entityinstantiator.generator.primitive.CharacterGenerator;

public class CharacterArrayGenerator extends AbstractGenerator<char[]> {

    private static final CharacterArrayGenerator INSTANCE = new CharacterArrayGenerator();

    private final CharacterGenerator charGenerator;

    private CharacterArrayGenerator() {
        super("character_array", char[].class);
        charGenerator = CharacterGenerator.getInstance();
    }

    public static CharacterArrayGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public char[] get() {
        return get(10);
    }

    public char[] get(int length) {
        char[] characters = new char[length];
        for (int i = 0; i < length; i++) {
            characters[i] = charGenerator.get();
        }
        return characters;
    }

    public Character[] getCharacter() {
        return getCharacter(10);
    }

    public Character[] getCharacter(int length) {
        Character[] characters = new Character[length];
        for (int i = 0; i < length; i++) {
            characters[i] = charGenerator.get();
        }
        return characters;
    }
}
