package entityinstantiator.mybatisTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.entity.mybatis.Member;
import entityinstantiator.generator.EntityGenerator;
import entityinstantiator.generator.SpringGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis Supports Field Type Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class MyBatisTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("MyBatis supports the fields of Member.class")
    void mybatis_supports_the_fields_of_Member() {
        EntityGenerator<Member> generator = springGeneratorFactory.getGenerator(Member.class);
        Member member = generator.get();
        System.out.println("member = " + member);

        assertThat(member).isNotNull().isInstanceOf(Member.class);
    }
}
