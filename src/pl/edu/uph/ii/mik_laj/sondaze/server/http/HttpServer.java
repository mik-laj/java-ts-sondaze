
package pl.edu.uph.ii.mik_laj.sondaze.server.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.App;

/**
 * Głœóny serwer. 
 * 
 * @author andrzej
 *
 */
public class HttpServer {

	private App app;

	public HttpServer(App app) {
		this.app = app;
	}
	
	private ServerSocket server_socket;

	public void run() throws IOException {
		server_socket = new ServerSocket(8000);
		System.out.println("Server running http://localhost:8000");

		while (true) {
			Socket client_socket = server_socket.accept();
			InputStream in = client_socket.getInputStream();
			OutputStream out = client_socket.getOutputStream();
			RequestReader reader = new RequestReader(in);
			ResponsetWriter writer = new ResponsetWriter(new OutputStreamWriter(out));

			Request request = reader.readRequest();
			ResponseInterface response = app.proccessRequest(request);
			writer.writeResponse(response);
			
			writer.close();
			reader.close();
			client_socket.close();

		}
	}
}
