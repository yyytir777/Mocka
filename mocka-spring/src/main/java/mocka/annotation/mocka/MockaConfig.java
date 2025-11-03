package mocka.annotation.mocka;


import mocka.generator.orm.ORMType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockaConfig {
    ORMType ormType() default ORMType.HIBERNATE;
    int size() default 5;
}
