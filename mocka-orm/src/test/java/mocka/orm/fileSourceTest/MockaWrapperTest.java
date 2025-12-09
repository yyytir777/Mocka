package mocka.orm.fileSourceTest;

import mocka.orm.entity.filesource.xml.Person;
import mocka.orm.parser.MockaWrapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

@DisplayName("EntityInstantiatorWrapper Test Code")
class MockaWrapperTest {

    @Test
    @DisplayName("unmarshal XML elements into entity list")
    void unmarshal_xmlElements_into_entity_list() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("file-source/person.xml");

        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = builder.parse(inputStream);
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        List<Element> elements = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element element) {
                elements.add(element);
            }
        }

        MockaWrapper<Person> wrapper = new MockaWrapper<>();
        wrapper.setList(elements);

        // when
        List<Person> result = wrapper.getList(Person.class);

        // then
        assertThat(result)
                .hasSize(2)
                .extracting("name", "age")
                .containsExactly(
                        tuple("Wonjae1", 25),
                        tuple("Wonjae2", 30)
                );
    }
}