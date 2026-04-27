package mocka.odm.document.valuesource;

import mocka.core.annotation.ValueSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ValueSourceSuccess {

    @Id
    private String id;

    @ValueSource(generatorKey = "test")
    private String name;

    @ValueSource(generatorKey = "test1")
    private String description;

    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "ValueSourceSuccess{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
