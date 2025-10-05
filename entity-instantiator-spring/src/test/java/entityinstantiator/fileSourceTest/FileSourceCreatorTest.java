package entityinstantiator.fileSourceTest;

import entityinstantiator.EntityInstantiatorSpringTestApplication;
import entityinstantiator.annotation.FileSource;
import entityinstantiator.entity.hibernate.Member;
import entityinstantiator.generator.FileSourceCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DisplayName("FileSourceCreator Test Code")
@SpringBootTest(classes = EntityInstantiatorSpringTestApplication.class)
public class FileSourceCreatorTest {

    @Autowired
    FileSourceCreator fileSourceCreator;

    @Test
    public void testCreate() {
        FileSource fileSource = Member.class.getAnnotation(FileSource.class);
        Member member = fileSourceCreator.createFromFileSource(Member.class, fileSource);
        System.out.println("member = " + member);
    }
}
