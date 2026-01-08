package mocka.core.generator.factory;

import mocka.core.generator.primitive.*;

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
