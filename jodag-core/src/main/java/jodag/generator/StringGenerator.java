package jodag.generator;

import jodag.generator.primitive.PrimitiveGenerator;


public class StringGenerator extends AbstractGenerator<String> {

    private static final StringGenerator INSTANCE = new StringGenerator();

    private StringGenerator() {
        super("string", String.class);
    }

    public static StringGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        return get((int) randomProvider.getGaussian(7, 2));
    }

    /**
     * 특정 길이의 문자열을 반환
     * @param length 반환할 문자열의 길이
     * @return length길이의 문자열
     */
    public String get(int length) {
        return getString(length);
    }

    public String get(int min, int max) {
        return getString(randomProvider.getInt(min, max + 1));
    }

    private String getString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(PrimitiveGenerator.getInstance().getCharacter());
        }
        return sb.toString();
    }
}
