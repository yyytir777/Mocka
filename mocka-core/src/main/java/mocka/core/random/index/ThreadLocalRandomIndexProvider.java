package mocka.core.random.index;


import java.util.concurrent.ThreadLocalRandom;

/**
 * Thread-safe implementation of {@link RandomIndexProvider} using {@link ThreadLocalRandom}.
 */
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

    @Override
    public boolean getBoolean() {
        return ThreadLocalRandom.current().nextBoolean();
    }

    @Override
    public int getInt(int size) {
        return ThreadLocalRandom.current().nextInt(size);
    }

    @Override
    public int getInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    @Override
    public long getLong(long size) {
        return ThreadLocalRandom.current().nextLong(size);
    }

    @Override
    public long getLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max);
    }

    @Override
    public float getFloat(float size) {
        return ThreadLocalRandom.current().nextFloat(size);
    }

    @Override
    public float getFloat(float min, float max) {
        return ThreadLocalRandom.current().nextFloat(min, max);
    }

    @Override
    public double getDouble(double size) {
        return ThreadLocalRandom.current().nextDouble(size);
    }

    @Override
    public double getDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    @Override
    public double getGaussian(double mean, double stddev) {
        return ThreadLocalRandom.current().nextGaussian(mean, stddev);
    }
}
