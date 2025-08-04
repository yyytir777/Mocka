package jodag.entity.associations;

import jakarta.persistence.*;
import jodag.generator.Generate;

import java.util.List;

@Entity
@Generate
public class Parent {
    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> children;

    @ManyToOne(cascade = CascadeType.ALL)
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
}
