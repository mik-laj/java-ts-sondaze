package pl.edu.uph.ii.mik_laj.sondaze.server.actions;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Główny punkt dostępowy
 * 
 * @author andrzej
 *
 */
public class RootEndpointAction extends BaseJsonAction {

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("GET") && request.getPath().matches("^/api/v1/$");
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) {
		JsonObject json = new JsonObject();
		json.addElement("poll", new JsonString("/api/v1/poll/"));
		json.addElement("user", new JsonString("/api/v1/user/"));
		return json;
	}

}
