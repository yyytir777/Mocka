package jodag.generator.primitive;


import java.util.Locale;

public class CharacterGenerator extends AbstractPrimitiveGenerator<Character> {

    private static CharacterGenerator INSTANCE;

    private CharacterGenerator() {
        super(Character.class);
    }

    public static synchronized CharacterGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CharacterGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Character get() {
        return randomProvider.nextCharacter(Locale.ENGLISH);
    }
}
