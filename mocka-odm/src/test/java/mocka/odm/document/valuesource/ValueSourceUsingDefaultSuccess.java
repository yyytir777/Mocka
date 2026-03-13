package mocka.odm.document.valuesource;

import mocka.core.annotation.ValueSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ValueSourceUsingDefaultSuccess {

    @Id
    private String id;

    @ValueSource(generatorKey = "name")
    private String name;

    @ValueSource(generatorKey = "email")
    private String email;

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "ValueSourceUsingDefaultSuccess{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
