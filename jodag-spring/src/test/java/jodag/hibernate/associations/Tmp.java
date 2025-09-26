package jodag.hibernate.associations;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tmp {

    @Id
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private GrandParent grandParent;

    @OneToMany(mappedBy = "tmp", cascade = CascadeType.ALL)
    private List<Child> children;

    public GrandParent getGrandParent() {
        return grandParent;
    }

    public List<Child> getChildren() {
        return children;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Tmp{" + name + '}';
    }

}
