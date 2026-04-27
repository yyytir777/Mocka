package mocka.odm.document;

import org.bson.types.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;
import org.springframework.data.mongodb.core.geo.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.*;
import java.util.*;

@Document
public class Member {

    @Id
    private String id;

    private String name;

    private String implicit;

    private Boolean typeBoolean;

    private Double typeDouble;

    private String typeString;

    private String[] stringArray;

    private float[] floatArray;


    // enum
    private MemberEnum memberEnum;

    // UUID
    private UUID uuid;

    // org.bson
    private Binary typeBinary;
    private ObjectId typeObjectId;
    private BSONTimestamp typeTimestamp;
    private Decimal128 typeDecimal128;


    // java.time & java.sql
    private Date typeDate;
    private LocalDate typeLocalDate;
    private LocalDateTime typeLocalDateTime;
    private LocalTime typeLocalTime;
    private Instant typeInstant;
    private ZonedDateTime typeZonedDateTime;
    private OffsetDateTime typeOffsetDateTime;


    // org.springframework.data.geo
    private Point typePoint;
    private Box typeBox;
    private Circle typeCircle;
    private Polygon typePolygon;

    // org.springframework.data.mongodb.core.geo
    private GeoJsonPoint typeGeoJsonPoint;
    private GeoJsonPolygon typeGeoJsonPolygon;
    private GeoJsonLineString typeGeoJsonLineString;
    private GeoJsonMultiPoint typeGeoJsonMultiPoint;
    private GeoJsonMultiPolygon typeGeoJsonMultiPolygon;
    private GeoJsonMultiLineString typeGeoJsonMultiLineString;

    // Collections
    private List<String> strings;
    private Set<Integer> integers;
    private List<Float> floats;
    private Collection<Long> longs;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' + '\n' +
                ", name='" + name + '\'' + '\n' +
                ", implicit='" + implicit + '\'' + '\n' +
                ", typeBoolean=" + typeBoolean + '\n' +
                ", typeDouble=" + typeDouble + '\n' +
                ", typeString='" + typeString + '\'' + '\n' +
                ", stringArray=" + Arrays.toString(stringArray) + '\n' +
                ", floatArray=" + Arrays.toString(floatArray) + '\n' +
                ", memberEnum=" + memberEnum + '\n' +
                ", uuid=" + uuid + '\n' +
                ", typeBinary=" + typeBinary + '\n' +
                ", typeObjectId=" + typeObjectId + '\n' +
                ", typeTimestamp=" + typeTimestamp + '\n' +
                ", typeDecimal128=" + typeDecimal128 + '\n' +
                ", typeDate=" + typeDate + '\n' +
                ", typeLocalDate=" + typeLocalDate + '\n' +
                ", typeLocalDateTime=" + typeLocalDateTime + '\n' +
                ", typeLocalTime=" + typeLocalTime + '\n' +
                ", typeInstant=" + typeInstant + '\n' +
                ", typeZonedDateTime=" + typeZonedDateTime + '\n' +
                ", typeOffsetDateTime=" + typeOffsetDateTime + '\n' +
                ", typePoint=" + typePoint + '\n' +
                ", typeBox=" + typeBox + '\n' +
                ", typeCircle=" + typeCircle + '\n' +
                ", typePolygon=" + typePolygon + '\n' +
                ", typeGeoJsonPoint=" + typeGeoJsonPoint + '\n' +
                ", typeGeoJsonPolygon=" + typeGeoJsonPolygon + '\n' +
                ", typeGeoJsonLineString=" + typeGeoJsonLineString + '\n' +
                ", typeGeoJsonMultiPolygon=" + typeGeoJsonMultiPolygon + '\n' +
                ", typeGeoJsonMultiLineString=" + typeGeoJsonMultiLineString + '\n' +
                ", strings=" + strings + '\n' +
                ", integers=" + integers + '\n' +
                ", floats=" + floats + '\n' +
                ", longs=" + longs +
                '}';
    }
}
