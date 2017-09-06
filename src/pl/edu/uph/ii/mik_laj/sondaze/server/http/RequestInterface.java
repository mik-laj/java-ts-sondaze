package pl.edu.uph.ii.mik_laj.sondaze.server.http;

/**
 * Interfejs dla zapytan do serwera
 * @author andrzej
 *
 */
public interface RequestInterface extends MessageInterface {
	public String getRequestTarget();

	public RequestInterface setRequestTarget(String requestTarget);

	public String getMethod();

	public RequestInterface setMethod(String method);

	public String getPath();

	public RequestInterface setPath(String path);
}
