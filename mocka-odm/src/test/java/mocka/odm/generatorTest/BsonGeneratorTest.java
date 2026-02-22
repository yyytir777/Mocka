package mocka.odm.generatorTest;

import mocka.odm.generator.factory.BsonGenerator;
import mocka.odm.generator.factory.ODMGeneratorFactory;
import org.bson.types.BSONTimestamp;
import org.bson.types.Binary;
import org.bson.types.Decimal128;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@DisplayName("BsonGenerator Test Code")
public class BsonGeneratorTest {

    private final ODMGeneratorFactory odmGeneratorFactory = new ODMGeneratorFactory();
    private final BsonGenerator bsonGenerator = odmGeneratorFactory.asBson();

    @Test
    @DisplayName("throws UnsupportedOperationException when get() is called")
    void throw_unsupported_exception_when_invoked_get_method() {
        assertThatThrownBy(bsonGenerator::get)
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("return ObjectId from BsonGenerator")
    void return_ObjectId_from_BsonGenerator() {
        ObjectId objectId = bsonGenerator.getObjectId();
        System.out.println("objectId = " + objectId);
        assertThat(objectId).isNotNull();
        assertThat(objectId).isInstanceOf(ObjectId.class);
    }

    @Test
    @DisplayName("return Binary from BsonGenerator")
    void return_Binary_from_BsonGenerator() {
        Binary binary = bsonGenerator.getBinary();
        System.out.println("binary = " + Arrays.toString(binary.getData()));
        assertThat(binary).isNotNull();
        assertThat(binary).isInstanceOf(Binary.class);
    }

    @Test
    @DisplayName("return Decimal128 from BsonGenerator")
    void return_Decimal128_from_BsonGenerator() {
        Decimal128 decimal128 = bsonGenerator.getDecimal128();
        System.out.println("decimal128 = " + decimal128);
        assertThat(decimal128).isNotNull();
        assertThat(decimal128).isInstanceOf(Decimal128.class);
    }

    @Test
    @DisplayName("return BSONTimestamp from BsonGenerator")
    void return_BSONTimestamp_from_BsonGenerator() {
        BSONTimestamp bsonTimestamp = bsonGenerator.getBsonTimestamp();
        System.out.println("bsonTimestamp = " + bsonTimestamp);
        assertThat(bsonTimestamp).isNotNull();
        assertThat(bsonTimestamp).isInstanceOf(BSONTimestamp.class);
    }
}
