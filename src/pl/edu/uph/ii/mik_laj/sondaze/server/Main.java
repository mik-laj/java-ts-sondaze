package pl.edu.uph.ii.mik_laj.sondaze.server;

import java.io.IOException;

import pl.edu.uph.ii.mik_laj.sondaze.server.http.HttpServer;

/**
 * Główny plik serwera
 * @author andrzej
 *
 */
public class Main {

	public static void main(String[] args) throws IOException {
		new HttpServer(new MyApp()).run();
	}
}