package mocka.annotation.mocka;

import jakarta.persistence.Entity;
import mocka.generator.GenerateType;
import mocka.generator.orm.ORMResolver;
import mocka.generator.orm.ORMType;
import mocka.generator.orm.hibernate.HibernateCreator;
import mocka.generator.orm.mybatis.MyBatisCreator;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This extension integrates the functionality of JUnit and the Mocka library.
 * By applying @ExtendWith(MockaExtension.class) to a test class,
 * you can enable and use the test annotations provided by the Mocka library.
 */
public class MockaExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getRequiredTestInstance();
        ApplicationContext ctx = SpringExtension.getApplicationContext(context);

        Class<?> clazz = testInstance.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Mocka.class)) continue;

            Mocka mockaAnnotation = field.getAnnotation(Mocka.class);
            GenerateType generateType = mockaAnnotation.value();

            field.setAccessible(true);

            Class<?> targetClass = field.getType();

            ORMType ormType = detectORM(targetClass);

            ORMResolver ormResolver = switch (ormType) {
                case MYBATIS -> ctx.getBean(MyBatisCreator.class);
                case HIBERNATE -> ctx.getBean(HibernateCreator.class);
            };

            Object entity;
            if(generateType.equals(GenerateType.ALL)) {
                entity = ormResolver.create(targetClass, new HashMap<>(), new HashSet<>());
            } else {
                entity = ormResolver.create(targetClass, generateType);

            }
            field.set(testInstance, entity);
        }
    }

    private <T> ORMType detectORM(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            return ORMType.HIBERNATE;
        }
        return ORMType.MYBATIS;
    }
}
