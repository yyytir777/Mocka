package jodag.random.index;

import java.util.Random;

public class DefaultRandomIndexProvider implements RandomIndexProvider {

    private static final DefaultRandomIndexProvider INSTANCE = new DefaultRandomIndexProvider();
    private static final Random random = new Random();

    private DefaultRandomIndexProvider() {}

    public static DefaultRandomIndexProvider getInstance() {
        return INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getNextIdx(T size) {
        if(Long.class.equals(size.getClass())) {
            return (T) Long.valueOf(random.nextLong((Long) size));
        } else {
            return (T) Integer.valueOf(random.nextInt((Integer) size));
        }
    }
}
