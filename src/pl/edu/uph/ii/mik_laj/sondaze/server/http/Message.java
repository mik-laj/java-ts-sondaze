package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa bazowa dla odpowiedzi i zapytań do serwera HTTP
 * @author andrzej
 *
 */
public abstract class Message implements MessageInterface {

	protected String protocolVersion = "1.1";
	protected HashMap<String, String> headers = new HashMap<>();
	protected String body;
	
	
	public Message() {
		super();
	}

	public Message(String protocol, Map<String, String> headers, String body) {
		super();
		this.protocolVersion = protocol;
		this.headers.putAll(headers);
		this.body = body;
	}

	@Override
	public String getProtocolVersion() {
		return this.protocolVersion;
	}

	@Override
	public MessageInterface setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
		return this;
	}

	@Override
	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(this.headers);
	}

	@Override
	public boolean hasHeader(String name) {
		return headers.containsKey(name);
	}

	@Override
	public MessageInterface addHeader(String key, String value) {
		this.headers.put(key, value);
		return this;
	}
	
	@Override
	public String getHeader(String name) {
		return this.headers.get(name);
	}

	@Override
	public String getBody() {
		return body;
	}

	@Override
	public MessageInterface setBody(String body) {
		this.body = body;
		return this;
	}



}
