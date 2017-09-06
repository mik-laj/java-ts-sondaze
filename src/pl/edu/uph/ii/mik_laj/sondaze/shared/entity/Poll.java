package pl.edu.uph.ii.mik_laj.sondaze.shared.entity;

import pl.edu.uph.ii.mik_laj.sondaze.server.dao.HaveIdInterface;

/**
 * Ankieta
 * 
 * @author andrzej
 *
 */
public class Poll implements HaveIdInterface {
	private int id;
	private String text;

	public Poll() {

	}

	public Poll(int id, String text) {
		super();
		this.id = id;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Poll [id=" + id + ", text=" + text + "]";
	}

}
