package mocka.core.generator.common;

import mocka.core.generator.AbstractGenerator;
import mocka.core.generator.regex.RegexGenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NetworkAddressGenerator extends AbstractGenerator<String> {

    private static final NetworkAddressGenerator instance = new NetworkAddressGenerator();
    private final RegexGenerator regexGenerator = RegexGenerator.getInstance();
    private static final List<String> TLDS = List.of(
            "com", "net", "org", "info", "biz", "xyz", "name", "pro", "io", "dev",
            "kr", "jp", "cn", "us", "uk", "de", "fr", "in", "ca", "au", "co.kr", "or.kr", "ac.kr",
            "co.uk", "org.uk", "com.au", "net.au", "app", "tech", "site", "online", "shop", "store",
            "blog", "design", "cloud", "ai");

    private NetworkAddressGenerator() {
        super("network_address", String.class);
    }

    public static NetworkAddressGenerator getInstance() {
        return instance;
    }

    @Override
    public String get() {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    public String getIPv4() {
        return IntStream.range(0, 4)
                .mapToObj(i -> String.valueOf(randomProvider.getNextIdx(256)))
                .collect(Collectors.joining("."));
    }

    public String getIPv6() {
        return IntStream.range(0, 8)
                .mapToObj(i -> String.format("%04x", randomProvider.getNextIdx(0x10000)))
                .collect(Collectors.joining(":"));
    }

    public String getMacAddress() {
        return IntStream.range(0, 6)
                .mapToObj(i -> String.format("%02X", randomProvider.getNextIdx(256)))
                .collect(Collectors.joining(":"));
    }

    public String getHost() {
        return regexGenerator.get("[a-zA-Z][a-z0-9]{3,10}");
    }

    public String getDomain() {
        return getHost() + "." + TLDS.get(randomProvider.getNextIdx(TLDS.size()));
    }
}
