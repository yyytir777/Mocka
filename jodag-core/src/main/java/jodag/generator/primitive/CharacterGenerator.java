package jodag.generator.primitive;

import jodag.generator.AbstractGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class CharacterGenerator extends AbstractGenerator<Character> {

    private static CharacterGenerator INSTANCE;

    private CharacterGenerator() {
        super("character", Character.class);
    }

    public static synchronized CharacterGenerator getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CharacterGenerator();
        }
        return INSTANCE;
    }

    @Override
    public Character get() {
        return randomProvider.nextBoolean() ? getCharacter('a', 'z') : getCharacter('A', 'Z');
    }

    public Character getCharacter() {
        return getCharacter((char) 0x0000, (char) 0xFFFF);
    }

    public Character getCharacter(char min, char max) {
        int range = (max - min) + 1;
        return (char) (ThreadLocalRandom.current().nextInt(range) + min);
    }
}
