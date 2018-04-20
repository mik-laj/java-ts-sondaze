package pl.edu.uph.ii.mik_laj.sondaze.json.types;

/**
 * Przechowuje wartosc boolowska
 * @author andrzej
 *
 */
public class JsonBoolean extends JsonElement {

	public static final JsonBoolean TRUE = new JsonBoolean(true);
	public static final JsonBoolean FALSE = new JsonBoolean(false);

	private boolean value;

	private JsonBoolean(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "JsonBoolean [value=" + value + "]";
	}

	public static JsonBoolean valueOf(boolean b) {
		return b ? TRUE : FALSE;
	}

}
