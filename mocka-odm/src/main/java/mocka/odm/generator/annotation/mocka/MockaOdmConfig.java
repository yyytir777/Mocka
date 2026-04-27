package mocka.odm.generator.annotation.mocka;

import mocka.odm.generator.ODMType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockaOdmConfig {

    ODMType odmType() default ODMType.MONGODB;

    int size() default 5;
}
