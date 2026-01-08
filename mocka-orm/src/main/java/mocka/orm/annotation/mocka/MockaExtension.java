package mocka.orm.annotation.mocka;

import jakarta.persistence.Entity;
import mocka.core.GenerateType;
import mocka.orm.generator.ORMCreator;
import mocka.orm.generator.ORMType;
import mocka.orm.generator.hibernate.HibernateCreator;
import mocka.orm.generator.mybatis.MyBatisCreator;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

/**
 * {@code MockaExtension} integrates JUnit 5 with the Mocka ORM library.
 *
 * <p>
 * When applied via {@code @ExtendWith(MockaExtension.class)}, this extension
 * automatically initializes fields annotated with {@link Mocka} before each test.
 * </p>
 *
 * <p>
 * For each {@code @Mocka}-annotated field:
 * </p>
 * <ul>
 *   <li>The target ORM type is resolved (explicitly via {@link MockaConfig} or implicitly)</li>
 *   <li>The appropriate {@link ORMCreator} is selected from the Spring context</li>
 *   <li>An entity instance is generated according to the specified {@link GenerateType}</li>
 *   <li>The generated entity is injected into the test instance field</li>
 * </ul>
 *
 * <p>
 * This extension performs no caching and runs before every test method execution.
 * </p>
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

            ORMCreator ormCreator = switch (ormType) {
                case MYBATIS -> ctx.getBean(MyBatisCreator.class);
                case HIBERNATE -> ctx.getBean(HibernateCreator.class);
            };

            Object entity;
            if(generateType.equals(GenerateType.ALL)) {
                entity = ormCreator.create(targetClass, new HashMap<>(), new HashSet<>());
            } else {
                entity = ormCreator.create(targetClass, generateType);

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
