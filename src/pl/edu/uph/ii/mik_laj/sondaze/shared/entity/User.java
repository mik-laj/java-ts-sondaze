package pl.edu.uph.ii.mik_laj.sondaze.shared.entity;

import pl.edu.uph.ii.mik_laj.sondaze.server.dao.HaveIdInterface;

/**
 * UZytkownik
 * 
 * @author andrzej
 *
 */
public class User implements HaveIdInterface {
	int id;
	String login;
	String password;
	boolean isAdmin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password + ", isAdmin=" + isAdmin + "]";
	}

}
