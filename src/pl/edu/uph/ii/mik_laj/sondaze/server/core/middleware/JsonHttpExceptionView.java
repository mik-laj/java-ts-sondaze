package pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonWriter;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.StatusCode;

/**
 * Widok JSON
 * @author andrzej
 *
 */
public class JsonHttpExceptionView implements HttpExceptionViewInterface {

	public JsonHttpExceptionView() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware.
	 * HttpExceptionViewInterface#getContent(pl.edu.uph.ii.mik_laj.sondaze.
	 * server.http.RequestInterface, java.lang.Exception)
	 */
	@Override
	public String getContent(RequestInterface request, Exception ex) {
		JsonObject json = new JsonObject();
		json.addElement("title", new JsonString(generateTitle(ex)));
		json.addElement("request", getRequestInfo(request));
		json.addElement("stack_trace", getStackTrace(ex));

		try {
			return JsonWriter.map(json);
		} catch (Exception exception) {
			return "{}";
		}

	}

	String generateTitle(Exception ex) {
		if (ex instanceof HttpException) {
			HttpException httpEx = (HttpException) ex;
			return "HTTP Exception: " + //
					httpEx.getStatusCode() + //
					" " + //
					StatusCode.getReasonPhrease(httpEx.getStatusCode());
		} else {
			return ex.getClass().getName() + ": " + ex.getMessage();
		}
	}

	private JsonObject getRequestInfo(RequestInterface request) {
		JsonObject json = new JsonObject();
		json.addElement("path", new JsonString(request.getMethod()));
		json.addElement("method", new JsonString(request.getPath()));

		List<JsonObject> headers = request.getHeaders().entrySet()//
				.stream()//
				.map(t -> {
					JsonObject r = new JsonObject();
					r.addElement("key", new JsonString(t.getKey()));
					r.addElement("value", new JsonString(t.getValue()));
					return r;
				}).collect(Collectors.toList());
		json.addElement("headers", new JsonArray(headers));
		return json;
	}

	private JsonArray getStackTrace(Throwable throwable) {
		List<JsonString> list = Arrays.stream(throwable.getStackTrace())//
				.map(Object::toString)//
				.map(JsonString::new)//
				.collect(Collectors.toList());
		return new JsonArray(list);
	}

	@Override
	public String getContentType() {
		return "application/json";
	}
}
