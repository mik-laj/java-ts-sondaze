package pl.edu.uph.ii.mik_laj.sondaze.server.core.middleware;

import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;

/**
 * Interfejs widokow klasy obsluguje warstawe posrednizca
 * @author andrzej
 *
 */
public interface HttpExceptionViewInterface {

	String getContent(RequestInterface request, Exception ex);

	String getContentType();
}