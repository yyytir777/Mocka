package jodag.entity.associations;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Tmp {

    @Id
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private GrandParent grandParent;

    public GrandParent getGrandParent() {
        return grandParent;
    }

    public Long getId() {
        return id;
    }
}
