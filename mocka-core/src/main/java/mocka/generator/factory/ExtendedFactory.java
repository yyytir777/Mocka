package mocka.generator.factory;

import mocka.generator.array.ByteArrayGenerator;
import mocka.generator.array.CharacterArrayGenerator;
import mocka.generator.datetime.DateTimeGenerator;
import mocka.generator.datetime.LegacyDateGenerator;
import mocka.generator.datetime.SqlDateGenerator;
import mocka.generator.numeric.BigDecimalGenerator;
import mocka.generator.numeric.BigIntegerGenerator;

public interface ExtendedFactory {

    default BigIntegerGenerator asBigInteger() {
        return BigIntegerGenerator.getInstance();
    }

    default BigDecimalGenerator asBigDecimal() {
        return BigDecimalGenerator.getInstance();
    }

    default DateTimeGenerator asDateTime() {
        return DateTimeGenerator.getInstance();
    }

    default LegacyDateGenerator asLegacyDate() {
        return LegacyDateGenerator.getInstance();
    }

    default SqlDateGenerator asSqlDate() {
        return SqlDateGenerator.getInstance();
    }

    default ByteArrayGenerator asByteArray() {
        return ByteArrayGenerator.getInstance();
    }

    default CharacterArrayGenerator asCharacterArray() {
        return CharacterArrayGenerator.getInstance();
    }
}
