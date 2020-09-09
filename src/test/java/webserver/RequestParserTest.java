package webserver;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestParserTest {
    private static final String METHOD = "GET";
    private static final String REQUEST_URI = "/index.html";
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String SP = " ";
    private static final String REQUEST = METHOD + SP + REQUEST_URI + SP + HTTP_VERSION;

    private RequestParser requestParser;

    @BeforeEach
    void setUp() {
        requestParser = new RequestParser(REQUEST);
    }

    @Test
    void getURI() {
        assertThat(requestParser.getURI()).isEqualTo(REQUEST_URI);
    }
}