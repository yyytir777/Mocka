package mocka.entity.regexsource;

import mocka.annotation.RegexSource;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RegexSourceFailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @RegexSource(pattern = "\\d{10")
    private String description;

    public String getDescription() {
        return description;
    }
}
