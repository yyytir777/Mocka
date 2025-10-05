package entityinstantiator.entity.hibernate.associations;


import jakarta.persistence.*;
import entityinstantiator.annotation.ValueSource;

import java.util.List;

@Entity
public class Parent {
    @Id
    private Long id;

    @ValueSource(generatorKey = "email")
    private String name;

    private String test;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children;

    @ManyToOne(fetch = FetchType.LAZY)
    private GrandParent grandParent;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTest() {
        return test;
    }

    public List<Child> getChildren() {
        return children;
    }

    public GrandParent getGrandParent() {
        return grandParent;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
