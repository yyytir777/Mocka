package jodag.generator.array;

import jodag.generator.AbstractGenerator;
import jodag.generator.primitive.CharacterGenerator;

public class CharacterArrayGenerator extends AbstractGenerator<Character[]> {

    private static final CharacterArrayGenerator INSTANCE = new CharacterArrayGenerator();

    private final CharacterGenerator charGenerator;

    private CharacterArrayGenerator() {
        super("character-array", Character[].class);
        charGenerator = CharacterGenerator.getInstance();
    }

    public static CharacterArrayGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Character[] get() {
        return new Character[]{charGenerator.get()};
    }

    public Character[] get(int length) {
        Character[] characters = new Character[length];
        for (int i = 0; i < length; i++) {
            characters[i] = charGenerator.get();
        }
        return characters;
    }
}
