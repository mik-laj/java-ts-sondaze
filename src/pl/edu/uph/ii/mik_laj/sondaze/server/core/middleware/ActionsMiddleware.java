package pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.ActionInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareProccesor.NextMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Funkcje posredniczaca, ktora jest przewaznie konfigurowana jako domyyslna akcja. Wyszukuje 
 * zarejestrowane akcje i ije uruchamia,
 * @author andrzej
 *
 */
public class ActionsMiddleware implements MiddlewareInterface {

	private List<ActionInterface> actions = new ArrayList<>();

	public int size() {
		return actions.size();
	}

	public boolean add(ActionInterface e) {
		return actions.add(e);
	}

	public void add(int index, ActionInterface element) {
		actions.add(index, element);
	}

	@Override
	public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
			NextMiddleware next) throws Exception {
		Optional<ActionInterface> found_action = actions.stream().filter(f -> f.support(request)).findFirst();

		if (!found_action.isPresent()) {
			throw new HttpException(404);
		}
		return found_action.get().getResponse(request, response);

	}

}
