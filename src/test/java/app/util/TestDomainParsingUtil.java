package app.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TestDomainParsingUtil {

    @Test
    public void testParseDomain() {
        assertThat(DomainParsingUtil.parseDomain("http://www.google.se/hehehe"), is("google.se"));
        assertThat(DomainParsingUtil.parseDomain("http:/www.google.se/hehehe"), nullValue());
        assertThat(DomainParsingUtil.parseDomain("http://google.se/hehehe"), is("google.se"));
        assertThat(DomainParsingUtil.parseDomain("http://se/hehehe"), nullValue());
        assertThat(DomainParsingUtil.parseDomain("http://images.maps.google.se"), is("google.se"));
    }
}
