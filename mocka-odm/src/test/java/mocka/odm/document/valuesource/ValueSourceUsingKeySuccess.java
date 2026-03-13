package mocka.odm.document.valuesource;

import mocka.core.annotation.ValueSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ValueSourceUsingKeySuccess {

    @Id
    private String id;

    @ValueSource(path = "test.txt", type = String.class)
    private String name;

    public String getName() { return name; }
}
