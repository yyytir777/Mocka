package jodag.random.index;


public interface RandomIndexProvider {

    <T> T getNextIdx(T size);

    boolean getBoolean();

    int getInt(int size);

    int getInt(int min, int max);

    long getLong(long size);

    long getLong(long min, long max);

    float getFloat(float size);

    float getFloat(float min, float max);

    double getDouble(double size);

    double getDouble(double min, double max);

    double getGaussian(double mean, double stdev);
}
