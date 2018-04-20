package pl.edu.uph.ii.mik_laj.sondaze.server.core;

import java.io.IOException;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonReader;
import pl.edu.uph.ii.mik_laj.sondaze.json.JsonWriter;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Klasa bazowa dla akcji, które wykorzysutja JSON. Zawiera usprawnienia ulatwiajace wyslanie odpowiedzi w tym formacie
 * 
 * @author andrzej
 *
 */
public abstract class BaseJsonAction implements ActionInterface {

	@Override
	public ResponseInterface getResponse(RequestInterface request, ResponseInterface response) throws IOException {
		String json = JsonWriter.map(getJson(request, response));
		return response.addHeader("Content-Type", "application/json")//
				.withStatus(200)
				.setBody(json);
	}

	public JsonElement parseBody(RequestInterface request) throws IOException {
		String body = request.getBody();
		if (body == null) {
			throw new IOException("Body is required");
		}
		return JsonReader.map(body);
	}

	public abstract Object getJson(RequestInterface request, ResponseInterface response) throws IOException;

}
