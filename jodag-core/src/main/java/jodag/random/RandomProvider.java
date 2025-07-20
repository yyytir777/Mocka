package jodag.random;

import jodag.random.index.RandomIndexProvider;
import jodag.random.index.ThreadLocalRandomIndexProvider;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomProvider {

    private final RandomIndexProvider randomIndexProvider;
    private static final char[] ALPHANUM = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final char[] NUMERIC = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] DIGIT = "0123456789".toCharArray();

    public RandomProvider() {
        this.randomIndexProvider = ThreadLocalRandomIndexProvider.getInstance();
    }

    public Character nextCharacter(Locale locale) {
        if(locale == Locale.ENGLISH) {
            return nextEnglish();
        } else if(locale == Locale.KOREAN) {
            return nextKorean();
        }
        return null;
    }

    public <T> T getNextIdx(T size) {
        return randomIndexProvider.getNextIdx(size);
    }

    private Character nextKorean() {
        return nextCharRange((char) 0xAC00, (char) 0xD7A3);
    }

    private Character nextEnglish() {
        return ThreadLocalRandom.current().nextBoolean() ? nextCharRange('A', 'Z') : nextCharRange('a', 'z');
    }

    private Character nextCharRange(char start, char end) {
        return (char) (start + ThreadLocalRandom.current().nextInt(end - start + 1));
    }

    public Character nextAlphabet() {
        return NUMERIC[randomIndexProvider.getNextIdx(NUMERIC.length)];
    }

    public Character nextAlphanum() {
        return ALPHANUM[randomIndexProvider.getNextIdx(NUMERIC.length)];
    }
}
