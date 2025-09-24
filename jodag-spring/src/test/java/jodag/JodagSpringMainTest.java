package jodag;

import jodag.entity.Member;
import jodag.generator.Generator;
import jodag.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
@DisplayName("Jodag-Spring 메인 기능 테스트 코드")
public class JodagSpringMainTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("엔티티 클래스는 자동으로 SpringGeneratorFactory에 저장됩니다.")
    void auto_register_generate_class_in_sprigGeneratorFactory() {
        List<String> generatorNames = springGeneratorFactory.getGeneratorNames();
        assertThat(Member.class.getSimpleName()).isIn(generatorNames);
    }

    @Test
    @DisplayName("Member 엔티티 및 인스턴스 생성")
    void test() {
        Generator<Member> generator = springGeneratorFactory.getGenerator(Member.class);

        Member member = generator.get();
        System.out.println("member = " + member.toString());
        assertThat(member.getId()).isNull(); // id는 JPA 생성전략에 따라 null로 설정 (@GeneratedValue(strategy = GenerationType.IDENTITY))
        assertThat(member.getEmail()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<String> generatorNames = springGeneratorFactory.getGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat("Member").isIn(generatorNames);
    }

    @Test
    @DisplayName("Member 엔티티 인스턴스 10개 생성")
    void create_10_instances() {
        Generator<Member> generator = springGeneratorFactory.getGenerator(Member.class);

        for (int i = 0; i < 10; i++) {
            Member member = generator.get();
            System.out.println("member : " + member.toString());
            assertThat(member.getName()).isNotNull();
        }
    }
}
