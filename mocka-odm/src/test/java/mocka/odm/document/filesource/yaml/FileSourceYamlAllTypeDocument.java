package mocka.odm.document.filesource.yaml;

import mocka.core.annotation.FileSource;
import mocka.odm.document.MemberEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.Date;

@Document
@FileSource("file-source/file_source_all_type.yaml")
public class FileSourceYamlAllTypeDocument {

    @Id
    private String id;

    private String name;
    private String email;

    private Integer typeInteger;
    private Long typeLong;
    private Double typeDouble;
    private Boolean typeBoolean;

    private BigInteger bigInteger;
    private BigDecimal bigDecimal;

    private Date date;

    private LocalDate localDate;
    private LocalTime localTime;
    private LocalDateTime localDateTime;
    private OffsetDateTime offsetDateTime;
    private OffsetTime offsetTime;
    private Instant instant;

    private MemberEnum memberEnum;

    public FileSourceYamlAllTypeDocument() {}

    public String getName() { return name; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "FileSourceYamlAllTypeDocument{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", typeInteger=" + typeInteger +
                ", typeLong=" + typeLong +
                ", typeDouble=" + typeDouble +
                ", typeBoolean=" + typeBoolean +
                ", bigInteger=" + bigInteger +
                ", bigDecimal=" + bigDecimal +
                ", date=" + date +
                ", localDate=" + localDate +
                ", localTime=" + localTime +
                ", localDateTime=" + localDateTime +
                ", offsetDateTime=" + offsetDateTime +
                ", offsetTime=" + offsetTime +
                ", instant=" + instant +
                ", memberEnum=" + memberEnum +
                '}';
    }
}
