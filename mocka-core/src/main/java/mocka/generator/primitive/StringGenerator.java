package mocka.generator.primitive;

import mocka.generator.AbstractGenerator;


public class StringGenerator extends AbstractGenerator<String> {

    private static final StringGenerator INSTANCE = new StringGenerator();

    private StringGenerator() {
        super("string", String.class);
    }

    public static StringGenerator getInstance() {
        return INSTANCE;
    }

    /**
     * returns a random string length follows a Gaussian distribution
     */
    @Override
    public String get() {
        int length = (int) randomProvider.getGaussian(7, 2);
        return getString(Math.max(1, length));
    }

    /**
     * returns a random string length with the specified fixed length
     */
    public String get(int length) {
        return getString(length);
    }

    /**
     * Returns a random string length within the range [min, max]
     */
    public String get(int min, int max) {
        return getString(randomProvider.getInt(min, max + 1));
    }

    /**
     * Returns a random string of length up to max
     */
    public String getUpTo(int max) {
        double biased = Math.pow(randomProvider.getDouble(1), 2); // 작은 값에 편향
        int length = 1 + (int)(biased * (max - 1)); // 최소 1, 최대 max
        return getString(length);
    }

    /** returns a string containing all characters in the Unicode range [a,b] */
    public String getAllCharacter(char a, char b) {
        StringBuilder sb = new StringBuilder();
        for (int i = a; i <= b; i++) {
            sb.append((char) i);
        }
        return sb.toString();
    }

    private String getString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(CharacterGenerator.getInstance().getCharacter());
        }
        return sb.toString();
    }

}
