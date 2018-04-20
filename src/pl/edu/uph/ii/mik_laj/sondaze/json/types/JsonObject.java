package pl.edu.uph.ii.mik_laj.sondaze.json.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Przechowuje obiekt w formie klucz<->wartosc
 * @author andrzej
 *
 */
public class JsonObject extends JsonElement {

	private Map<String, JsonElement> elements = new HashMap<>();

	public Map<String, JsonElement> getElements() {
		return Collections.unmodifiableMap(elements);
	}

	public void addElement(String key, JsonElement element) {
		elements.put(key, element);
	}

	public void removeElement(String key) {
		elements.remove(key);
	}

	public boolean hasKey(String key) {
		return elements.containsKey(key);
	}

	public JsonElement get(String key) {
		return elements.get(key);
	}

	public int size() {
		return elements.size();
	}

	@Override
	public String toString() {
		return "JsonObject [elements=" + Arrays.asList(elements) + "]";
	}
	
	

}
