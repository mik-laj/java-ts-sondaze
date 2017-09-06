package pl.edu.uph.ii.mik_laj.sondaze.server.http;

/**
 * Interfejs odpowiedzi z serwera.
 * @author andrzej
 *
 */
public interface ResponseInterface extends MessageInterface {
	public int getStatusCode();

	public String getReasonPhrase();

	public ResponseInterface withStatus(int code);

	public ResponseInterface withStatus(int code, String reasonPhrase);

	public ResponseInterface setProtocolVersion(String version);

	public ResponseInterface addHeader(String key, String value);

	public ResponseInterface setBody(String body);
}
