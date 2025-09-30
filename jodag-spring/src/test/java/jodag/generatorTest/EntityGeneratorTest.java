package jodag.generatorTest;

import jodag.JodagTestApplication;
import jodag.generator.EntityGenerator;
import jodag.generator.Generator;
import jodag.generator.SpringGeneratorFactory;
import jodag.hibernate.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@DisplayName("EntityGenerator Test Code")
@SpringBootTest(classes = JodagTestApplication.class)
class EntityGeneratorTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("returns entityGenerator instance")
    void get_instance() {
        Generator<TestEntity> generator = springGeneratorFactory.getGenerator(TestEntity.class);
        Assertions.assertThat(generator).isInstanceOf(EntityGenerator.class);
    }
}