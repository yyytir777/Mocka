package mocka.core.generator.primitive;

import mocka.core.generator.AbstractGenerator;

import java.util.List;


public class DoubleGenerator extends AbstractGenerator<Double> {

    private static final DoubleGenerator INSTANCE = new DoubleGenerator();

    private DoubleGenerator() {
        super("double", Double.class);
    }

    public static DoubleGenerator getInstance() {
        return INSTANCE;
    }

    /** returns a random double value */
    @Override
    public Double get() {
        return getDouble();
    }

    /** returns a random double within the full double range [-Double.MAX_VALUE, Double.MAX_VALUE]. */
    public Double getDouble() {
        return getDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
    }

    /** returns a random double between the given min and max values [min, max]. */
    public Double getDouble(Double min, Double max) {
        if (Double.isInfinite(max - min)) {
            return randomProvider.getBoolean()
                    ? randomProvider.getDouble(Double.MIN_VALUE, max)
                    : randomProvider.getDouble(min,-Double.MIN_VALUE);
        }
        return randomProvider.getDouble(min, max);
    }

    /** returns a random positive double (Double.MIN_VALUE, Double.MAX_VALUE]. */
    public Double getPositiveDouble() {
        return getDouble(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    /** returns a random negative double [-Double.MAX_VALUE, -Double.MIN_VALUE). */
    public Double getNegativeDouble() {
        return getDouble(-Double.MAX_VALUE, -Double.MIN_VALUE);
    }

    /** picks a random double from the given list. */
    public Double pickFrom(List<Double> list) {
        return list.get(randomProvider.getNextIdx(list.size()));
    }

    /** picks a random double from the given array. */
    public Double pickFrom(Double[] doubles) {
        return doubles[randomProvider.getNextIdx(doubles.length)];
    }
}
