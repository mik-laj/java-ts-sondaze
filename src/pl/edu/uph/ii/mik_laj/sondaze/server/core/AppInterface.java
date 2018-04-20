package pl.edu.uph.ii.mik_laj.sondaze.server.core;

import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;

/**
 * Interfejs aplikacji. Wykorzsytywany do połaczenia serwera HTTP z konkrenta aplikacja
 * @author andrzej
 *
 */
public interface AppInterface {
	public ResponseInterface proccessRequest(RequestInterface request);
}
