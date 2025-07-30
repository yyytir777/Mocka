package jodag.generator;

import jodag.TestConfig;
import jodag.entity.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@DisplayName("EntityGenerator 테스트 코드")
@SpringBootTest(classes = TestConfig.class)
class EntityGeneratorTest {

    @Test
    @DisplayName("entityGenerator 인스턴스를 반환합니다.")
    void get_instance() {
        Generator<TestEntity> generator = SpringGeneratorFactory.getGenerator(TestEntity.class);
        Assertions.assertThat(generator).isInstanceOf(EntityGenerator.class);
    }
}