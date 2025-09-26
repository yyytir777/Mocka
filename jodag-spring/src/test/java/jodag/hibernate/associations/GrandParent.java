package jodag.hibernate.associations;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class GrandParent {
    @Id
    private Long id;

    private String name;

    @OneToMany
    private List<Parent> parents;

    @OneToMany
    private List<Tmp> tmps;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Parent> getParents() {
        return parents;
    }

    public List<Tmp> getTmps() {
        return tmps;
    }

    @Override
    public String toString() {
        return "GrandParent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
