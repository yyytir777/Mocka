package jodag.mybatisTest;


import jodag.JodagTestApplication;
import jodag.generator.EntityGenerator;
import jodag.generator.GenerateType;
import jodag.generator.SpringGeneratorFactory;
import jodag.mybatis.associations.GrandParent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@DisplayName("Mybatis ONE_TO_MANY 테스트 코드")
@ActiveProfiles("test")
@SpringBootTest(classes = JodagTestApplication.class)
public class OneToManyTest {

    @Autowired
    SpringGeneratorFactory springGeneratorFactory;

    @Test
    @DisplayName("mybatis one_to_many로 엔티티를 생성합니다.")
    void oneToMany() {
        EntityGenerator<GrandParent> generator = springGeneratorFactory.getGenerator(GrandParent.class);
        GrandParent grandParent = generator.get(GenerateType.CHILDREN);
        System.out.println("grandParent = " + grandParent);
        System.out.println("Parent = " + grandParent.getParents());
        System.out.println("Children = " + grandParent.getParents().get(0).getChildren());
    }
}
