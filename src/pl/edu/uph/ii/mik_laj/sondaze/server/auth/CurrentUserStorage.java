package pl.edu.uph.ii.mik_laj.sondaze.server.auth;

import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

/**
 * Pamiec na katualnie zalogowanego uzytkownika
 * 
 * @author andrzej
 *
 */
public class CurrentUserStorage {
	private User currentUser;

	/**
	 * Aktualnie zautoryzowany uzytkownik
	 * @return
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

}
