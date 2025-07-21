package jodag;

import jodag.entity.Member;
import jodag.generator.Generator;
import jodag.generator.SpringGeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest(classes = TestConfig.class)
@DisplayName("Jodag-Spring 메인 기능 테스트 코드")
public class JodagSpringMainTest {

    @Test
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
}
