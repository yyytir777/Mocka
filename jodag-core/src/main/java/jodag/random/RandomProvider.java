package jodag.random;

import jodag.random.index.RandomIndexProvider;
import jodag.random.index.ThreadLocalRandomIndexProvider;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Thread-safe singleton provider for generating random values.
 *
 * <p>
 * This class serves as a facade for random number generation, delegating to
 * {@link ThreadLocalRandomIndexProvider} for thread-safe random value generation.
 * It provides convenient methods for generating various primitive types with
 * different range constraints.
 */
public class RandomProvider {

    private static final RandomProvider INSTANCE = new RandomProvider();

    private final RandomIndexProvider randomIndexProvider = ThreadLocalRandomIndexProvider.getInstance();

    private RandomProvider() {}

    public static RandomProvider getInstance() {
        return INSTANCE;
    }

    public Random getRandom() {
        return ThreadLocalRandom.current();
    }

    /**
     * Generates a random index value within the bounds of the given size.
     * @param size the upper bound (exclusive) for the random index
     * @param <T>  the numeric type (Integer, Long, etc.)
     * @return a random value between 0 (inclusive) and size (exclusive)
     */

    public <T> T getNextIdx(T size) {
        return randomIndexProvider.getNextIdx(size);
    }

    /**
     * Generates a random boolean value.
     * @return a random boolean (true or false with equal probability)
     */
    public boolean getBoolean() {
        return randomIndexProvider.getBoolean();
    }

    /**
     * Generates a random integer between 0 (inclusive) and size (exclusive).
     * @param size the upper bound (exclusive)
     * @return a random integer in the range [0, size)
     */
    public int getInt(int size) {
        return randomIndexProvider.getInt(size);
    }

    /**
     * Generates a random integer within the specified range.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random integer in the range [min, max)
     */
    public int getInt(int min, int max) {
        return randomIndexProvider.getInt(min, max);
    }

    /**
     * Generates a random long between 0 (inclusive) and size (exclusive).
     *
     * @param size the upper bound (exclusive)
     * @return a random long in the range [0, size)
     */
    public long getLong(long size) {
        return randomIndexProvider.getLong(size);
    }

    /**
     * Generates a random long within the specified range.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random long in the range [min, max)
     */
    public long getLong(long min, long max) {
        return randomIndexProvider.getLong(min, max);
    }

    /**
     * Generates a random float between 0 (inclusive) and size (exclusive).
     *
     * @param size the upper bound (exclusive)
     * @return a random float in the range [0, size)
     */
    public float getFloat(float size) {
        return randomIndexProvider.getFloat(size);
    }

    /**
     * Generates a random float within the specified range.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random float in the range [min, max)
     */
    public float getFloat(float min, float max) {
        return randomIndexProvider.getFloat(min, max);
    }

    /**
     * Generates a random double between 0 (inclusive) and size (exclusive).
     *
     * @param size the upper bound (exclusive)
     * @return a random double in the range [0, size)
     */
    public double getDouble(double size) {
        return randomIndexProvider.getDouble(size);
    }

    /**
     * Generates a random double within the specified range.
     *
     * @param min the lower bound (inclusive)
     * @param max the upper bound (exclusive)
     * @return a random double in the range [min, max)
     */
    public double getDouble(double min, double max) {
        return randomIndexProvider.getDouble(min, max);
    }

    /**
     * Generates a random double from a Gaussian (normal) distribution.
     *
     * <p>
     * The generated values follow a bell curve centered around the mean,
     * with spread determined by the standard deviation.
     * </p>
     *
     * @param mean  the mean (center) of the Gaussian distribution
     * @param stdev the standard deviation (spread) of the distribution
     * @return a random double following the specified Gaussian distribution
     */
    public double getGaussian(double mean, double stdev) {
        return randomIndexProvider.getGaussian(mean, stdev);
    }
}
