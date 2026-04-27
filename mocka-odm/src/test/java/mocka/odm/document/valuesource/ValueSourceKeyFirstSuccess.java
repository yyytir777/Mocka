package mocka.odm.document.valuesource;

import mocka.core.annotation.ValueSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ValueSourceKeyFirstSuccess {

    @Id
    private String id;

    @ValueSource(path = "test.txt", type = String.class, generatorKey = "test1")
    private String name;

    public String getName() { return name; }
}
