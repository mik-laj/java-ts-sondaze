package pl.edu.uph.ii.mik_laj.sondaze.client;

import java.io.IOException;

import javax.swing.JOptionPane;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.Api;
import pl.edu.uph.ii.mik_laj.sondaze.client.frame.ListPollFrame;
import pl.edu.uph.ii.mik_laj.sondaze.client.frame.LoginFrame;
import pl.edu.uph.ii.mik_laj.sondaze.client.frame.ModeFrame;
import pl.edu.uph.ii.mik_laj.sondaze.client.frame.LoginFrame.Credentials;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

/**
 * Punkt uruchamiania dla aplikacji klienckiej
 * @author andrzej
 *
 */
public class Main {

	public Main() {

	}

	public static void main(String[] args) {
		Api api = new Api();
		int mode = ModeFrame.askForMode();
		switch (mode) {
		case ModeFrame.MODE_ANONYMOUS:
			runAnonymouseMode(api);
			break;
		case ModeFrame.MODE_AUTHORIZED:
			runAuthorizedMode(api);
			break;
		}

	}

	/**
	 * Uruchamia aplikacje w trybie anonimowym
	 * @param api
	 */
	private static void runAnonymouseMode(Api api) {
		User anonymousUser = new User();
		anonymousUser.setLogin("Anonymous");
		anonymousUser.setAdmin(false);
		ListPollFrame.startFrame(api, anonymousUser);
	}

	/**
	 * Uruchamia aplikacje w trybie uprzywilejowanym
	 * @param api
	 */
	private static void runAuthorizedMode(Api api) {
		User user = tryLogin(api);
		if (user == null) {
			System.exit(0);
		}

		ListPollFrame.startFrame(api, user);
	}

	/**
	 * Autoryzuje użytkownka
	 * @param api
	 */
	private static User tryLogin(Api api) {
		User user = null;
		do {
			Credentials credentials = LoginFrame.login();
			if (credentials == null) {
				return null;
			}
			api.getClient().setCredentials(credentials.login, credentials.password);
			try {
				user = api.getUserService().getMe();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Invalid login or password", "Invalid credentails",
						JOptionPane.ERROR_MESSAGE);
				api.getClient().setCredentials(null, null);
			}
		} while (user == null);
		return user;
	}
}
