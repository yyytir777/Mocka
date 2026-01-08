package mocka.core.generator.array;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.primitive.ByteGenerator;

public class ByteArrayGenerator extends AbstractGenerator<byte[]> {

    private static final ByteArrayGenerator INSTANCE = new ByteArrayGenerator();

    private final ByteGenerator byteGenerator;

    private ByteArrayGenerator() {
        super("byte_array", byte[].class);
        byteGenerator = ByteGenerator.getInstance();
    }

    public static ByteArrayGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * Returns a random byte array with a default length of 10
     */
    @Override
    public byte[] get() {
        return get(10);
    }

    /**
     * Returns a random byte array with the specified length
     */
    public byte[] get(int length) {
        byte[] array = new byte[length];
        for(int i = 0; i < length; i++) {
            array[i] = byteGenerator.get();
        }
        return array;
    }

    /**
     * Returns a random Byte array with a default length of 10
     */
    public Byte[] getByte() {
        return getByte(10);
    }

    /**
     * Returns a random Byte array with the specified length
     */
    public Byte[] getByte(int length) {
        Byte[] array = new Byte[length];
        for(int i = 0; i < length; i++) {
            array[i] = byteGenerator.get();
        }
        return array;
    }
}
