package entityinstantiator.parser;

import entityinstantiator.random.RandomProvider;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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

            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

            while (reader.hasNext()) {
                if (reader.next() == XMLStreamConstants.START_ELEMENT) break;
            }
            String rootName = reader.getLocalName();

            inputStream.reset();

            if (rootName.equals("entityInstantiator")) {
                JAXBContext context = JAXBContext.newInstance(EntityInstantiatorWrapper.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                EntityInstantiatorWrapper<T> wrapper =
                        (EntityInstantiatorWrapper<T>) unmarshaller.unmarshal(inputStream);

                List<T> list = wrapper.getList(clazz);

                if (list == null || list.isEmpty()) {
                    throw new RuntimeException("Empty XML input: no content to parse.");
                }

                return list.get(randomProvider.getNextIdx(list.size()));
            }

            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(inputStream), clazz).getValue();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to parse XML file: " + e.getMessage(), e);
        } catch (XMLStreamException e) {
            throw new RuntimeException("XML stream error: malformed XML or unexpected end of file. " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while reading XML file: " + e.getMessage(), e);
        }
    }
}
