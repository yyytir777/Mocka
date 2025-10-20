package mocka.generator.factory;

import mocka.generator.common.*;

public interface CommonFactory {

    default EmailGenerator asEmail() {
        return EmailGenerator.getInstance();
    }

    default NameGenerator asName() {
        return NameGenerator.getInstance();
    }

    default LoremIpsumGenerator asLoremIpsum() {
        return LoremIpsumGenerator.getInstance();
    }

    default CountryGenerator asCountry() {
        return CountryGenerator.getInstance();
    }

    default PhoneNumberGenerator asPhoneNumber() {
        return PhoneNumberGenerator.getInstance();
    }

    default NetworkAddressGenerator asNetworkAddress() {
        return NetworkAddressGenerator.getInstance();
    }

    default DateGenerator asDate() {
        return DateGenerator.getInstance();
    }
}
