package pl.edu.uph.ii.mik_laj.sondaze.client.api;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonReader;
import pl.edu.uph.ii.mik_laj.sondaze.json.JsonWriter;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;

public class ApiClient {

	public static ApiClient factoryDefault() {
		return new ApiClient("http://localhost:8000/api/v1/");
	}

	/**
	 * Content-Type header
	 */
	protected static final String HEADER_CONTENT_TYPE = "Content-Type";

	/**
	 * Accept header
	 */
	protected static final String HEADER_ACCEPT = "Accept";
	/**
	 * Authorization header
	 */
	protected static final String HEADER_AUTHORIZATION = "Authorization";

	/**
	 * User-Agent header
	 */
	protected static final String HEADER_USER_AGENT = "User-Agent";

	/**
	 * METHOD_GET
	 */
	protected static final String METHOD_GET = "GET";

	/**
	 * METHOD_PUT
	 */
	protected static final String METHOD_PUT = "PUT";

	/**
	 * METHOD_POST
	 */
	protected static final String METHOD_POST = "POST";

	/**
	 * METHOD_DELETE
	 */
	protected static final String METHOD_DELETE = "DELETE";

	/**
	 * METHOD_PATCH
	 */
	protected static final String METHOD_PATCH = "PATCH";

	/**
	 * Default user agent request header value
	 */
	protected static final String USER_AGENT = "Uph/0.0.1";

	/**
	 * Json format mime-type
	 */
	protected static final String CONTENT_TYPE_JSON = "application/json";

	private String credentials;

	private String baseUri;

	public ApiClient(String baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * Konfiguruje zapytania z domyślnymi nagłówkami
	 * @param request
	 * @return configured request
	 */
	protected HttpURLConnection configureRequest(final HttpURLConnection request) {
		if (credentials != null)
			request.setRequestProperty(HEADER_AUTHORIZATION, credentials);
		request.setRequestProperty(HEADER_USER_AGENT, USER_AGENT);
		request.setRequestProperty(HEADER_ACCEPT, CONTENT_TYPE_JSON);
		return request;
	}

	/**
	 * Tworzy połączenie do URI
	 *
	 * @param uri
	 * @param method
	 * @return connection
	 * @throws IOException
	 */
	protected HttpURLConnection createConnection(String uri, String method) throws IOException {
		URL url = new URL(createUri(uri));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		if (method.equals(METHOD_PATCH)) {
			connection.setRequestMethod(METHOD_POST);
			connection.setRequestProperty("X-HTTP-Method-Override", METHOD_PATCH);
		} else {
			connection.setRequestMethod(method);
		}
		return configureRequest(connection);
	}

	/**
	 * Ustawia dane wyjściowe
	 *
	 * @param request
	 * @param params
	 * @throws IOException
	 */
	protected void sendOutputParams(HttpURLConnection request, JsonElement params) throws IOException {
		request.setDoOutput(true);
		if (params != null) {
			request.setRequestProperty(HEADER_CONTENT_TYPE, CONTENT_TYPE_JSON);
			byte[] data = JsonWriter.map(params).getBytes();
			request.setFixedLengthStreamingMode(data.length);
			BufferedOutputStream output = new BufferedOutputStream(request.getOutputStream(), 4096);
			try {
				output.write(data);
				output.flush();
			} finally {
				try {
					output.close();
				} catch (IOException ignored) {
					// Ignored
				}
			}
		} else {
			request.setFixedLengthStreamingMode(0);
			request.setRequestProperty("Content-Length", "0");
		}
	}

	/**
	 * Tworzy pełny adres Uri tj. z baseUri oraz dołaczona ścieżka.
	 *
	 * @param path
	 * @return uri
	 */
	protected String createUri(final String path) {
		return baseUri + path;
	}

	/**
	 * Pobiera strumień z zapytania
	 *
	 * @param request
	 * @return stream
	 * @throws IOException
	 */
	protected InputStream getStream(HttpURLConnection request) throws IOException {
		if (request.getResponseCode() < HttpURLConnection.HTTP_BAD_REQUEST)
			return request.getInputStream();
		else {
			InputStream stream = request.getErrorStream();
			return stream != null ? stream : request.getInputStream();
		}
	}

	/**
	 * Wysyła zapytanie GET i przetwarza odpowiedz JSON
	 *
	 * @param request
	 * @return response
	 * @throws IOException
	 */
	public JsonElement get(String Uri) throws IOException {
		HttpURLConnection httpRequest = createConnection(Uri, METHOD_GET);
		return parseBody(httpRequest);
	}

	/**
	 * Przetwarza odpowiedz z serwera
	 * @param httpRequest
	 * @return
	 * @throws IOException
	 */
	private JsonElement parseBody(HttpURLConnection httpRequest) throws IOException {
		int code = httpRequest.getResponseCode();
		if (code == HttpURLConnection.HTTP_NO_CONTENT) {
			return null;
		}
		if (code >= HttpURLConnection.HTTP_BAD_REQUEST) {
			throw new IOException();
		}
		return JsonReader.map(getStream(httpRequest));
	}

	/**
	 * Wysyła zapytanie GET 
	 *
	 * @param request
	 * @return response
	 * @throws IOException
	 */
	public JsonElement post(String Uri, JsonElement el) throws IOException {
		HttpURLConnection httpRequest = createConnection(Uri, METHOD_GET);
		if (el != null) {
			sendOutputParams(httpRequest, el);
		}
		return parseBody(httpRequest);
	}

	/**
	 * Wysyła zapytanie DELETE
	 *
	 * @param request
	 * @return response
	 * @throws IOException
	 */
	public JsonElement delete(String Uri) throws IOException {
		HttpURLConnection httpRequest = createConnection(Uri, METHOD_DELETE);
		return parseBody(httpRequest);
	}

	/**
	 * Wysyła zapytanie PATCH
	 *
	 * @param request
	 * @return response
	 * @throws IOException
	 */
	public JsonElement patch(String Uri, JsonElement el) throws IOException {
		HttpURLConnection httpRequest = createConnection(Uri, METHOD_PATCH);
		sendOutputParams(httpRequest, el);
		return parseBody(httpRequest);
	}

	/**
	 * Ustawia dane autoryzacyjne. 
	 *
	 * @param user
	 * @param password
	 * @return this client
	 */
	public ApiClient setCredentials(final String user, final String password) {
		if (user != null && user.length() > 0 && password != null && password.length() > 0) {
			String token = Base64.getEncoder().encodeToString((user + ':' + password).getBytes());
			credentials = "Basic " + token;
		} else
			credentials = null;
		return this;
	}
}
