package mocka.odm.generator.factory;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.factory.GeneratorFactory;
import org.bson.types.*;

public class BsonGenerator extends AbstractGenerator<String> {

    private static final BsonGenerator INSTANCE = new BsonGenerator();
    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    private BsonGenerator() {
        super("odm", String.class);
    }

    public static BsonGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException();
    }

    public ObjectId getObjectId() {
        return ObjectId.get();
    }

    public Binary getBinary() {
        return new Binary(generatorFactory.asByteArray().get());
    }

    public Decimal128 getDecimal128() {
        return new Decimal128(generatorFactory.asLong().get());
    }

    public BSONTimestamp getBsonTimestamp() {
        int time = generatorFactory.asInteger().get();
        int increment = generatorFactory.asInteger().get();
        return new BSONTimestamp(time, increment);
    }
}
