package pl.edu.uph.ii.mik_laj.sondaze.shared.mapper;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;

/**
 * Mapper dla ankiet
 * 
 * @author andrzej
 *
 */
public class PollMapper {

	public static Poll to(JsonElement el, Poll instance) {
		if (!(el instanceof JsonObject)) {
			throw new MappingException();
		}
		JsonObject el_o = (JsonObject) el;
		Poll p = instance;
		if (el_o.hasKey("id")) {
			p.setId(((JsonNumber) el_o.get("id")).getValue());
		}
		if (el_o.hasKey("text")) {
			p.setText(((JsonString) el_o.get("text")).getValue());
		}
		return p;
	}

	public static Poll to(JsonElement el) {
		return to(el, new Poll());
	}

	public static JsonObject from(Poll p) {
		JsonObject r = new JsonObject();
		r.addElement("id", new JsonNumber(p.getId()));
		r.addElement("text", new JsonString(p.getText()));
		return r;
	}
}
