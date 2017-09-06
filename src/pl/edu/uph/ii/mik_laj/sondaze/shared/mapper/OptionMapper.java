package pl.edu.uph.ii.mik_laj.sondaze.shared.mapper;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;

/**
 * Mapper dla opcji
 * 
 * @author andrzej
 *
 */
public class OptionMapper {

	public static Option to(JsonElement el, Option instance) {
		if (!(el instanceof JsonObject)) {
			throw new MappingException();
		}
		JsonObject el_o = (JsonObject) el;
		Option p = instance;
		if (el_o.hasKey("id")) {
			p.setId(((JsonNumber) el_o.get("id")).getValue());
		}
		if (el_o.hasKey("poll_id")) {
			p.setPollId(((JsonNumber) el_o.get("poll_id")).getValue());
		}
		
		if (el_o.hasKey("text")) {
			p.setText(((JsonString) el_o.get("text")).getValue());
		}
		return p;
	}

	public static Option to(JsonElement el) {
		return to(el, new Option());
	}

	public static JsonObject from(Option p) {
		JsonObject r = new JsonObject();
		r.addElement("id", new JsonNumber(p.getId()));
		r.addElement("poll_id", new JsonNumber(p.getPollId()));
		r.addElement("text", new JsonString(p.getText()));
		return r;
	}
}
