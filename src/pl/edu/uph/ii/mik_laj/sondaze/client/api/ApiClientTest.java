package pl.edu.uph.ii.mik_laj.sondaze.client.api;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

public class ApiClientTest {

	public static void main(String[] args) throws IOException {
		Api a = new Api();
		a.getClient().setCredentials("user", "pass");
		User me = a.getUserService().getMe();
		System.out.println(me);
	}
}
