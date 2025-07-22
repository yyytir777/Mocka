package jodag.random;

import jodag.random.index.RandomIndexProvider;
import jodag.random.index.ThreadLocalRandomIndexProvider;


public class RandomProvider {

    private static final RandomProvider INSTANCE = new RandomProvider();

    private final RandomIndexProvider randomIndexProvider = ThreadLocalRandomIndexProvider.getInstance();

    private RandomProvider() {}

    public static RandomProvider getInstance() {
        return INSTANCE;
    }

    public <T> T getNextIdx(T size) {
        return randomIndexProvider.getNextIdx(size);
    }

    public boolean getBoolean() {
        return randomIndexProvider.getBoolean();
    }

    public int getInt(int size) {
        return randomIndexProvider.getInt(size);
    }

    public int getInt(int min, int max) {
        return randomIndexProvider.getInt(min, max);
    }

    public long getLong(long size) {
        return randomIndexProvider.getLong(size);
    }

    public long getLong(long min, long max) {
        return randomIndexProvider.getLong(min, max);
    }

    public float getFloat(float size) {
        return randomIndexProvider.getFloat(size);
    }

    public float getFloat(float min, float max) {
        return randomIndexProvider.getFloat(min, max);
    }

    public double getDouble(double size) {
        return randomIndexProvider.getDouble(size);
    }

    public double getDouble(double min, double max) {
        return randomIndexProvider.getDouble(min, max);
    }

    public double getGaussian(double mean, double stdev) {
        return randomIndexProvider.getGaussian(mean, stdev);
    }
}
