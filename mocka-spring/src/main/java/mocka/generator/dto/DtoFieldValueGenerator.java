package mocka.generator.dto;

import mocka.generator.orm.FieldValueGenerator;
import mocka.random.RandomProvider;

import java.lang.reflect.Field;

public class DtoFieldValueGenerator implements FieldValueGenerator {

    private static final DtoFieldValueGenerator INSTANCE = new DtoFieldValueGenerator();
    private final RandomProvider randomProvider;

    private DtoFieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();;
    }

    public static DtoFieldValueGenerator getInstance() {
        return INSTANCE;
    }

    @Override
    public Object get(Field field) {
        return randomProvider.getInt(10);
    }
}
