package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.util.Map;

/**
 * Klasa odpowiedzi z serwera
 * @author andrzej
 *
 */
public class Response extends Message implements ResponseInterface {

	private int code = 501;
	private String phrase;

	public Response() {
		super();
	}

	public Response(int code) {
		super();
		this.code = code;
	}

	public Response(String protocol, Map<String, String> headers, String body) {
		super(protocol, headers, body);
	}

	@Override
	public int getStatusCode() {
		return code;
	}

	@Override
	public ResponseInterface withStatus(int code) {
		this.code = code;
		return this;
	}

	@Override
	public ResponseInterface withStatus(int code, String reasonPhrase) {
		this.code = code;
		this.phrase = reasonPhrase;
		return this;
	}

	@Override
	public String getReasonPhrase() {
		if (phrase == null) {
			return StatusCode.getReasonPhrease(code);
		}
		return phrase;
	}

	@Override
	public String getProtocolVersion() {
		return super.getProtocolVersion();
	}

	@Override
	public ResponseInterface setProtocolVersion(String protocolVersion) {
		return (ResponseInterface) super.setProtocolVersion(protocolVersion);
	}

	@Override
	public ResponseInterface addHeader(String key, String value) {
		// TODO Auto-generated method stub
		return (ResponseInterface) super.addHeader(key, value);
	}

	@Override
	public ResponseInterface setBody(String body) {
		return (ResponseInterface) super.setBody(body);
	}

}
