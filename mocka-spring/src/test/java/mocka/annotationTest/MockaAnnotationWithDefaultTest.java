package mocka.annotationTest;

import mocka.annotation.mocka.Mocka;
import mocka.annotation.mocka.MockaExtension;
import mocka.entity.hibernate.associations.Parent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockaExtension.class)
@DisplayName("Mocka Annotation With Default(GenerateType.SELF) Test")
public class MockaAnnotationWithDefaultTest {

    @Mocka
    Parent hibernateParent; // hibernate

    @Mocka
    mocka.entity.mybatis.associations.Parent myBatisParent; // mybatis


    @Test
    void test_mocka_annotation_test_with_hibernate() {
        System.out.println("hibernateParent = " + hibernateParent);
        assertThat(hibernateParent).isNotNull();
        assertThat(hibernateParent.getGrandParent()).isNull();
        assertThat(hibernateParent.getChildren()).isNull();
    }

    @Test
    void test_mocka_annotation_test_with_mybatis() {
        System.out.println("myBatisParent = " + myBatisParent);
        assertThat(myBatisParent).isNotNull();
        assertThat(myBatisParent.getGrandParent()).isNull();
        assertThat(myBatisParent.getChildren()).isNull();
    }
}
