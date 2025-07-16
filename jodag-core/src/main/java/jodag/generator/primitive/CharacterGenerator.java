package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

public class CharacterGenerator extends AbstractGenerator<Character> {

    private static CharacterGenerator INSTANCE;

    private CharacterGenerator() {
        super("char", Character.class);
    }

    public static synchronized CharacterGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CharacterGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Character get() {
        return randomProvider.nextCharacter();
    }
}
