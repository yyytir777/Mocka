package mocka.annotation.mocka;

import mocka.generator.GenerateType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to automatically generate mock entity instances
 * for testing within the Mocka framework.
 * <p>
 * When applied to a field, this annotation triggers entity creation
 * using the specified {@link GenerateType}. By default, it uses
 * {@code GenerateType.SELF}, which generates only the annotated entity itself
 * without recursively generating related entities.
 * </p>
 *
 * <h3>Usage Example:</h3>
 * <pre>{@code
 * @ExtendsWith(MockaExtension.class)
 * public class MemberTest {
 *
 *     @Mocka
 *     private Member member; // generates a single Member entity
 *
 *     @Mocka(GenerateType.CHILDREN)
 *     private Recruit recruit; // generates Recruit and its child entities
 * }
 * }</pre>
 *
 * @see GenerateType for available generation modes (SELF, CHILD, CHILDREN, PARENT, etc.)
 *
 * @author Lim Wonjae
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Mocka {
    GenerateType value() default GenerateType.SELF;
}
