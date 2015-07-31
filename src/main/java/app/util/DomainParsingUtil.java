package app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainParsingUtil {

    private static Pattern SUB_DOMAIN_PATTERN = Pattern.compile("^.*://([^/]*).*$");

    public static String parseDomain(String url) {
        Matcher matcher = SUB_DOMAIN_PATTERN.matcher(url);
        if (matcher.matches()) {
            String subDomain = matcher.group(1);
            String[] split = subDomain.split("\\.");

            if (split.length > 1) {
                return split[split.length - 2] + "." + split[split.length - 1];
            }
        }

        return null;
    }

}
