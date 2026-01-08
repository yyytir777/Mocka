package mocka.orm.annotation;



import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ValueSource {

    String path() default "";

    Class<?> type() default Object.class;

    String generatorKey() default "";
}
