package pl.edu.uph.ii.mik_laj.sondaze.shared.mapper;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.AnswerSummary;

/*
 * Mapper dla podsumowanka odpowiedzi
 * 
 */
public class AnswerSummaryMapper {
	public static AnswerSummary to(JsonElement el, AnswerSummary instance) {
		if (!(el instanceof JsonObject)) {
			throw new MappingException();
		}
		JsonObject el_o = (JsonObject) el;
		AnswerSummary u = instance;
		if (el_o.hasKey("count")) {
			u.setCount(((JsonNumber) el_o.get("count")).getValue());
		}
		if (el_o.hasKey("option_id")) {
			u.setOptionId(((JsonNumber) el_o.get("option_id")).getValue());
		}
		return u;
	}

	public static AnswerSummary to(JsonElement el) {
		return to(el, new AnswerSummary());
	}

	public static JsonObject from(AnswerSummary u) {
		JsonObject r = new JsonObject();
		r.addElement("count", new JsonNumber(u.getCount()));
		r.addElement("option_id", new JsonNumber(u.getOptionId()));
		return r;
	}

}
