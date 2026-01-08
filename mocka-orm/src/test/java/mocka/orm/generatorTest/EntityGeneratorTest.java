package mocka.orm.generatorTest;

import mocka.orm.MockaSpringTestApplication;
import mocka.orm.generator.EntityGenerator;
import mocka.core.generator.Generator;
import mocka.orm.entity.orm.TestEntity;
import mocka.orm.generator.EntityGeneratorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@DisplayName("EntityGenerator Test Code")
@SpringBootTest(classes = MockaSpringTestApplication.class)
class EntityGeneratorTest {

    @Autowired
    EntityGeneratorFactory generatorFactory;

    @Test
    @DisplayName("returns entityGenerator instance")
    void get_instance() {
        Generator<TestEntity> generator = generatorFactory.getGenerator(TestEntity.class);
        Assertions.assertThat(generator).isInstanceOf(EntityGenerator.class);
    }
}