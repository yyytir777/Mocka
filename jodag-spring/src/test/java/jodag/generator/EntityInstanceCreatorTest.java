package jodag.generator;

import jakarta.persistence.Embeddable;
import jodag.TestConfig;
import jodag.entity.EmbeddableClass;
import jodag.entity.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TestConfig.class)
class EntityInstanceCreatorTest {

    @Test
    @DisplayName("embeddable객체가 담긴 엔티티에서 embeddable 객체를 생성합니다.")
    void create_embeddable_instance_given_entity_with_embeddable_field() {
        Generator<TestEntity> generator = SpringGeneratorFactory.getGenerator(TestEntity.class);
        TestEntity testEntity = generator.get();
        Assertions.assertThat(testEntity.getEmbeddableClass()).isInstanceOf(EmbeddableClass.class);
    }
}