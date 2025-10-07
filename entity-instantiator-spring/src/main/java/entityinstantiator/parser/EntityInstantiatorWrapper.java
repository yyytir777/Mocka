package entityinstantiator.parser;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "entityInstantiator")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntityInstantiatorWrapper<T> {

    @XmlAnyElement(lax = true)
    private List<Element> list;

    public List<T> getList(Class<T> clazz) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            List<T> result = new ArrayList<>();
            for (Element element : list) {
                JAXBElement<T> entity = unmarshaller.unmarshal(element, clazz);
                result.add(entity.getValue());
            }
            return result;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
    public void setList(List<Element> list) {
        this.list = list;
    }
}
