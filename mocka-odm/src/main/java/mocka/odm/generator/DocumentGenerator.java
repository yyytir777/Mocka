package mocka.odm.generator;

import mocka.core.generator.AbstractGenerator;
import mocka.odm.generator.odm.ODMType;
import mocka.core.GenerateType;

import java.util.HashMap;
import java.util.HashSet;

public class DocumentGenerator<T> extends AbstractGenerator<T> {

    private final ODMSelector odmSelector;
    private GenerateType generateType = GenerateType.SELF;
    private ODMType odmType = ODMType.MONGDODB;

    public DocumentGenerator(Class<T> type, ODMSelector odmSelector) {
        super(type.getName(), type);
        this.odmSelector = odmSelector;
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
            return odmSelector.create(odmType, type, new HashMap<>(), new HashSet<>());
        }
        return odmSelector.create(odmType, type, generateType);
    }
}
