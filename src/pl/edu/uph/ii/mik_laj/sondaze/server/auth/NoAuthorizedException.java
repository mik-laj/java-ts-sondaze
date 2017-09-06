package pl.edu.uph.ii.mik_laj.sondaze.server.auth;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;

/**
 * Blad niepowodzenia autoryzacji lub braku autoryzacji
 * @author andrzej
 *
 */
public class NoAuthorizedException extends HttpException {

	public NoAuthorizedException() {
		super(401);
	}

}
