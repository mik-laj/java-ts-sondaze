package pl.edu.uph.ii.mik_laj.sondaze.server.core;

import java.io.IOException;

import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Interfejs akcji
 * @author andrzej
 *
 */
public interface ActionInterface {

	/**
	 * Sprawdza, czy zapytanie moze byc obsluzowane przez ta akcje
	 * @param request
	 * @return
	 */
	public boolean support(RequestInterface request);

	/**
	 * Pobiera odpowieidz od akcji
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ResponseInterface getResponse(RequestInterface request, ResponseInterface response) throws IOException;

}
