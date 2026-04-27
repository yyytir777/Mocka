package mocka.odm.generatorTest;

import mocka.core.GenerateType;
import mocka.odm.MockaOdmTestApplication;
import mocka.odm.document.association.GrandParent;
import mocka.odm.document.association.Parent;
import mocka.odm.generator.mongodb.MongodbCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = MockaOdmTestApplication.class)
@DisplayName("MongodbCreator Edge Case Test")
class MongodbCreatorTest {

    private static final int DEFAULT_ASSOCIATION_SIZE = 5;

    @Autowired
    private MongodbCreator mongodbCreator;

    @AfterEach
    void restoreDefaultAssociationSize() {
        mongodbCreator.setAssociationSize(DEFAULT_ASSOCIATION_SIZE);
    }

    @Test
    @DisplayName("create with null GenerateType returns null")
    void create_withNullGenerateType_returnsNull() {
        Parent result = mongodbCreator.create(Parent.class, (GenerateType) null);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("create with SELF type does not populate @DBRef fields")
    void create_withSelfGenerateType_doesNotPopulateDBRefFields() {
        Parent parent = mongodbCreator.create(Parent.class, GenerateType.SELF);

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNull();
        assertThat(parent.getGrandParent()).isNull();
    }

    @Test
    @DisplayName("create with CHILD type populates collection @DBRef but not single @DBRef")
    void create_withChildGenerateType_populatesCollectionDBRefOnly() {
        Parent parent = mongodbCreator.create(Parent.class, GenerateType.CHILD);

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNull();
    }

    @Test
    @DisplayName("create with PARENT type populates single @DBRef but not collection @DBRef")
    void create_withParentGenerateType_populatesSingleDBRefOnly() {
        Parent parent = mongodbCreator.create(Parent.class, GenerateType.PARENT);

        assertThat(parent).isNotNull();
        assertThat(parent.getGrandParent()).isNotNull().isInstanceOf(GrandParent.class);
        assertThat(parent.getChildren()).isNull();
    }

    @Test
    @DisplayName("setAssociationSize changes the number of generated collection elements")
    void setAssociationSize_changesCollectionElementCount() {
        int customSize = 3;
        mongodbCreator.setAssociationSize(customSize);

        Parent parent = mongodbCreator.create(Parent.class, GenerateType.CHILD);

        assertThat(parent.getChildren()).hasSize(customSize);
    }

    @Test
    @DisplayName("create with CHILDREN type populates collection @DBRef recursively")
    void create_withChildrenGenerateType_populatesCollectionDBRefRecursively() {
        Parent parent = mongodbCreator.create(Parent.class, GenerateType.CHILDREN);

        assertThat(parent).isNotNull();
        assertThat(parent.getChildren()).isNotNull().isNotEmpty();
        assertThat(parent.getGrandParent()).isNull();
    }

    @Test
    @DisplayName("create with PARENTS type populates single @DBRef recursively")
    void create_withParentsGenerateType_populatesSingleDBRefRecursively() {
        Parent parent = mongodbCreator.create(Parent.class, GenerateType.PARENTS);

        assertThat(parent).isNotNull();
        assertThat(parent.getGrandParent()).isNotNull();
        assertThat(parent.getChildren()).isNull();
    }
}
