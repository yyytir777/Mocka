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

    public byte[] getPrimitiveByteArray() {
        Byte[] byteArray = getByteArray();
        byte[] result = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            result[i] = byteArray[i];
        }
        return result;
    }

    public char[] getPrimitiveCharArray() {
        Character[] characterArray = getCharacterArray();
        char[] result = new char[characterArray.length];
        for (int i = 0; i < characterArray.length; i++) {
            result[i] = characterArray[i];
        }
        return result;
    }
}
