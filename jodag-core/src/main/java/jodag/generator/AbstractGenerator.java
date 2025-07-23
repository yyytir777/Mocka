package jodag.generator;

import jodag.random.RandomProvider;



public abstract class AbstractGenerator<T> implements Generator<T> {

    protected final RandomProvider randomProvider;
    protected final String name;
    protected final Class<T> type;

    public AbstractGenerator(String name, Class<T> type) {
        this.name = name;
        this.type = type;
        this.randomProvider = RandomProvider.getInstance();
    }

    protected String getName() {
        return this.name;
    }

    public Class<T> getType() {
        return this.type;
    }
}
