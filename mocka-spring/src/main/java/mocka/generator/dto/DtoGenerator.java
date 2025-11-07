package mocka.generator.dto;

import mocka.generator.AbstractGenerator;

public class DtoGenerator<T> extends AbstractGenerator<T> {

    private final DtoInstantiator dtoInstantiator;

    public DtoGenerator(String key, Class<T> type) {
        super(key, type);
        this.dtoInstantiator = new DtoInstantiator();
    }

    @Override
    public T get() {
        return dtoInstantiator.create(type);
    }
}
