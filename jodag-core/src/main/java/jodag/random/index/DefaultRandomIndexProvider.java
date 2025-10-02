package jodag.random.index;

import java.util.Random;

/**
 * Default implementation of {@link RandomIndexProvider} using a single shared {@link Random} instance.
 */
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

    @Override
    public boolean getBoolean() {
        return random.nextBoolean();
    }

    @Override
    public int getInt(int size) {
        return random.nextInt(size);
    }

    @Override
    public int getInt(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

    @Override
    public long getLong(long size) {
        return random.nextLong(size);
    }

    @Override
    public long getLong(long min, long max) {
        return random.nextLong(min, max);
    }

    @Override
    public float getFloat(float size) {
        return random.nextFloat(size);
    }

    @Override
    public float getFloat(float min, float max) {
        return random.nextFloat(min, max);
    }

    @Override
    public double getDouble(double size) {
        return random.nextDouble(size);
    }

    @Override
    public double getDouble(double min, double max) {
        return random.nextDouble(min, max);
    }

    @Override
    public double getGaussian(double mean, double stdev) {
        return random.nextGaussian(mean, stdev);
    }
}
