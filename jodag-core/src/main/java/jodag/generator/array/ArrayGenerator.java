package jodag.generator.array;


public class ArrayGenerator {

    private static final ArrayGenerator INSTANCE = new ArrayGenerator();

    private final ByteArrayGenerator byteArrayGenerator = ByteArrayGenerator.getInstance();
    private final CharacterArrayGenerator characterArrayGenerator = CharacterArrayGenerator.getInstance();

    private ArrayGenerator() {}

    public static ArrayGenerator getInstance() {
        return INSTANCE;
    }

    public Byte[] getByteArray() {
        return byteArrayGenerator.get();
    }

    public Byte[] getByteArray(int length) {
        return byteArrayGenerator.get(length);
    }


    public Character[] getCharacterArray() {
        return characterArrayGenerator.get();
    }

    public Character[] getCharacterArray(int length) {
        return characterArrayGenerator.get(length);
    }
}
