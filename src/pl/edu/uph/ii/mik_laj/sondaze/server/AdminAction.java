package pl.edu.uph.ii.mik_laj.sondaze.server;

import java.io.IOException;

import pl.edu.uph.ii.mik_laj.sondaze.server.auth.BasicAuthorizationMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.auth.CurrentUserStorage;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.ActionInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

/**
 * Chroni akcje przed uruchomieniem przez uzytkownika bez upsrawnien administratora
 * @author andrzej
 *
 */
public class AdminAction implements ActionInterface {

	private ActionInterface action;
	private CurrentUserStorage current_user_storage;

	public AdminAction(ActionInterface action, CurrentUserStorage current_user_storage) {
		this.action = action;
		this.current_user_storage = current_user_storage;
	}

	@Override
	public boolean support(RequestInterface request) {
		return action.support(request);
	}

	@Override
	public ResponseInterface getResponse(RequestInterface request, ResponseInterface response) throws IOException {
		User user = current_user_storage.getCurrentUser();

		if (user == null) {
			BasicAuthorizationMiddleware.askForPassword(response);
			return null;
		}

		if (!user.isAdmin()) {
			throw new HttpException(401);
		}

		return action.getResponse(request, response);
	}

}
