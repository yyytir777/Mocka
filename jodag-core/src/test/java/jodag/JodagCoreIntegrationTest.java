package jodag;

import jodag.generator.GeneratorFactory;
import jodag.generator.StringGenerator;
import jodag.generator.common.EmailGenerator;
import jodag.generator.common.LoremIpsumGenerator;
import jodag.generator.common.NameGenerator;
import jodag.generator.primitive.PrimitiveGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("jodag-core 통합 테스트")
public class JodagCoreIntegrationTest {


    @Test
    @DisplayName("GeneratorFactory 테스트 시작 - GeneratorFactory 생성 불필요 (GeneratorFactory는 인스턴스화 되지 않음)")
    void start_integration_test() {
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("GeneratorFactory의 각 generator에 접근 시 동일한 인스턴스를 반환한다.(singleton)")
    void all_generator_in_GeneratorFactory_is_singleton_pattern() {
        assertAll(
                () -> assertThat(EmailGenerator.getInstance()).isEqualTo(GeneratorFactory.email()),
                () -> assertThat(NameGenerator.getInstance()).isEqualTo(GeneratorFactory.name()),
                () -> assertThat(StringGenerator.getInstance()).isEqualTo(GeneratorFactory.string()),
                () -> assertThat(LoremIpsumGenerator.getInstance()).isEqualTo(GeneratorFactory.loremIpsum()),
                () -> assertThat(PrimitiveGenerator.getInstance()).isEqualTo(GeneratorFactory.primitive())
        );
    }

    @Test
    @DisplayName("GeneratorFactory의 EmailGenerator는 random Email을 반환합니다.")
    void get_email_generator_returns_email() {
        EmailGenerator emailGenerator = GeneratorFactory.email();
        for(int i = 0; i < 1000; i++) {
            String email = emailGenerator.get();
            Pattern pattern = Pattern.compile("\\w+@\\w+\\.\\w+");
            Matcher matcher = pattern.matcher(email);
            assertThat(matcher.matches()).isTrue();
        }
    }

    @Test
    @DisplayName("GeneratorFactory의 NameGenerator는 Name을 반환합니다.")
    void get_name_generator_returns_name() {
        NameGenerator nameGenerator = GeneratorFactory.name();
        for(int i = 0; i < 1000; i++) {
            String name = nameGenerator.get();
            String firstName = nameGenerator.firstName();
            String lastName = nameGenerator.lastName();

            assertAll(
                    () -> assertThat(name).isNotBlank(),
                    () -> assertThat(firstName).isNotBlank(),
                    () -> assertThat(lastName).isNotBlank()
            );
        }
    }

    @Test
    @DisplayName("GeneratorFactory의 StringGenerator는 String을 반환합니다.")
    void get_string_generator_returns_string() {
        StringGenerator stringGenerator = GeneratorFactory.string();
        for(int i = 0; i < 1000; i++) {
            String string = stringGenerator.get();
            System.out.println("string = " + string);
            assertThat(string.length()).isGreaterThanOrEqualTo(0);

            String string1 = stringGenerator.get(10);
            System.out.println("string1 = " + string1);
            assertThat(string1.length()).isEqualTo(10);
        }
    }

    @Test
    @DisplayName("GeneratorFactory의 LoremIpsumGenerator는 loremIpsum을 반환합니다.")
    void get_loremIpsum_generator_returns_loremIpsum() {
        LoremIpsumGenerator loremIpsumGenerator = GeneratorFactory.loremIpsum();

        String loremIpsum = loremIpsumGenerator.get();
        assertThat(loremIpsum).isNotNull();

        String loremIpsum1 = loremIpsumGenerator.get(2000);
        assertThat(loremIpsum1).isNotNull();
        assertThat(loremIpsum1.length()).isEqualTo(2000);
    }

    @Test
    @DisplayName("GeneratorFactory의 PrimitiveGenerator는 각 primitive값을 반환합니다.")
    void get_primitive_generator_returns_primitive() {
        PrimitiveGenerator primitiveGenerator = GeneratorFactory.primitive();

        // Boolean
        Boolean b = primitiveGenerator.getBoolean();
        System.out.println("b = " + b);
        assertThat(b).isIn(true, false);

        // Byte
        Byte b1 = primitiveGenerator.getByte();
        System.out.println("b1 = " + b1);
        assertThat(b1).isBetween(Byte.MIN_VALUE, Byte.MAX_VALUE);

        // Short
        Short s = primitiveGenerator.getShort();
        System.out.println("s = " + s);
        assertThat(s).isBetween(Short.MIN_VALUE, Short.MAX_VALUE);

        // Integer
        Integer i = primitiveGenerator.getInteger();
        System.out.println("i = " + i);
        assertThat(i).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);

        // Long
        Long l = primitiveGenerator.getLong();
        System.out.println("l = " + l);
        assertThat(l).isBetween(Long.MIN_VALUE, Long.MAX_VALUE);

        // Float
        Float f = primitiveGenerator.getFloat();
        System.out.println("f = " + f);
        assertThat(f).isBetween(Float.MIN_VALUE, Float.MAX_VALUE);

        // Double
        Double d = primitiveGenerator.getDouble();
        System.out.println("d = " + d);
        assertThat(d).isBetween(Double.MIN_VALUE, Double.MAX_VALUE);

        // Character
        Character c = primitiveGenerator.getCharacter();
        System.out.println("c = " + c);
        assertThat(c).isBetween(Character.MIN_VALUE, Character.MAX_VALUE);
    }
}
