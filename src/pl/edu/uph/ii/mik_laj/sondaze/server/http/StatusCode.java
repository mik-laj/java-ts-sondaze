package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Klasa narzedziowa do zarzadaniza kodami odpowiedzi
 * 
 * @see https://www.iana.org/assignments/http-status-codes/http-status-codes.
 *      xhtml
 * @author andrzej
 * 
 */
public class StatusCode {

	public static final Map<Integer, String> STATUS_CODES;

	static {
		HashMap<Integer, String> status_to_phrase = new HashMap<>();
		status_to_phrase.put(100, "Continue");
		status_to_phrase.put(101, "Switching Protocols");
		status_to_phrase.put(102, "Processing");
		status_to_phrase.put(200, "OK");
		status_to_phrase.put(201, "Created");
		status_to_phrase.put(202, "Accepted");
		status_to_phrase.put(203, "Non-Authoritative Information");
		status_to_phrase.put(204, "No Content");
		status_to_phrase.put(205, "Reset Content");
		status_to_phrase.put(206, "Partial Content");
		status_to_phrase.put(207, "Multi-Status");
		status_to_phrase.put(208, "Already Reported");
		status_to_phrase.put(226, "IM Used");
		status_to_phrase.put(300, "Multiple Choices");
		status_to_phrase.put(301, "Moved Permanently");
		status_to_phrase.put(302, "Found");
		status_to_phrase.put(303, "See Other");
		status_to_phrase.put(304, "Not Modified");
		status_to_phrase.put(305, "Use Proxy");
		status_to_phrase.put(307, "Temporary Redirect");
		status_to_phrase.put(308, "Permanent Redirect");
		status_to_phrase.put(400, "Bad Request");
		status_to_phrase.put(401, "Unauthorized");
		status_to_phrase.put(402, "Payment Required");
		status_to_phrase.put(403, "Forbidden");
		status_to_phrase.put(404, "Not Found");
		status_to_phrase.put(405, "Method Not Allowed");
		status_to_phrase.put(406, "Not Acceptable");
		status_to_phrase.put(407, "Proxy Authentication Required");
		status_to_phrase.put(408, "Request Timeout");
		status_to_phrase.put(409, "Conflict");
		status_to_phrase.put(410, "Gone");
		status_to_phrase.put(411, "Length Required");
		status_to_phrase.put(412, "Precondition Failed");
		status_to_phrase.put(413, "Payload Too Large");
		status_to_phrase.put(414, "URI Too Long");
		status_to_phrase.put(415, "Unsupported Media Type");
		status_to_phrase.put(416, "Range Not Satisfiable");
		status_to_phrase.put(417, "Expectation Failed");
		status_to_phrase.put(421, "Misdirected Request");
		status_to_phrase.put(422, "Unprocessable Entity");
		status_to_phrase.put(423, "Locked");
		status_to_phrase.put(424, "Failed Dependency");
		status_to_phrase.put(425, "Unassigned");
		status_to_phrase.put(426, "Upgrade Required");
		status_to_phrase.put(427, "Unassigned");
		status_to_phrase.put(428, "Precondition Required");
		status_to_phrase.put(429, "Too Many Requests");
		status_to_phrase.put(430, "Unassigned");
		status_to_phrase.put(431, "Request Header Fields Too Large");
		status_to_phrase.put(451, "Unavailable For Legal Reasons");
		status_to_phrase.put(500, "Internal Server Error");
		status_to_phrase.put(501, "Not Implemented");
		status_to_phrase.put(502, "Bad Gateway");
		status_to_phrase.put(503, "Service Unavailable");
		status_to_phrase.put(504, "Gateway Timeout");
		status_to_phrase.put(505, "HTTP Version Not Supported");
		status_to_phrase.put(506, "Variant Also Negotiates");
		status_to_phrase.put(507, "Insufficient Storage");
		status_to_phrase.put(508, "Loop Detected");
		status_to_phrase.put(509, "Unassigned");
		status_to_phrase.put(510, "Not Extended");
		status_to_phrase.put(511, "Network Authentication Required");
		STATUS_CODES = Collections.unmodifiableMap(status_to_phrase);

	}

	private StatusCode() {

	}

	public static String getReasonPhrease(int code) {
		String r = STATUS_CODES.get(code);
		if (r == null) {
			return "Internal server error";
		}
		return r;
	}

}
