package entityinstantiator.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import entityinstantiator.annotation.ValueSource;

@Entity
public class ValueSource5 {
    @Id
    private Long id;

    @ValueSource(generatorKey = "name")
    private String name;

    @ValueSource(generatorKey = "email")
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "ValueSource5{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
