package entityinstantiator.generator.primitive;

import entityinstantiator.generator.AbstractGenerator;

import java.util.List;


public class FloatGenerator extends AbstractGenerator<Float> {

    private static final FloatGenerator INSTANCE = new FloatGenerator();

    private FloatGenerator() {
        super("float", Float.class);
    }

    public static FloatGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random float value */
    @Override
    public Float get() {
        return getFloat();
    }

    /** returns a random float within the full float range [-Float.MAX_VALUE, Float.MAX_VALUE]. */
    public Float getFloat() {
        return getFloat(-Float.MAX_VALUE, Float.MAX_VALUE);
    }

    /** returns a random float between the given min and max values [min, max]. */
    public Float getFloat(Float min, Float max) {
        if (Float.isInfinite(max - min)) {
            return randomProvider.getBoolean()
                    ? randomProvider.getFloat(Float.MIN_VALUE, max)
                    : randomProvider.getFloat(min, -Float.MIN_VALUE);
        }
        return randomProvider.getFloat(min, max);
    }

    /** returns a random positive float (Float.MIN_VALUE, Float.MAX_VALUE]. */
    public Float getPositiveFloat() {
        return getFloat(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    /** returns a random negative float [-Float.MAX_VALUE, -Float.MIN_VALUE). */
    public Float getNegativeFloat() {
        return getFloat(-Float.MAX_VALUE, -Float.MIN_VALUE);
    }

    /** picks a random float from the given list. */
    public Float pickFrom(List<Float> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    /** picks a random float from the given array. */
    public Float pickFrom(Float[] floats) {
        return floats[randomProvider.getNextIdx(floats.length)];
    }
}
