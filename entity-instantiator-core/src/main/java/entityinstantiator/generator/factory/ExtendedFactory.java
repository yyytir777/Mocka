package entityinstantiator.generator.factory;

import entityinstantiator.generator.array.ByteArrayGenerator;
import entityinstantiator.generator.array.CharacterArrayGenerator;
import entityinstantiator.generator.datetime.DateTimeGenerator;
import entityinstantiator.generator.datetime.LegacyDateGenerator;
import entityinstantiator.generator.datetime.SqlDateGenerator;
import entityinstantiator.generator.numeric.BigDecimalGenerator;
import entityinstantiator.generator.numeric.BigIntegerGenerator;

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
