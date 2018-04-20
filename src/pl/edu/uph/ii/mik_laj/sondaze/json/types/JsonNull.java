package pl.edu.uph.ii.mik_laj.sondaze.json.types;

/**
 * Wartosc pusta
 * @author andrzej
 *
 */
public class JsonNull extends JsonElement {
	public static final JsonNull NULL = new JsonNull();

	@Override
	public String toString() {
		return "JsonNull []";
	}
	
}
