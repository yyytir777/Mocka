package jodag.generator.array;

import jodag.generator.AbstractGenerator;
import jodag.generator.primitive.ByteGenerator;

public class ByteArrayGenerator extends AbstractGenerator<Byte[]> {

    private static final ByteArrayGenerator INSTANCE = new ByteArrayGenerator();

    private final ByteGenerator byteGenerator;

    private ByteArrayGenerator() {
        super("byte-array", Byte[].class);
        byteGenerator = ByteGenerator.getInstance();
    }

    public static ByteArrayGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Byte[] get() {
        return new Byte[]{byteGenerator.get()};
    }

    public Byte[] get(int length) {
        Byte[] array = new Byte[length];
        for(int i = 0; i < length; i++) {
            array[i] = byteGenerator.get();
        }
        return array;
    }
}
