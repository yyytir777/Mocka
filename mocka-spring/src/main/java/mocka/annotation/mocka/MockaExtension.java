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
    public void beforeEach(ExtensionContext context) throws IllegalAccessException {
        Object testInstance = context.getRequiredTestInstance();
        ApplicationContext ctx = SpringExtension.getApplicationContext(context);

        ORMType ormType = resolveMockaConfig(testInstance, ctx);

        Class<?> clazz = testInstance.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Mocka.class)) continue;

            Mocka mockaAnnotation = field.getAnnotation(Mocka.class);
            GenerateType generateType = mockaAnnotation.value();

            field.setAccessible(true);

            Class<?> targetClass = field.getType();

            if(ormType == null) ormType = detectORM(targetClass);

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
            ormType = null;
        }
    }

    private ORMType resolveMockaConfig(Object testClass, ApplicationContext ctx) {
        MockaConfig mockaConfig = testClass.getClass().getAnnotation(MockaConfig.class);
        if(mockaConfig == null) return null;

        ORMType ormType = mockaConfig.ormType();
        int size = mockaConfig.size();

        switch (ormType) {
            case HIBERNATE -> {
                HibernateCreator creator = ctx.getBean(HibernateCreator.class);
                creator.setAssociationSize(size);
                return ORMType.HIBERNATE;
            }
            case MYBATIS -> {
                MyBatisCreator creator = ctx.getBean(MyBatisCreator.class);
                creator.setAssociationSize(size);
                return ORMType.MYBATIS;
            }
        }
        return null;
    }

    private <T> ORMType detectORM(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Entity.class)) {
            return ORMType.HIBERNATE;
        }
        return ORMType.MYBATIS;
    }
}
