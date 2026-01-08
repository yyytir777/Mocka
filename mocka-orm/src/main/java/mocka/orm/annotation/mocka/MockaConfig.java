package mocka.orm.annotation.mocka;


import mocka.orm.generator.ORMType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code MockaConfig} defines class-level configuration for Mocka-based tests.
 *
 * <p>
 * When applied to a test class, this annotation configures how entities
 * annotated with {@link Mocka} are generated during test execution.
 * </p>
 *
 * <p>
 * The configuration applies to all {@link Mocka}-annotated fields within
 * the test class.
 * </p>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockaConfig {

    /**
     * Specifies the ORM type to use when generating entities.
     *
     * <p>
     * If not explicitly set, Hibernate is used by default.
     * </p>
     *
     * @return the ORM type
     */
    ORMType ormType() default ORMType.HIBERNATE;

    /**
     * Specifies the number of associated entities to generate
     * when relationship-based generation is performed.
     *
     * @return the association size limit
     */
    int size() default 5;
}
