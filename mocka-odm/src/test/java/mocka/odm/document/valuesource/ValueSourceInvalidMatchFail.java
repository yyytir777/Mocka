package mocka.odm.document.valuesource;

import mocka.core.annotation.ValueSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ValueSourceInvalidMatchFail {

    @Id
    private String id;

    @ValueSource(generatorKey = "invalid_match.txt")
    private String name;

    public String getName() { return name; }
}
