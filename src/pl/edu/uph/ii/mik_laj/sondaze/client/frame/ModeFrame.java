package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import javax.swing.JOptionPane;

/**
 * Okno wyboru trybu uruchamiania - anonimowy, uwierzytelniony
 * @author andrzej
 *
 */
public class ModeFrame {

	public static final int MODE_UKNOWN = -1;
	public static final int MODE_AUTHORIZED = 0;
	public static final int MODE_ANONYMOUS = 1;

	public static int askForMode() {
		String[] options = { "Authorized", "Anonymous" };
		int n = JOptionPane.showOptionDialog(null, "Do you have login credentials?", "Credentials",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		return n;
	}

}
