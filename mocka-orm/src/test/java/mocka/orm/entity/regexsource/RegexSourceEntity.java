package mocka.orm.entity.regexsource;

import mocka.orm.annotation.RegexSource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegexSourceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RegexSource("\\d{10}")
    private String description;

    public String getDescription() {
        return description;
    }
}
