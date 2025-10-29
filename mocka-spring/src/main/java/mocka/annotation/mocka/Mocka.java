package mocka.annotation.mocka;

import mocka.generator.GenerateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mocka {
    GenerateType value() default GenerateType.SELF;
}
