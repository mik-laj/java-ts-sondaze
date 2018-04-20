package pl.edu.uph.ii.mik_laj.sondaze.shared.mapper;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Answer;

/**
 * Mapper dla odzpoiwiedzi
 * 
 * @author andrzej
 *
 */
public class AnswerMapper {

	public static Answer to(JsonElement el, Answer instance) {
		if (!(el instanceof JsonObject)) {
			throw new MappingException();
		}
		JsonObject el_o = (JsonObject) el;
		Answer p = instance;
		if (el_o.hasKey("id")) {
			p.setId(((JsonNumber) el_o.get("id")).getValue());
		}

		if (el_o.hasKey("option_id")) {
			p.setOptionId((((JsonNumber) el_o.get("option_id")).getValue()));
		}

		if (el_o.hasKey("user_id")) {
			p.setUserId(((JsonNumber) el_o.get("user_id")).getValue());
		}
		
		return p;
	}

	public static Answer to(JsonElement el) {
		return to(el, new Answer());
	}

	public static JsonObject from(Answer p) {
		JsonObject r = new JsonObject();
		r.addElement("id", new JsonNumber(p.getId()));
		r.addElement("option_id", new JsonNumber(p.getOptionId()));
		r.addElement("user_id", new JsonNumber(p.getUserId()));
		return r;
	}
}
