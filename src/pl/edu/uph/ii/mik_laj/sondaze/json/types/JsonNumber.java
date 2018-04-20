package pl.edu.uph.ii.mik_laj.sondaze.json.types;

/**
 * Przechowuje cfry
 * @author andrzej
 *
 */
public class JsonNumber extends JsonElement {

	int value;
	
	public JsonNumber(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "JsonNumber [value=" + value + "]";
	}
	
}
