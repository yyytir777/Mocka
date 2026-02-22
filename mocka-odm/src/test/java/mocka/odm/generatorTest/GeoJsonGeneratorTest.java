package mocka.odm.generatorTest;

import mocka.odm.generator.factory.GeoJsonGenerator;
import mocka.odm.generator.factory.ODMGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.mongodb.core.geo.*;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("GeoJsonGenerator Test Code")
public class GeoJsonGeneratorTest {

    private final ODMGeneratorFactory odmGeneratorFactory = new ODMGeneratorFactory();
    private final GeoJsonGenerator geoJsonGenerator = odmGeneratorFactory.asGeoJson();

    @Test
    @DisplayName("throws UnsupportedOperationException when get() is called")
    void throw_unsupported_exception_when_invoked_get_method() {
        assertThatThrownBy(geoJsonGenerator::get)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("return GeoJsonPoint from GeoJsonGenerator")
    void return_GeoJsonPoint_from_geoJsonGenerator() {
        GeoJsonPoint geoJsonPoint = geoJsonGenerator.getGeoJsonPoint();
        System.out.println("geoJsonPoint = " + geoJsonPoint);
        assertThat(geoJsonPoint.getCoordinates().get(0)).isBetween(-90.0, 90.0);
        assertThat(geoJsonPoint.getCoordinates().get(1)).isBetween(-180.0, 180.0);
    }

    @Test
    @DisplayName("return List<GeoJsonPoint> from GeoJsonGenerator with random size")
    void return_GeoJsonPoint_list_with_random_size() {
        List<GeoJsonPoint> geoJsonPoints = geoJsonGenerator.getGeoJsonPoints();
        assertThat(geoJsonPoints.size()).isBetween(3, 10);
        geoJsonPoints.forEach(geoJsonPoint -> {
            assertThat(geoJsonPoint).isNotNull();
            assertThat(geoJsonPoint).isInstanceOf(GeoJsonPoint.class);
        });
    }

    @Test
    @DisplayName("return List<GeoJsonPoint> from GeoJsonGenerator with given size")
    void return_GeoJsonPoint_list_with_given_size() {
        int size = 5;
        List<GeoJsonPoint> geoJsonPoints = geoJsonGenerator.getGeoJsonPoints(size);
        System.out.println("geoJsonPoints = " + geoJsonPoints);
        assertThat(geoJsonPoints).hasSize(size);
        geoJsonPoints.forEach(p -> assertThat(p).isNotNull());
    }

    @Test
    @DisplayName("return GeoJsonMultiPoint from GeoJsonGenerator with random size")
    void return_GeoJsonMultiPoint_with_random_size() {
        GeoJsonMultiPoint multiPoint = geoJsonGenerator.getGeoJsonMultiPoint();
        System.out.println("multiPoint = " + multiPoint);
        assertThat(multiPoint).isNotNull();
        assertThat(multiPoint.getCoordinates().size()).isBetween(3, 10);
    }

    @Test
    @DisplayName("return GeoJsonMultiPoint from GeoJsonGenerator with given count")
    void return_GeoJsonMultiPoint_with_given_count() {
        int cnt = 5;
        GeoJsonMultiPoint multiPoint = geoJsonGenerator.getGeoJsonMultiPoint(cnt);
        System.out.println("multiPoint = " + multiPoint);
        assertThat(multiPoint.getCoordinates()).hasSize(cnt);
    }

    @Test
    @DisplayName("return GeoJsonLineString from GeoJsonGenerator with random size")
    void return_GeoJsonLineString_with_random_size() {
        GeoJsonLineString lineString = geoJsonGenerator.getGeoJsonLineString();
        System.out.println("lineString = " + lineString);
        assertThat(lineString).isNotNull();
        assertThat(lineString.getCoordinates().size()).isBetween(3, 10);
    }

    @Test
    @DisplayName("return GeoJsonLineString from GeoJsonGenerator with given size")
    void return_GeoJsonLineString_with_given_size() {
        int size = 4;
        GeoJsonLineString lineString = geoJsonGenerator.getGeoJsonLineString(size);
        System.out.println("lineString = " + lineString);
        assertThat(lineString.getCoordinates()).hasSize(size);
    }

    @Test
    @DisplayName("return GeoJsonPolygon from GeoJsonGenerator with random size")
    void return_GeoJsonPolygon_with_random_size() {
        GeoJsonPolygon polygon = geoJsonGenerator.getGeoJsonPolygon();
        System.out.println("polygon = " + polygon);
        assertThat(polygon).isNotNull();
        assertThat(polygon).isInstanceOf(GeoJsonPolygon.class);
    }

    @Test
    @DisplayName("return GeoJsonPolygon from GeoJsonGenerator with given size")
    void return_GeoJsonPolygon_with_given_size() {
        int size = 5;
        GeoJsonPolygon polygon = geoJsonGenerator.getGeoJsonPolygon(size);
        System.out.println("polygon = " + polygon);
        assertThat(polygon).isNotNull();
        assertThat(polygon).isInstanceOf(GeoJsonPolygon.class);
    }

    @Test
    @DisplayName("return GeoJsonMultiPolygon from GeoJsonGenerator with random size")
    void return_GeoJsonMultiPolygon_with_random_size() {
        GeoJsonMultiPolygon multiPolygon = geoJsonGenerator.getGeoJsonMultiPolygon();
        System.out.println("multiPolygon = " + multiPolygon);
        assertThat(multiPolygon).isNotNull();
        assertThat(multiPolygon.getCoordinates().size()).isBetween(3, 10);
    }

    @Test
    @DisplayName("return GeoJsonMultiPolygon from GeoJsonGenerator with given size")
    void return_GeoJsonMultiPolygon_with_given_size() {
        int size = 4;
        GeoJsonMultiPolygon multiPolygon = geoJsonGenerator.getGeoJsonMultiPolygon(size);
        System.out.println("multiPolygon = " + multiPolygon);
        assertThat(multiPolygon.getCoordinates()).hasSize(size);
    }

    @Test
    @DisplayName("return GeoJsonMultiLineString from GeoJsonGenerator with random size")
    void return_GeoJsonMultiLineString_with_random_size() {
        GeoJsonMultiLineString multiLineString = geoJsonGenerator.getGeoJsonMultiLineString();
        System.out.println("multiLineString = " + multiLineString);
        assertThat(multiLineString).isNotNull();
    }

    @Test
    @DisplayName("return GeoJsonMultiLineString from GeoJsonGenerator with given size")
    void return_GeoJsonMultiLineString_with_given_size() {
        int size = 3;
        GeoJsonMultiLineString multiLineString = geoJsonGenerator.getGeoJsonMultiLineString(size);
        System.out.println("multiLineString = " + multiLineString);
        assertThat(multiLineString.getCoordinates()).hasSize(size);
    }

    @Test
    @DisplayName("return Sphere from GeoJsonGenerator")
    void return_Sphere() {
        Sphere sphere = geoJsonGenerator.getSphere();
        System.out.println("sphere = " + sphere);
        assertThat(sphere).isNotNull();
        assertThat(sphere).isInstanceOf(Sphere.class);
    }
}
