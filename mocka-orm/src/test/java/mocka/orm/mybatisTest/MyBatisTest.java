package mocka.orm.mybatisTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.entity.orm.Member;
import mocka.orm.generator.EntityGenerator;
import mocka.orm.generator.EntityGeneratorFactory;
import mocka.orm.generator.ORMType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DisplayName("MyBatis Supports Field Type Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
public class MyBatisTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("MyBatis supports the fields of Member.class")
    void mybatis_supports_the_fields_of_Member() {
        EntityGenerator<Member> generator = generatorFactory.getGenerator(Member.class);
        Member member = generator.ormType(ORMType.MYBATIS).get();
        System.out.println("member = " + member);

        assertThat(member).isNotNull().isInstanceOf(Member.class);
    }
}
