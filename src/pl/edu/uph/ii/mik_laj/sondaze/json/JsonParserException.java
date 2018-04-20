package pl.edu.uph.ii.mik_laj.sondaze.json;

import java.io.IOException;

/**
 * Wyjatek wypadku niepowodzenia przetwarzania JSON
 * @author andrzej
 *
 */
public class JsonParserException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JsonParserException() {
		super();
	}

	public JsonParserException(String message) {
		super(message);
	}

}
