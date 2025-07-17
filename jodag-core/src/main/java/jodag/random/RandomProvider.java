package jodag.random;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomProvider {

    private static final Random DEFAULT_RANDOM = new Random();
    private final Random random;

    private static final char[] ALPHANUM = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final char[] NUMERIC = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] DIGIT = "0123456789".toCharArray();

    public RandomProvider() {
        random = DEFAULT_RANDOM;
    }

    public RandomProvider(long seed) {
        random = new Random(seed);
    }

    public RandomProvider(Random random) {
        this.random = Objects.requireNonNullElse(random, DEFAULT_RANDOM);
    }

    public Integer nextInt(int n) {
        if(n < 0) throw new IllegalArgumentException("0 ~ 양수여야합니다.");
        return random.nextInt(n);
    }

    public Long nextLong(long n) {
        return random.nextLong(n);
    }

    public Double nextDouble(double n) {
        return random.nextDouble(n);
    }

    public Boolean nextBoolean() {
        return random.nextBoolean();
    }

    public Float nextFloat() {
        return random.nextFloat();
    }

    public Short nextShort() {
        return (short) random.nextInt();
    }

    public Long nextLong() {
        return random.nextLong();
    }

    public Byte nextByte() {
        return (byte) random.nextInt();
    }

    public Character nextCharacter() {
        return (char) random.nextInt();
    }

    public Character nextCharacter(Locale locale) {
        if(locale == Locale.ENGLISH) {
            return nextEnglish();
        } else if(locale == Locale.KOREAN) {
            return nextKorean();
        }
        return this.nextCharacter();
    }

    private Character nextKorean() {
        return nextCharRange((char) 0xAC00, (char) 0xD7A3);
    }

    private Character nextEnglish() {
        return random.nextBoolean() ? nextCharRange('A', 'Z') : nextCharRange('a', 'z');
    }

    private Character nextCharRange(char start, char end) {
        return (char) (start + random.nextInt(end - start + 1));
    }

    public Integer nextInteger() {
        return random.nextInt();
    }

    public Double nextDouble() {
        return random.nextDouble();
    }

    public Character nextAlphabet() {
        return  NUMERIC[random.nextInt(NUMERIC.length)];
    }

    public Character nextNum() {
        return DIGIT[random.nextInt(DIGIT.length)];
    }

    public Character nextAlphanum() {
        return ALPHANUM[random.nextInt(ALPHANUM.length)];
    }
}
