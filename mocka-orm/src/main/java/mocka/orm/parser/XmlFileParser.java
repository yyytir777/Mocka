package mocka.orm.parser;

import mocka.core.random.RandomProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class XmlFileParser implements FileParser {

    private static final XmlFileParser INSTANCE = new XmlFileParser();

    private final RandomProvider randomProvider = RandomProvider.getInstance();

    private XmlFileParser() {}

    public static XmlFileParser getInstance() {
        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T parse(InputStream inputStream, Class<T> clazz) {
        try {
            if (inputStream == null || inputStream.available() == 0) {
                throw new RuntimeException("Empty XML input: no content to parse.");
            }

            if (!inputStream.markSupported()) {
                inputStream = new BufferedInputStream(inputStream);
            }
            inputStream.mark(Integer.MAX_VALUE);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);

            Node root = doc.getDocumentElement();
            NodeList nodes = root.getChildNodes();
            List<T> list = new ArrayList<>();

            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    T instance = clazz.getDeclaredConstructor().newInstance();
                    NodeList fields = node.getChildNodes();

                    for (int j = 0; j < fields.getLength(); j++) {
                        Node fieldNode = fields.item(j);
                        if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                            try {
                                Field field = clazz.getDeclaredField(fieldNode.getNodeName());
                                field.setAccessible(true);
                                field.set(instance, convertValue(field.getType(), fieldNode.getTextContent()));
                            } catch (NoSuchFieldException ignore) {
                                // 필드 없으면 무시
                            }
                        }
                    }
                    list.add(instance);
                }
            }

            if (list.isEmpty())
                throw new RuntimeException("No valid XML entities found for class: " + clazz.getSimpleName());

            return list.get(randomProvider.getNextIdx(list.size()));
        } catch (IOException e) {
            throw new RuntimeException("I/O error while reading XML file: " + e.getMessage(), e);
        } catch (ParserConfigurationException | SAXException | InvocationTargetException | InstantiationException |
                 IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
