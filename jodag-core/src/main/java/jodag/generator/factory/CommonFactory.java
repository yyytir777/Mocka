package jodag.generator.factory;

import jodag.generator.common.*;

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

//    default PhoneNumberGenerator asPhoneNumber() {
//        return PhoneNumberGenerator.getInstance();
//    }
//
//    default IpAddressGenerator asIpAddress() {
//        return IpAddressGenerator.getInstance();
//    }

    default DateGenerator asDate() {
        return DateGenerator.getInstance();
    }
}
