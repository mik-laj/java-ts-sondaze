package pl.edu.uph.ii.mik_laj.sondaze.json;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonBoolean;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNull;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;

public class JsonReaderTest {

	@Test
	public void testReadString() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("\"foo\"");
		assertThat(reader.readString().toString(), equalTo("foo"));
	}

	@Test
	public void testReadStringWithQuote() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("\"foo\\\"\"");
		assertThat(reader.readString().toString(), equalTo("foo\""));

	}

	@Test
	public void testReadNumber() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("1");
		assertThat(reader.readNumber().getValue(), equalTo(1));
	}

	@Test
	public void testReadArrayOfNumber() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("[1,2,3]");
		JsonArray arr = reader.readArray();
		List<JsonElement> l = Arrays.asList(arr.toArray());

		assertThat(l, everyItem(instanceOf(JsonNumber.class)));
		assertSame(((JsonNumber) l.get(1)).getValue(), 2);
	}

	@Test
	public void testReadArrayOfMixed() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("[\"foo\",     2222,     [333], true, false]");
		JsonArray arr = reader.readArray();
		List<JsonElement> l = Arrays.asList(arr.toArray());

		assertThat(((JsonString) l.get(0)).getValue(), equalTo("foo"));
		assertThat(((JsonNumber) l.get(1)).getValue(), equalTo(2222));
		assertThat(l.get(2), instanceOf(JsonArray.class));
		assertSame(l.get(3), JsonBoolean.TRUE);
		assertSame(l.get(4), JsonBoolean.FALSE);
	}

	@Test
	public void testReadObject() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("{" //
				+ " \"a\":    true," //
				+ " \"b\": false," //
				+ " \"c\":[1,2,3,4]," //
				+ " \"d\": null," //
				+ " \"e\": { }," //
				+ " \"f\": \"fff\"," //
				+ "}");

		JsonObject arr = reader.readObject();

		assertSame(arr.size(), 6);

		Map<String, JsonElement> elements = arr.getElements();
		assertThat(elements.get("a"), equalTo(JsonBoolean.TRUE));
		assertThat(elements.get("b"), equalTo(JsonBoolean.FALSE));
		assertThat(elements.get("c"), instanceOf(JsonArray.class));
		assertThat(elements.get("d"), equalTo(JsonNull.NULL));
		assertThat(elements.get("e"), instanceOf(JsonObject.class));
		assertThat(elements.get("f"), instanceOf(JsonString.class));
	}

	@Test
	public void testReadObjectWithQuoteInString() throws IOException, JsonParserException {
		JsonReader reader = JsonReader.fromString("{" //
				+ " \"\\\"\":    true," //
				+ "}");

		JsonObject arr = reader.readObject();

		assertSame(arr.size(), 1);

		Map<String, JsonElement> elements = arr.getElements();
		assertThat(elements.get("\""), equalTo(JsonBoolean.TRUE));
	}
}
