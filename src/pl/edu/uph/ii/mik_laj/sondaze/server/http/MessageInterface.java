package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.util.Map;

/**
 * Intefejs dla odpowiedzi i zapytań do serwera HTTP
 * @author andrzej
 *
 */
public interface MessageInterface {
    public String getProtocolVersion();
    public MessageInterface setProtocolVersion(String version);
    public Map<String, String> getHeaders();
    public boolean hasHeader(String name);
    public MessageInterface addHeader(String key, String value);
    public String getHeader(String name);
    // public String getHeaderLine(String name);
    // public static withoutHeader(String name);
    public String getBody();
    public MessageInterface setBody(String body);
}
