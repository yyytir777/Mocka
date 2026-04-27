package mocka.odm.document.regexsource;

import mocka.core.annotation.RegexSource;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RegexSourceDocument {

    @Id
    private String id;

    @RegexSource("\\d{10}")
    private String description;

    public String getDescription() {
        return description;
    }
}
