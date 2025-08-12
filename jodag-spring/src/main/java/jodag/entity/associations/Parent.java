package jodag.entity.associations;


import jakarta.persistence.*;
import jodag.ValueSource;

import java.util.List;

@Entity
public class Parent {
    @Id
    private Long id;

    @ValueSource(path = "test.txt", type = String.class)
    private String name;

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
