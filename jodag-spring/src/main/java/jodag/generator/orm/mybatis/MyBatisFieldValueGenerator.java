package jodag.generator.orm.mybatis;

import jodag.generator.orm.FieldValueGenerator;
import jodag.random.RandomProvider;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class MyBatisFieldValueGenerator implements FieldValueGenerator {

    private final RandomProvider randomProvider;

    public MyBatisFieldValueGenerator() {
        this.randomProvider = RandomProvider.getInstance();
    }

    @Override
    public Object get(Field field) {
        return randomProvider.getInt(10);
    }
}
