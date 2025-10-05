package entityinstantiator.generator.common;

import entityinstantiator.generator.factory.GeneratorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@DisplayName("NetworkAddressGenerator Test Code")
class NetworkAddressGeneratorTest {

    private final GeneratorFactory generatorFactory = new GeneratorFactory();

    @Test
    @DisplayName("Verify the NetworkAddressGenerator retrieved through GeneratorFactory is the correct class")
    void get_instance() {
        NetworkAddressGenerator networkAddressGenerator = generatorFactory.asNetworkAddress();
        assertThat(networkAddressGenerator).isInstanceOf(NetworkAddressGenerator.class);
    }

    @Test
    @DisplayName("NetworkAddressGenerator is managed as a singleton")
    void networkAddressGenerator_is_singleton() {
        NetworkAddressGenerator networkAddressGenerator1 = generatorFactory.asNetworkAddress();
        NetworkAddressGenerator networkAddressGenerator2 = generatorFactory.asNetworkAddress();

        assertThat(networkAddressGenerator1).isNotNull();
        assertThat(networkAddressGenerator2).isNotNull();
        assertThat(networkAddressGenerator1).isSameAs(networkAddressGenerator2);
    }


    @Test
    @DisplayName("NetworkAddressGenerator get() throws UnsupportedException")
    void get_value_from_networkAddressGenerator() {
        NetworkAddressGenerator networkAddressGenerator = generatorFactory.asNetworkAddress();
        assertThatThrownBy(networkAddressGenerator::get).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("NetworkAddressGenerator returns for")
    void test() {
        NetworkAddressGenerator networkAddressGenerator = generatorFactory.asNetworkAddress();
        String iPv4 = networkAddressGenerator.getIPv4();
        System.out.println("iPv4 = " + iPv4);
        assertThat(iPv4).matches("(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}");

        String iPv6 = networkAddressGenerator.getIPv6();
        System.out.println("iPv6 = " + iPv6);
        assertThat(iPv6).matches("(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:))");

        String macAddress = networkAddressGenerator.getMacAddress();
        System.out.println("MAC = " + macAddress);
        assertThat(macAddress).matches("([0-9A-Fa-f]{2}([-:])){5}([0-9A-Fa-f]{2})");

        String host = networkAddressGenerator.getHost();
        System.out.println("host = " + host);

        String domain = networkAddressGenerator.getDomain();
        System.out.println("domain = " + domain);
    }
}