package pl.edu.uph.ii.mik_laj.sondaze.json.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Przechowuje tablice elementow
 * @author andrzej
 *
 */
public class JsonArray extends JsonElement implements Iterable<JsonElement> {
	private List<JsonElement> elements = new ArrayList<>();

	public JsonArray() {

	}

	public JsonArray(Collection<? extends JsonElement> collection) {
		this.elements.addAll(collection);
	}

	public void add(JsonElement el) {
		elements.add(el);
	}

	public JsonElement[] toArray() {
		return elements.toArray(new JsonElement[elements.size()]);
	}

	public List<JsonElement> toList() {
		return Collections.unmodifiableList(elements);
	}

	public int size() {
		return elements.size();
	}

	public boolean isEmpty() {
		return elements.isEmpty();
	}

	public Iterator<JsonElement> iterator() {
		return elements.iterator();
	}

	public JsonElement get(int index) {
		return elements.get(index);
	}

	public Stream<JsonElement> stream() {
		return elements.stream();
	}

	@Override
	public String toString() {
		return "JsonArray [elements=" + Arrays.asList(elements) + "]";
	}

}
