package jodag;

import jodag.entity.Member;
import jodag.entity.NonGenerateEntity;
import jodag.entity.TestEntity;
import jodag.exception.MissingRequiredAnnotationException;
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
    @DisplayName("Member 엔티티 @Generate 등록 및 인스턴스 생성")
    void test() {
        Generator<Member> generator = SpringGeneratorFactory.getGenerator(Member.class);

        Member member = generator.get();
        System.out.println("member = " + member.toString());
        assertThat(member.getId()).isNull(); // id는 JPA 생성전략에 따라 null로 설정 (@GeneratedValue(strategy = GenerationType.IDENTITY))
        assertThat(member.getEmail()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<String> generatorNames = SpringGeneratorFactory.getGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat("Member").isIn(generatorNames);
    }

    @Test
    @DisplayName("Member 엔티티 @Generate 등록 및 인스턴스 10개 생성")
    void create_10_instances() {
        Generator<Member> generator = SpringGeneratorFactory.getGenerator(Member.class);

        for (int i = 0; i < 10; i++) {
            Member member = generator.get();
            System.out.println("member : " + member.toString());
            assertThat(member.getName()).isNotNull();
        }
    }

    @Test
    @DisplayName("test 엔티티 @Generate 등록 및 인스턴스 생성")
    void test_entity_class() {
        Generator<TestEntity> generator = SpringGeneratorFactory.getGenerator(TestEntity.class);
        TestEntity testEntity = generator.get();
        System.out.println("testEntity = " + testEntity.toString());
    }
}
