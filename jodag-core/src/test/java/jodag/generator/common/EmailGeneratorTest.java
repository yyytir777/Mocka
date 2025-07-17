package jodag.generator.common;

import jodag.generator.GenerateType;
import jodag.generator.Generator;
import jodag.registry.DataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

@DisplayName("EmailGenerator 테스트 클래스입니다.")
class EmailGeneratorTest {

    private final DataRegistry dataRegistry = DataRegistry.getInstance();

    @Test
    @DisplayName("EmailGenerator 인스턴스를 반환한다")
    void return_email_generator_instance() {
        // given & when
        Generator<String> emailGenerator = dataRegistry.getGenerator(GenerateType.EMAIL);

        // then
        assertThat(emailGenerator).isInstanceOf(EmailGenerator.class);
    }

    @Test
    @DisplayName("EmailGenerator에서 get()을 호출하여 랜덤한 값을 반환합니다.")
    void generate_different_values_on_each_get_call() {
        // given
        Generator<String> emailGenerator = dataRegistry.getGenerator(GenerateType.EMAIL);
        Pattern emailPattern = Pattern.compile("\\w+@\\w+\\.\\w+");

        // when
        String name1 = emailGenerator.get();
        String name2 = emailGenerator.get();

        System.out.println("email1 = " + name1);
        System.out.println("email2 = " + name2);
        // then
        assertThat(name1).matches(emailPattern);
        assertThat(name2).matches(emailPattern);
        assertThat(name1).isNotEqualTo(name2);
    }

    @Test
    @DisplayName("EmailGenerator에서 1000개의 랜덤 이름을 반환합니다.")
    void return_1000_names() {
        Generator<String> generator = dataRegistry.getGenerator(GenerateType.EMAIL);
        for(int i = 0; i < 1000; i++) {
            String email = generator.get();
            System.out.println("email = " + email);
        }

        assertThat(true).isTrue();
    }
}