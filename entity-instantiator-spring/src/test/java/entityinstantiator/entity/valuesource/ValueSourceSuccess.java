package entityinstantiator.entity.valuesource;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import entityinstantiator.annotation.ValueSource;

@Entity
public class ValueSourceSuccess {
    @Id
    private Long id;

    @ValueSource(generatorKey = "test")
    private String name;

    @ValueSource(generatorKey = "test1")
    private String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "ValueSourceEntity{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
