package jodag.generator.common;

import jodag.generator.Generator;
import jodag.random.RandomProvider;

import java.util.Random;


public abstract class DefaultAbstractGenerator<T> implements Generator<T> {

    protected final RandomProvider randomProvider;
    protected final String name;
    protected final Class<T> type;

    public DefaultAbstractGenerator(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.randomProvider = new RandomProvider();
    }

    public DefaultAbstractGenerator(String name, Class<T> type, Random random) {
        this.name = name;
        this.type = type;
        this.randomProvider = new RandomProvider(random);
    }

    public DefaultAbstractGenerator(String name, Class<T> type, long seed) {
        this.name = name;
        this.type = type;
        this.randomProvider = new RandomProvider(new Random(seed));
    }

    public String getName() {
        return this.name;
    }

    public Class<T> getType() {
        return this.type;
    }
}
