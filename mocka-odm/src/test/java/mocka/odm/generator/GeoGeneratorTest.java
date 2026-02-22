package mocka.odm.generator;

import mocka.odm.generator.factory.GeoGenerator;
import mocka.odm.generator.factory.ODMGeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Point;
import org.springframework.data.geo.Polygon;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("GeoGenerator Test Code")
public class GeoGeneratorTest {

    private final ODMGeneratorFactory odmGeneratorFactory = new ODMGeneratorFactory();
    private final GeoGenerator geoGenerator = odmGeneratorFactory.asGeo();

    @Test
    @DisplayName("throws UnsupportedOperationException when get() is called")
    void throw_unsupported_exception_when_invoked_get_method() {
        assertThatThrownBy(geoGenerator::get)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("return Point from GeoGenerator")
    void return_Point_from_GeoGenerator() {
        Point point = geoGenerator.getPoint();
        assertThat(point).isNotNull();
        assertThat(point.getX()).isBetween(-100.0, 100.0);
        assertThat(point.getY()).isBetween(-100.0, 100.0);
    }

    @Test
    @DisplayName("return List<Point> from GeoGenerator with given size")
    void return_List_from_GeoGenerator_with_given_size() {
        List<Point> points = geoGenerator.getPoints(10);
        assertThat(points).hasSize(10);
        points.forEach(point -> {
                    assertThat(point.getX()).isBetween(-100.0, 100.0);
                    assertThat(point.getY()).isBetween(-100.0, 100.0);
                });
    }

    @Test
    @DisplayName("return Box from GeoGenerator")
    void return_Box_from_GeoGenerator() {
        Box box = geoGenerator.getBox();
        System.out.println("box = " + box);
        assertThat(box).isNotNull();
        assertThat(box.getFirst().getX()).isBetween(-100.0, 100.0);
        assertThat(box.getFirst().getY()).isBetween(-100.0, 100.0);
        assertThat(box.getSecond().getX()).isBetween(-100.0, 100.0);
        assertThat(box.getSecond().getY()).isBetween(-100.0, 100.0);
    }

    @Test
    @DisplayName("return GeoBox from GeoGenerator")
    void return_GeoBox_from_GeoGenerator() {
        Box geoBox = geoGenerator.getGeoBox();
        System.out.println("geoBox = " + geoBox);
        assertThat(geoBox).isNotNull();
        assertThat(geoBox.getFirst().getX()).isBetween(-180.0, 100.0);
        assertThat(geoBox.getFirst().getY()).isBetween(-90.0, 90.0);
        assertThat(geoBox.getSecond().getX()).isBetween(-180.0, 100.0);
        assertThat(geoBox.getSecond().getY()).isBetween(-90.0, 90.0);
    }

    @Test
    @DisplayName("return Circle from GeoGenerator")
    void return_Circle_from_GeoGenerator() {
        Circle circle = geoGenerator.getCircle();
        System.out.println("circle = " + circle);
        assertThat(circle).isNotNull();
        assertThat(circle).isInstanceOf(Circle.class);
    }

    @Test
    @DisplayName("return Circle from GeoGenerator with given point")
    void return_Circle_from_GeoGenerator_with_given_value() {
        Point point = new Point(1, 1);
        Circle circle = geoGenerator.getCircle(point);
        System.out.println("circle = " + circle);
        assertThat(circle).isNotNull();
        assertThat(circle).isInstanceOf(Circle.class);
        assertThat(circle.getCenter()).isEqualTo(point);
    }

    @Test
    @DisplayName("return Circle from GeoGenerator with given radius")
    void return_Circle_from_GeoGenerator_with_given_radius() {
        double radius = 4.0;
        Circle circle = geoGenerator.getCircle(radius);
        System.out.println("circle = " + circle);
        assertThat(circle).isNotNull();
        assertThat(circle).isInstanceOf(Circle.class);
        assertThat(circle.getRadius().getValue()).isEqualTo(radius);
    }

    @Test
    @DisplayName("throw IllegalArgumentException when negative radius is given")
    void throw_IllegalArgumentException_when_negative_radius_is_given() {
        double radius = -4.0;
        assertThatThrownBy(() -> geoGenerator.getCircle(radius))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("return Polygon from GeoGenerator")
    void return_Polygon_from_GeoGenerator() {
        Polygon polygon = geoGenerator.getPolygon();
        System.out.println("polygon = " + polygon);
        assertThat(polygon).isNotNull();
        assertThat(polygon).isInstanceOf(Polygon.class);
    }

    @Test
    @DisplayName("return Polygon from GeoGenerator with given the count of point")
    void return_Polygon_from_GeoGenerator_with_given_count_of_point() {
        int count = 10;
        Polygon polygon = geoGenerator.getPolygon(count);
        System.out.println("polygon = " + polygon);
        assertThat(polygon).isNotNull();
        assertThat(polygon).isInstanceOf(Polygon.class);
        assertThat(polygon.getPoints()).hasSize(count);
    }
}
