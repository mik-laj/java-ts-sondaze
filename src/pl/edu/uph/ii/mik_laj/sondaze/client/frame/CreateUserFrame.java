package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Okno tworzaące nowego uzytkownika
 */
public class CreateUserFrame {

	/**
	 * Zawiera informacje pobrane z formularza
	 * @author andrzej
	 *
	 */
	public static class UserData {

		public UserData(String login, String password) {
			super();
			this.login = login;
			this.password = password;
		}

		public final String login;
		public final String password;

		@Override
		public String toString() {
			return "Credentials [login=" + login + ", password=" + password + "]";
		}

	}

	/**
	 * Wyswietla okno
	 * 
	 * @return obiekt UserData, z danymi wskazanymi przez uzytkownika lub null, w przypadku nie powoerdzenia
	 */
	public static UserData showDialog() {
		JPanel panel = new JPanel(new BorderLayout(5, 5));

		JPanel label = new JPanel(new GridLayout(0, 1, 2, 2));
		label.add(new JLabel("Login", SwingConstants.RIGHT));
		label.add(new JLabel("Password", SwingConstants.RIGHT));
		panel.add(label, BorderLayout.WEST);

		JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
		JTextField username = new JTextField();
		controls.add(username);
		JPasswordField password = new JPasswordField();
		controls.add(password);
		panel.add(controls, BorderLayout.CENTER);

		int r = JOptionPane.showConfirmDialog(null, panel, "Create user", JOptionPane.OK_CANCEL_OPTION);

		if (r == 0) {
			String user = username.getText();
			String pass = new String(password.getPassword());
			return new UserData(user, pass);
		}
		return null;
	}
}
