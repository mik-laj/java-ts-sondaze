package pl.edu.uph.ii.mik_laj.sondaze.json;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.*;

/**
 * Umozliwia zapis danych JSON
 * @author andrzej
 *
 */
public class JsonWriter extends OutputStreamWriter {

	private boolean prettyPrint;

	private int tab = 0;;

	public JsonWriter(OutputStream out) {
		this(out, true);
	}

	public JsonWriter(OutputStream out, boolean prettyPrint) {
		super(out);
		this.prettyPrint = prettyPrint;
	}

	private void writeString(String value) throws IOException {
		this.write('\"');
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '\t':
				this.write("\\t");
				break;
			case '\n':
				this.write("\\n");
				break;
			case '\r':
				this.write("\\r");
				break;
			case '"':
			case '\\':
				this.write('\\');
				this.write(c);
				break;
			default:
				this.write(c);
			}
		}
		this.write('\"');
	}

	private void writeCollection(Collection<?> collection) throws IOException {
		this.write('[');
		tab++;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext();) {
			Object a = iterator.next();
			this.tab();
			this.writeObject(a);
			if (iterator.hasNext()) {
				this.write(", ");
			}
		}
		this.tabOut();
		this.write(']');

	}

	public void writeObject(Object a) throws IOException {
		if (a instanceof String) {
			writeString((String) a);
		} else if (a instanceof Collection<?>) {
			writeCollection((Collection<?>) a);
		} else if (a instanceof JsonElement) {
			writeJsonElement((JsonElement) a);
		} else if (a instanceof Integer) {
			write(Integer.toString((int) a));
		} else if (a.getClass().isArray()) {
			if (a instanceof int[]) {
				this.write('[');
				int al = ((int[]) a).length;
				tab++;
				for (int i = 0; i < al; i++) {
					this.tab();
					this.write(Integer.toString(((int[]) a)[i]));
					if (i != al - 1) {
						this.write(",");
					}
				}
				this.write(']');
			}
		} else {
			throw new RuntimeException("Unsupported type: " + a.getClass());
		}
	}

	private void writeJsonElement(JsonElement obj) throws IOException {
		if (obj instanceof JsonArray) {
			writeCollection(((JsonArray) obj).toList());
		} else if (obj instanceof JsonBoolean) {
			write(obj == JsonBoolean.TRUE ? "true" : "false");
		} else if (obj instanceof JsonNull) {
			write("null");
		} else if (obj instanceof JsonNumber) {
			write(Integer.toString(((JsonNumber) obj).getValue()));
		} else if (obj instanceof JsonObject) {
			writeJsonObject((JsonObject) obj);
		} else if (obj instanceof JsonString) {
			writeString(((JsonString) obj).getValue());
		} else {
			throw new IOException(String.format("Unable to write class: %s", obj.getClass()));
		}

	}

	private void writeJsonObject(JsonObject obj) throws IOException {
		this.write('{');
		this.tabIn();
		for (Iterator<Entry<String, JsonElement>> iterator = obj.getElements().entrySet().iterator(); iterator
				.hasNext();) {
			Entry<String, JsonElement> a = iterator.next();
			this.writeString(a.getKey());
			this.write(" : ");
			this.writeJsonElement(a.getValue());
			if (iterator.hasNext()) {
				this.write(",");
				this.tab();
			}
		}
		this.tabOut();
		this.write('}');
	}

	private void tabIn() throws IOException {
		tab++;
		tab();
	}

	private void tabOut() throws IOException {
		tab--;
		tab();
	}

	private void tab() throws IOException {
		if (prettyPrint) {
			this.write('\n');
			for (int i = 0; i < tab; i++) {
				this.write('\t');
			}
		}
	}

	public static String map(Object element) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		JsonWriter writer = new JsonWriter(os);
		writer.writeObject(element);
		writer.close();
		return os.toString();
	}

	public static void main(String[] args) throws IOException {
		JsonObject o = new JsonObject();
		o.addElement("id", new JsonNumber(5));
		o.addElement("text", new JsonString("AAA"));
		System.out.println(map(o));
	}
}
