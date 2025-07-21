package jodag;

import jodag.entity.Member;
import jodag.generator.Generator;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(classes = TestConfig.class)
@DisplayName("Jodag-Spring 메인 기능 테스트 코드")
public class JodagSpringMainTest {

    @Test
    @DisplayName("@Generate가 붙은 엔티티 클래스는 SpringGeneratorFactory에 저장됩니다.")
    void auto_register_generate_class_in_sprigGeneratorFactory() {
        List<String> generatorNames = SpringGeneratorFactory.getGeneratorNames();
        assertThat(Member.class.getSimpleName()).isIn(generatorNames);
    }

    @Test
    @DisplayName("Member.class를 자동으로 생성합니다.")
    void test() {
        Generator<Member> generator = SpringGeneratorFactory.getGenerator(Member.class);

        Member member = generator.get();
        System.out.println("member : " + member.toString());
        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<String> generatorNames = SpringGeneratorFactory.getGeneratorNames();
        System.out.println("generatorNames : " + generatorNames);
        assertThat("Member").isIn(generatorNames);
    }

    @Test
    @DisplayName("Member.class를 10개 생성합니다.")
    void create_10_instances() {
        Generator<Member> generator = SpringGeneratorFactory.getGenerator(Member.class);

        for (int i = 0; i < 10; i++) {
            Member member = generator.get();
            System.out.println("member : " + member.toString());
            assertThat(member.getId()).isNotNull();
            assertThat(member.getName()).isNotNull();
        }
    }
}
