package jodag;

import jodag.entity.Member;
import jodag.generator.Generator;
import jodag.registry.SpringDataRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Jodag-Spring 테스트 코드")
public class JodagCoreTest {

    private final SpringDataRegistry springDataRegistry = new SpringDataRegistry();

    @Test
    void test() {
        Generator<Member> generator = springDataRegistry.getGenerator(Member.class);
        Member member = generator.get();
    }
}
