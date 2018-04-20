package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Odpowiada za zapisania odpowiedzi HTTP.
 * @author andrzej
 *
 */
public class ResponsetWriter extends BufferedWriter {

	public ResponsetWriter(Writer out) {
		super(out);
	}

	public ResponsetWriter(Writer out, int sz) {
		super(out, sz);
	}

	public void writeResponse(ResponseInterface response) throws IOException {
		if (!response.hasHeader("Content-Length") && response.getBody() != null) {
			response.addHeader("Content-Length", "" + response.getBody().length());
		}

		writeStatusLine(response);
		writeHeaders(response);
		this.newLine();
		writeBody(response);
		this.flush();
	}

	private void writeStatusLine(ResponseInterface response) throws IOException {
		String version = response.getProtocolVersion();
		int code = response.getStatusCode();
		String phrase = response.getReasonPhrase();
		this.write(String.format("HTTP/%s %s %s", version, code, phrase));
		this.newLine();
	}

	private void writeHeaders(ResponseInterface response) throws IOException {
		Map<String, String> headers = response.getHeaders();
		if (headers.size() > 0) {
			for (Entry<String, String> header : headers.entrySet()) {
				this.write(header.getKey());
				this.write(": ");
				this.write(header.getValue());
				this.newLine();
			}
		}
	}

	private void writeBody(ResponseInterface response) throws IOException {
		String body = response.getBody();
		if (body != null) {
			this.write(body);
		}
	}
}
