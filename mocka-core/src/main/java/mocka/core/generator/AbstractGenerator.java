package mocka.core.generator;

import mocka.core.random.RandomProvider;



public abstract class AbstractGenerator<T> implements Generator<T> {

    protected final RandomProvider randomProvider;
    protected final String key;
    protected final Class<T> type;

    public AbstractGenerator(String key, Class<T> type) {
        this.key = key;
        this.type = type;
        this.randomProvider = RandomProvider.getInstance();
    }

    public String getKey() {
        return key;
    }

    public Class<T> getType() {
        return this.type;
    }
}
