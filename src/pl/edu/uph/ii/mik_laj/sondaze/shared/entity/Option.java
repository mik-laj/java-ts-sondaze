package pl.edu.uph.ii.mik_laj.sondaze.shared.entity;

import pl.edu.uph.ii.mik_laj.sondaze.server.dao.HaveIdInterface;

/**
 * Opcje
 * 
 * @author andrzej
 *
 */
public class Option implements HaveIdInterface {
	private int id;
	private int pollId;
	private String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPollId() {
		return pollId;
	}

	public void setPollId(int pollId) {
		this.pollId = pollId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Option [id=" + id + ", answerId=" + pollId + ", text=" + text + "]";
	}

}
