package jodag.random.index;


import java.util.concurrent.ThreadLocalRandom;

public class ThreadLocalRandomIndexProvider implements RandomIndexProvider {

    private static final ThreadLocalRandomIndexProvider INSTANCE = new ThreadLocalRandomIndexProvider();

    private ThreadLocalRandomIndexProvider() {}

    public static ThreadLocalRandomIndexProvider getInstance() {
        return INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getNextIdx(T size) {
        if(Long.class.equals(size.getClass())) {
            return (T) (Long) ThreadLocalRandom.current().nextLong((Long) size);
        } else {
            return (T) (Integer) ThreadLocalRandom.current().nextInt((Integer) size);
        }
    }
}
