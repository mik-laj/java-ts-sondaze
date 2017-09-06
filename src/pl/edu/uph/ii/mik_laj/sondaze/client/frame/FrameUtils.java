package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.function.Consumer;

/**
 * Klasa narzędziowa do obsługi okien
 * @author andrzej
 *
 */
public class FrameUtils {

	public static final WindowFocusListener simplifyWindowListener(Consumer<WindowEvent> lostFocus,
			Consumer<WindowEvent> gainedFocus) {
		return new WindowFocusListener() {

			@Override
			public void windowLostFocus(WindowEvent e) {
				lostFocus.accept(e);

			}

			@Override
			public void windowGainedFocus(WindowEvent e) {
				gainedFocus.accept(e);
			}
		};
	}
}
