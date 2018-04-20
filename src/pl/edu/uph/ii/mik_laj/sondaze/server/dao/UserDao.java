package pl.edu.uph.ii.mik_laj.sondaze.server.dao;

import java.io.IOException;
import java.util.function.Function;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonParserException;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.UserMapper;

/**
 * Klasa dostepowa do obiektu z uzytkownikami. Wykorzystuuje pliki w formacie JSON
 * @author andrzej
 *
 */
public class UserDao extends BaseDao<User> {

	public UserDao(String filename) throws IOException, JsonParserException {
		super(filename);
	}

	protected Function<User, ? extends JsonElement> getFromMapper() {
		return UserMapper::from;
	}

	protected Function<? super JsonElement, User> getToMapper() {
		return UserMapper::to;
	}

	protected void copyFieldToUpdate(User new_p, User old_p) {
		old_p.setLogin(new_p.getLogin());
		old_p.setPassword(new_p.getPassword());
		old_p.setAdmin(new_p.isAdmin());
	}

}
