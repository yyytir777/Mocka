package mocka.odm.generator;

import mocka.core.generator.AbstractGenerator;
import mocka.core.GenerateType;

import java.util.HashMap;
import java.util.HashSet;

public class DocumentGenerator<T> extends AbstractGenerator<T> {

    private final ODMResolver odmResolver;
    private GenerateType generateType = GenerateType.SELF;
    private ODMType odmType = ODMType.MONGDODB;

    public DocumentGenerator(Class<T> type, ODMResolver odmResolver) {
        super(type.getName(), type);
        this.odmResolver = odmResolver;
    }

    public DocumentGenerator<T> generateType(GenerateType generateType) {
        this.generateType = generateType;
        return this;
    }

    public DocumentGenerator<T> odmType(ODMType odmType) {
        this.odmType = odmType;
        return this;
    }

    @Override
    public T get() {
        if (generateType.equals(GenerateType.ALL)) {
            return odmResolver.create(odmType, type, new HashMap<>(), new HashSet<>());
        }
        return odmResolver.create(odmType, type, generateType);
    }
}
