package pl.edu.uph.ii.mik_laj.sondaze.json;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonBoolean;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNull;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;

/**
 * Umozliwia odczyt danych JSON
 * @author andrzej
 *
 */
public class JsonReader extends InputStreamReader {

	private static final int STATE_OBJECT = 0;
	private static final int STATE_FIELD = 1;
	private static final int STATE_VALUE = 2;
	private static final int STATE_POST_OBJECT = 3;
	private static final int STATE_STRING_CHR = 4;
	private static final int STATE_STRING_SLASH = 5;

	public JsonReader(InputStream in) {
		super(in);
	}

	public JsonObject readObject() throws IOException {
		JsonObject obj = new JsonObject();
		boolean done = false;
		String field = null;
		JsonElement value = null;
		int state = STATE_OBJECT;
		while (!done) {
			int c = readNonWhiteSpace();
			if (c == -1) {
				throw new JsonParserException("Unexpected end of of stream");
			}
			switch (state) {
			case STATE_OBJECT:
				if (c == '{') {
					state = STATE_FIELD;
				} else {
					throw new JsonParserException("Object must start with {");
				}
				break;
			case STATE_FIELD:
				if (c == '"') {
					this.unread(c);
					field = this.readInternalString();
					c = readNonWhiteSpace();
					if (c != ':') {
						throw new JsonParserException("Expected \":\" after a field");
					}
					state = STATE_VALUE;
				} else if (c == '}') {
					done = true;
				} else {
					throw new JsonParserException("Field must be in quote, found: " + (char) c);
				}
				break;
			case STATE_VALUE:
				unread(c);
				value = readElement();
				obj.addElement(field, value);
				state = STATE_POST_OBJECT;
				break;
			case STATE_POST_OBJECT:
				if (c == '}') {
					done = true;
				} else if (c == ',') {
					state = STATE_FIELD;
				} else {
					throw new JsonParserException("Expected \"}\" character");
				}
				break;
			}
		}
		return obj;
	}

	public JsonElement readElement() throws IOException {
		int c = readNonWhiteSpace();
		this.unread(c);
		if (c == '[') {
			return readArray();
		} else if (c >= '0' && c <= '9') {
			return readNumber();
		} else if (c == '{') {
			return this.readObject();
		} else if (c == 'f' || c == 'F') {
			readSpecialToken("false");
			return JsonBoolean.FALSE;
		} else if (c == 't' || c == 'T') {
			readSpecialToken("true");
			return JsonBoolean.TRUE;
		} else if (c == 'n' || c == 'N') {
			readSpecialToken("null");
			return JsonNull.NULL;
		} else if (c == '"') {
			return new JsonString(readInternalString());
		} else if (c == -1) {
			return null;
		}
		throw new JsonParserException("Unsupported element type c:" + (char) c);
	}

	private void readSpecialToken(String token) throws IOException {
		char[] token_bytes = token.toCharArray();
		for (int i = 0; i < token.length(); i++) {
			int c = this.read();
			if (c == -1) {
				throw new JsonParserException("Unexpected end while read token: " + token);
			}

			if (token_bytes[i] != Character.toLowerCase(c)) {
				throw new JsonParserException("Unexpected character " + (char) c + ", while parsing token " + token);
			}
		}
	}

	public JsonNumber readNumber() throws IOException {
		boolean done = false;
		StringBuilder sb = new StringBuilder();
		while (!done) {
			int c = this.read();
			if (c >= '0' && c <= '9') {
				sb.appendCodePoint(c);
			} else {
				unread(c);
				done = true;
			}
		}
		return new JsonNumber(Integer.parseInt(sb.toString()));
	}

	public JsonArray readArray() throws IOException {
		boolean done = false;
		JsonArray arr = new JsonArray();
		assertStartCharacter('[');
		while (!done) {
			int c = readNonWhiteSpace();
			switch (c) {
			case ',':
				break;
			case ']':
				done = true;
				break;
			default:
				unread(c);
				JsonElement element = readElement();
				arr.add(element);
				break;
			}
		}
		return arr;
	}

	public JsonString readString() throws IOException {
		return new JsonString(readInternalString());
	}

	private String readInternalString() throws IOException {
		int state = STATE_STRING_CHR;
		StringBuilder sb = new StringBuilder();
		// Assert quote start
		assertStartCharacter('"');
		while (true) {
			int c = this.read();
			if (c == -1) {
				throw new JsonParserException("Unexpected end of string");
			}
			switch (state) {
			case STATE_STRING_CHR:
				if (c == '"') {
					return sb.toString();
				} else if (c == '\\') {
					state = STATE_STRING_SLASH;
				} else {
					sb.appendCodePoint(c);
				}
				break;
			case STATE_STRING_SLASH:
				switch (c) {
				case '\\':
				case '/':
				case '"':
					sb.appendCodePoint(c);
					state = STATE_STRING_CHR;
					break;
				default:
					throw new JsonParserException("Invalid escape sequence");
				}
				break;
			}
		}
	}

	private void assertStartCharacter(char chr) throws IOException {
		int c = this.readNonWhiteSpace();
		if (c != chr) {
			throw new JsonParserException("String must start with " + (char) chr);
		}
	}

	int unread_byte = -1;

	@Override
	public int read() throws IOException {
		if (unread_byte != -1) {
			int chr = unread_byte;
			unread_byte = -1;
			return chr;
		}
		return super.read();
	}

	public void unread(int chr) {
		unread_byte = chr;
	}

	private int readNonWhiteSpace() throws IOException {
		int c;
		do {
			c = this.read();
		} while (c == ' ' || c == '\n' || c == '\r' || c == '\t');
		return c;
	}

	public static final JsonReader fromString(String str) {
		return new JsonReader(new ByteArrayInputStream(str.getBytes()));
	}

	public static final JsonElement map(String str) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
		return map(is);
	}

	public static JsonElement map(InputStream is) throws IOException {
		JsonReader reader = new JsonReader(is);
		JsonElement o = reader.readElement();
		reader.close();
		return o;
	}

}
