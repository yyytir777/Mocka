package entityinstantiator.annotation;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ComponentScan(basePackages = "entityinstantiator")
public @interface EnableEntityInstantiator {
}
