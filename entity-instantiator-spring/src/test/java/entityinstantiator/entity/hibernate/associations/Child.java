package entityinstantiator.entity.hibernate.associations;

import entityinstantiator.annotation.ValueSource;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Child {
    @Id
    private Long id;

    @ValueSource(generatorKey = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Parent parent;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Parent getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "Child{name='" + name + '\'' +
                '}';
    }
}
