package jodag.generator.common;

import jodag.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.Test;

class NetworkAddressGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    void test() {
        NetworkAddressGenerator networkAddressGenerator = generatorFactory.asNetworkAddress();
        String iPv4 = networkAddressGenerator.getIPv4();
        System.out.println("iPv4 = " + iPv4);

        String iPv6 = networkAddressGenerator.getIPv6();
        System.out.println("iPv6 = " + iPv6);

        String macAddress = networkAddressGenerator.getMacAddress();
        System.out.println("MAC = " + macAddress);

        String host = networkAddressGenerator.getHost();
        System.out.println("host = " + host);

        String domain = networkAddressGenerator.getDomain();
        System.out.println("domain = " + domain);
    }
}