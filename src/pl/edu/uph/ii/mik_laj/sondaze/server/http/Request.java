package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.util.Map;

/**
 * Odpowiedz z serwera
 * @author andrzej
 *
 */
public class Request extends Message implements RequestInterface {

	private String requestTarget = "0.0.0.0";
	private String method;
	private String path;

	public Request(String method, String path, String protocol, Map<String, String> headers, String body) {
		super(protocol, headers, body);
		this.method = method;
		this.path = path;
	}

	@Override
	public String getRequestTarget() {
		return this.requestTarget;
	}

	@Override
	public RequestInterface setRequestTarget(String requestTarget) {
		this.requestTarget = requestTarget;
		return this;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public RequestInterface setMethod(String method) {
		this.method = method;
		return this;
	}

	@Override
	public String getPath() {
		return this.path;
	}

	@Override
	public RequestInterface setPath(String path) {
		this.path = path;
		return this;
	}

	@Override
	public String toString() {
		return "Request [requestTarget=" + requestTarget + ", method=" + method + ", path=" + path
				+ ", protocolVersion=" + protocolVersion + ", headers=" + headers + ", body=" + body + "]";
	}

}
