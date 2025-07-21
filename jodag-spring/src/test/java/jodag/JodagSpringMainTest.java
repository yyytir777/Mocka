package jodag;

import jodag.entity.Member;
import jodag.generator.Generator;
import jodag.registry.SpringDataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
@DisplayName("Jodag-Spring 메인 기능 테스트 코드")
public class JodagSpringMainTest {

    @Autowired
    SpringDataRegistry dataRegistry;

    @Test
    void test() {
        Generator<Member> generator = dataRegistry.getGenerator(Member.class);
        Member member = generator.get();
        System.out.println("member = " + member.toString());
    }
}
