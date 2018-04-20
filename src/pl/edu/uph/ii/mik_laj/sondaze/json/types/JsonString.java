package pl.edu.uph.ii.mik_laj.sondaze.json.types;

/**
 * PRzechowuje łancuch tekstowy
 * @author andrzej
 *
 */
public class JsonString extends JsonElement {
	private final String value;

	public JsonString(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "JsonString [value=" + value + "]";
	}

}
