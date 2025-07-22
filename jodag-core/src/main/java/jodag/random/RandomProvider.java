package jodag.random;

import jodag.random.index.RandomIndexProvider;
import jodag.random.index.ThreadLocalRandomIndexProvider;


public class RandomProvider {

    private static final RandomProvider INSTANCE = new RandomProvider();

    private final RandomIndexProvider randomIndexProvider = ThreadLocalRandomIndexProvider.getInstance();

    public static RandomProvider getInstance() {
        return INSTANCE;
    }

    public <T> T getNextIdx(T size) {
        return randomIndexProvider.getNextIdx(size);
    }
}
