package pl.edu.uph.ii.mik_laj.sondaze.server.core;

/**
 * Wyjatek zawierajacy informacje o kodzie bledu
 * @author andrzej
 *
 */
public class HttpException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int code;

	public HttpException(int code, String message) {
		super(message);

		this.code = code;
	}

	public HttpException(int code, Throwable cause) {
		super(cause);
		this.code = code;

	}

	public HttpException(int code) {
		super();
		this.code = code;
	}

	public int getStatusCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
