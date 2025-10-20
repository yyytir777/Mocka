package mocka.generator.factory;

import mocka.generator.primitive.StringGenerator;
import mocka.generator.primitive.*;

public interface PrimitiveFactory {

    default BooleanGenerator asBoolean() {
        return BooleanGenerator.getInstance();
    }

    default ByteGenerator asByte() {
        return ByteGenerator.getInstance();
    }

    default ShortGenerator asShort() {
        return ShortGenerator.getInstance();
    }

    default IntegerGenerator asInteger() {
        return IntegerGenerator.getInstance();
    }

    default LongGenerator asLong() {
        return LongGenerator.getInstance();
    }

    default CharacterGenerator asCharacter() {
        return CharacterGenerator.getInstance();
    }

    default DoubleGenerator asDouble() {
        return DoubleGenerator.getInstance();
    }

    default FloatGenerator asFloat() {
        return FloatGenerator.getInstance();
    }

    default StringGenerator asString() {
        return StringGenerator.getInstance();
    }
}
