package mocka.odm.generator.annotation.mocka;


import mocka.core.GenerateType;
import mocka.core.annotation.Mocka;
import mocka.odm.generator.ODMCreator;
import mocka.odm.generator.ODMType;
import mocka.odm.generator.mongodb.MongodbCreator;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

public class MockaOdmExtension implements BeforeEachCallback {

    // 해당 Extension을 사용하는 클래스에서, @Mocka필드를 가진 클래스가 Odm으로 스캔된 클래스라면,
    // 해당 클래스(도큐먼트)의 인스턴스를 생성하여 반환하는 로직
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getRequiredTestInstance();
        ApplicationContext ctx = SpringExtension.getApplicationContext(context);

        ODMType odmType = resolveMockaConfig(testInstance, ctx);

        Class<?> clazz = testInstance.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Mocka.class)) continue;

            Mocka mockaAnnotation = field.getAnnotation(Mocka.class);
            GenerateType generateType = mockaAnnotation.value();

            field.setAccessible(true);

            Class<?> targetClass = field.getType();

            if(odmType == null) odmType = detectODM(targetClass);

            if(odmType == null) continue;

            ODMCreator odmCreator = switch (odmType) {
                case MONGODB -> ctx.getBean(MongodbCreator.class);
            };

            Object document;
            if(generateType.equals(GenerateType.ALL)) {
                document = odmCreator.create(targetClass, new HashMap<>(), new HashSet<>());
            } else {
                document = odmCreator.create(targetClass, generateType);
            }
            field.set(testInstance, document);
            odmType = null;
        }
    }

    private ODMType resolveMockaConfig(Object testClass, ApplicationContext ctx) {
        MockaOdmConfig mockaOdmConfig = testClass.getClass().getAnnotation(MockaOdmConfig.class);
        if(mockaOdmConfig == null) return null;

        ODMType odmType = mockaOdmConfig.odmType();
        int size = mockaOdmConfig.size();

        switch (odmType) {
            case MONGODB -> {
                MongodbCreator creator = ctx.getBean(MongodbCreator.class);
                creator.setAssociationSize(size);
                return ODMType.MONGODB;
            }
            /*
            case ->
             */
        }

        return null;
    }

    private <T> ODMType detectODM(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Document.class)) {
            return ODMType.MONGODB;
        }
        return null;
    }
}
