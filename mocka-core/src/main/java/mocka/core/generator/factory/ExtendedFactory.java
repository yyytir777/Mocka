package mocka.core.generator.factory;

import mocka.core.generator.array.ByteArrayGenerator;
import mocka.core.generator.array.CharacterArrayGenerator;
import mocka.core.generator.datetime.DateTimeGenerator;
import mocka.core.generator.datetime.LegacyDateGenerator;
import mocka.core.generator.datetime.SqlDateGenerator;
import mocka.core.generator.numeric.BigDecimalGenerator;
import mocka.core.generator.numeric.BigIntegerGenerator;

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
