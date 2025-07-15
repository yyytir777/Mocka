package jodag.random;

import java.util.Objects;
import java.util.Random;

public class RandomProvider {

    private static final Random DEFAULT_RANDOM = new Random();
    private final Random random;

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

}
