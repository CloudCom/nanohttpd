package fi.iki.elonen;

import java.io.ByteArrayOutputStream;

import org.junit.Test;

import static junit.framework.Assert.*;

public class HttpProxyGetRequest extends HttpServerTest {

	@Test
    public void testFullyQualifiedWorkingGetRequest() throws Exception {
        ByteArrayOutputStream outputStream = invokeServer("PROXY TCP4 10.164.62.60 52.12.123.60 22231 80\r\nGET " + URI + " HTTP/1.1");

        String[] expected = {
                "HTTP/1.1 200 OK",
                "Content-Type: text/html",
                "Date: .*",
                "Connection: keep-alive",
                "Content-Length: 0",
                ""
        };

        assertResponse(outputStream, expected);
        assertEquals("TCP4", testServer.header.get("x-proxy-protocol"));
        assertEquals("10.164.62.60", testServer.header.get("x-proxy-client-ip"));
        assertEquals("52.12.123.60", testServer.header.get("x-proxy-proxy-ip"));
        assertEquals("22231", testServer.header.get("x-proxy-client-port"));
        assertEquals("80", testServer.header.get("x-proxy-proxy-port"));
    }
}
