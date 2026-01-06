package mocka.orm;

import mocka.orm.generator.EntityGeneratorFactory;
import mocka.orm.generator.EntityGeneratorInitializer;
import mocka.orm.generator.ORMResolver;
import mocka.orm.generator.ORMCreator;
import mocka.orm.entity.orm.Member;
import mocka.core.generator.Generator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(classes = MockaSpringTestApplication.class)
@DisplayName("mocka-spring Main Test Code")
public class MockaSpringMainTest {

    @Autowired
    private EntityGeneratorInitializer initializer;

    @Autowired
    private EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("Set ORM scan range by the value of 'mocka.orm.ormType' in application.yaml. If not specified, use bean definition.")
    void get_ormType() {
        ORMResolver ormResolver = initializer.getOrmSelector();
        List<ORMCreator> creators = ormResolver.getCreators();
        for (ORMCreator ormCreator : creators) {
            System.out.println("ormCreator = " + ormCreator);
            assertThat(ormCreator).isNotNull();
        }
    }

    @Test
    @DisplayName("Verify Member instance generation from Member.class generator")
    void create_member_instance() {
        Generator<Member> generator = generatorFactory.getGenerator(Member.class);

        Member member = generator.get();
        System.out.println("member = " + member.toString());
        assertThat(member.getId()).isNull();
        assertThat(member.getEmail()).isNotNull();
        assertThat(member.getName()).isNotNull();

        List<Class<?>> generatorNames = generatorFactory.getEntityGeneratorNames();
        System.out.println("generatorNames = " + generatorNames);
        assertThat(Member.class).isIn(generatorNames);
    }

    @Test
    @DisplayName("Verify 10 Member instance generation")
    void create_10_instances() {
        Generator<Member> generator = generatorFactory.getGenerator(Member.class);

        for (int i = 0; i < 10; i++) {
            Member member = generator.get();
            System.out.println("member : " + member.toString());
            assertThat(member.getName()).isNotNull();
        }
    }
}
