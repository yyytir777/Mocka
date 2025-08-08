package jodag.generator.array;


public class ArrayGenerator {

    private static final ArrayGenerator INSTANCE = new ArrayGenerator();

    private final ByteArrayGenerator byteArrayGenerator = ByteArrayGenerator.getInstance();
    private final CharacterArrayGenerator characterArrayGenerator = CharacterArrayGenerator.getInstance();

    private ArrayGenerator() {}

    public static ArrayGenerator getInstance() {
        return INSTANCE;
    }

    public byte[] getPrimitiveByteArray() {
        Byte[] byteArray = byteArrayGenerator.get();
        byte[] result = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            result[i] = byteArray[i];
        }
        return result;
    }

    public byte[] getPrimitiveByteArray(int length) {
        return byteArrayGenerator.get(length);
    }

    public char[] getPrimitiveCharArray() {
        Character[] characterArray = characterArrayGenerator.get();
        char[] result = new char[characterArray.length];
        for (int i = 0; i < characterArray.length; i++) {
            result[i] = characterArray[i];
        }
        return result;
    }

    public char[] getPrimitiveCharArray(int length) {
        return characterArrayGenerator.get(length);
    }
}
