package pl.edu.uph.ii.mik_laj.sondaze.shared.mapper;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonBoolean;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.AnswerSummary;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

public class UserMapper {

	public static User to(JsonElement el, User instance) {
		if (!(el instanceof JsonObject)) {
			throw new MappingException();
		}
		JsonObject el_o = (JsonObject) el;
		User u = instance;
		if (el_o.hasKey("id")) {
			u.setId(((JsonNumber) el_o.get("id")).getValue());
		}
		if (el_o.hasKey("login")) {
			u.setLogin(((JsonString) el_o.get("login")).getValue());
		}
		if (el_o.hasKey("password")) {
			u.setPassword(((JsonString) el_o.get("password")).getValue());
		}
		if (el_o.hasKey("is_admin")) {
			u.setAdmin(((JsonBoolean) el_o.get("is_admin")).getValue());
		}
		return u;
	}

	public static User to(JsonElement el) {
		return to(el, new User());
	}

	public static JsonObject from(User u) {
		JsonObject r = new JsonObject();
		r.addElement("id", new JsonNumber(u.getId()));
		r.addElement("login", new JsonString(u.getLogin()));
		r.addElement("password", new JsonString(u.getPassword()));
		r.addElement("is_admin", JsonBoolean.valueOf(u.isAdmin()));
		return r;
	}

	public static JsonObject fromSecure(User u) {
		JsonObject r = new JsonObject();
		r.addElement("id", new JsonNumber(u.getId()));
		r.addElement("login", new JsonString(u.getLogin()));
		r.addElement("is_admin", JsonBoolean.valueOf(u.isAdmin()));
		return r;
	}

}
