package mocka.orm.hibernateTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.entity.orm.Member;
import mocka.orm.generator.EntityGenerator;
import mocka.orm.generator.EntityGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("Hibernate Supports Field Type Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class HibernateTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("Hibernate supports the fields of Member.class")
    void hibernate_supports_the_fields_of_Member() {
        EntityGenerator<Member> generator = generatorFactory.getGenerator(Member.class);
        Member member = generator.get();
        System.out.println("member = " + member);

        assertThat(member).isNotNull().isInstanceOf(Member.class);
    }
}
