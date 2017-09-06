package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa czytnika umozliwiajaca odczytanie zapytania HTTP z strumienia
 * @author andrzej
 *
 */
public class RequestReader extends BufferedReader {

	public static final Pattern REQUEST_LINE = Pattern
			.compile("(?<method>[A-Z]*) (?<path>[A-Za-z:0-9_\\+~%\\-\\/;\\.]*) HTTP/(?<version>1.(?:1|0))");
	private static final Pattern GENERAL_HEADER = Pattern.compile("^(?<key>[a-zA-Z\\-]+)\\s*:\\s*(?<value>.+)$");
	private Map<String, String> headers;
	private String method;
	private String path;
	private String protocol_version;
	private String body;

	public RequestReader(InputStream stream) {
		super(new InputStreamReader(stream));
	}

	public Request readRequest() throws IOException {
		readRequestLine();
		readHeaders();
		readBody();
		overrideMethodViaHeader();
		System.out.println("--------");
		return new Request(method, path, protocol_version, headers, body);
	}

	private void overrideMethodViaHeader() {
		if (headers.containsKey("X-HTTP-Method-Override")) {
			method = headers.get("X-HTTP-Method-Override");
		}
	}

	private void readRequestLine() throws IOException {
		String line = readLine();
		Matcher request_line_matcher = REQUEST_LINE.matcher(line);
		System.out.println(line);
		if (!request_line_matcher.matches()) {
			throw new IOException("Malformed request line: " + line);
		}
		method = request_line_matcher.group("method");
		path = request_line_matcher.group("path");
		protocol_version = request_line_matcher.group("path");
	}

	private void readHeaders() throws IOException {
		headers = new HashMap<>();
		String line = this.readLine();
		while (line != null && !"".equals(line)) {
			Matcher header_line_matcher = GENERAL_HEADER.matcher(line);
			System.out.println(line);
			if (!header_line_matcher.matches()) {
				throw new IOException("Malformed header line");
			}
			String key = header_line_matcher.group("key");
			String value = header_line_matcher.group("value");

			headers.put(key, value);

			// Continue parse
			line = this.readLine();
		}
	}

	private void readBody() throws IOException {
		body = null;
		if (headers.containsKey("Content-Length")) {
			int count_bytes_to_read = Integer.parseInt(headers.get("Content-Length"));
			StringBuilder sb = new StringBuilder(count_bytes_to_read);
			while (count_bytes_to_read != 0) {
				char[] chunk = new char[1000];
				int chunk_size = Math.min(1000, count_bytes_to_read % 1000);
				int readLen = read(chunk, 0, chunk_size);
				if (chunk_size != readLen) {
					throw new IOException("Unexpected end of request");
				}
				count_bytes_to_read -= chunk_size;
				sb.append(chunk, 0, chunk_size);
			}
			body = sb.toString();
			System.out.println("body:" + body);
		}
	}

}
