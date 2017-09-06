package pl.edu.uph.ii.mik_laj.sondaze.server.auth;

import java.util.Base64;
import java.util.Optional;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.MiddlewareProccesor.NextMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.UserDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

/**
 * Warstwa psredniczaca opdoiweidzlana za mechanizm autoryzacji
 * @author andrzej
 *
 */
public class BasicAuthorizationMiddleware implements MiddlewareInterface {

	private CurrentUserStorage storage;
	private UserDao userDao;

	public BasicAuthorizationMiddleware(CurrentUserStorage storage, UserDao userDao) {
		this.storage = storage;
		this.userDao = userDao;
	}

	private class Credentials {
		String userId;
		String password;

		@Override
		public String toString() {
			return "Credentials [userId=" + userId + ", password=" + password + "]";
		}

	}

	@Override
	public ResponseInterface proccessMiddleware(RequestInterface request, ResponseInterface response,
			NextMiddleware next) throws Exception {
		if (request.hasHeader("Authorization")) {
			Optional<Credentials> credentials_op = parseHeaders(request);
			if (!credentials_op.isPresent()) {
				askForPassword(response);
			}
			Credentials credentails = credentials_op.get();
			Optional<User> user_op = userDao.getAll().stream()//
					.filter(t -> t.getLogin().equals(credentails.userId)) //
					.filter(t -> t.getPassword().equals(credentails.password))//
					.findAny();
			System.out.println(credentails);
			if (!user_op.isPresent()) {
				askForPassword(response);
			}
			
			storage.setCurrentUser(user_op.get());

		} else {
			storage.setCurrentUser(null);
		}
		try {
			return next.procces(request, response);
		} catch (NoAuthorizedException ex) {
			askForPassword(response);
			return response;
		}
	}

	public static void askForPassword(ResponseInterface response) {
		response.addHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
		throw new NoAuthorizedException();
	}

	public Optional<Credentials> parseHeaders(RequestInterface request) {
		String[] auth_data_parts = request.getHeader("Authorization").split(" ", 2);
		String[] auth_data_raw;
		try {
			auth_data_raw = new String(Base64.getDecoder().decode(auth_data_parts[1])).split(":");
		} catch (IllegalArgumentException ex) {
			return Optional.empty();
		}
		if (auth_data_raw.length != 2) {
			return Optional.empty();
		}
		Credentials c = new Credentials();
		c.userId = auth_data_raw[0];
		c.password = auth_data_raw[1];
		return Optional.of(c);
	}

}
