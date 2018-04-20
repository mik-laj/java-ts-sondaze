package pl.edu.uph.ii.mik_laj.sondaze.server.core;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware.ActionsMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware.HttpExceptionMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.Response;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Klasa bazowa dla aplikacji serwoerych
 * @author andrzej
 *
 */
public abstract class App implements AppInterface {

	private ActionsMiddleware actions = new ActionsMiddleware();
	private MiddlewareProccesor middleware = new MiddlewareProccesor();

	public App() {
		middleware.setDefaultMiddleware(actions);
		this.addMiddleware(new HttpExceptionMiddleware());
	}

	public void addAction(ActionInterface action) {
		actions.add(action);
	}

	public void addMiddleware(MiddlewareInterface action) {
		middleware.add(action);
	}

	@Override
	public ResponseInterface proccessRequest(RequestInterface request) {
		Response response = new Response();
		try {
			middleware.procces(request, response);
		} catch (Exception ex) {
			response.withStatus(501).setBody("ERROR");
		}
		return response;
	}
}
