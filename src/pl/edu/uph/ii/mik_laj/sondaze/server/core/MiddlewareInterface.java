package pl.edu.uph.ii.mik_laj.sondaze.server.core;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareProccesor.NextMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Interfejs kodu pośredniczacego
 * 
 * @author andrzej
 *
 */
public interface MiddlewareInterface {

	public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
			NextMiddleware next) throws Exception;
}
