package entityinstantiator.generatorTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.generator.Generator;
import entityinstantiator.generator.SpringGeneratorFactory;
import entityinstantiator.entity.hibernate.EmbeddableClass;
import entityinstantiator.entity.hibernate.TestEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DisplayName("EntityGenerator Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
class EntityInstanceCreatorTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("embeddable이 담긴 엔티티에서 embeddable 객체를 생성합니다.")
    void create_embeddable_instance_given_entity_with_embeddable_field() {
        Generator<TestEntity> generator = springGeneratorFactory.getGenerator(TestEntity.class);
        TestEntity testEntity = generator.get();
        Assertions.assertThat(testEntity.getEmbeddableClass()).isInstanceOf(EmbeddableClass.class);
    }
}