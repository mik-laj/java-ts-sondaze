package pl.edu.uph.ii.mik_laj.sondaze.shared.entity;

import pl.edu.uph.ii.mik_laj.sondaze.server.dao.HaveIdInterface;

/**
 * Odpowiedz
 * @author andrzej
 *
 */
public class Answer implements HaveIdInterface {

	private int id;
	private int optionId;
	private int userId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOptionId() {
		return optionId;
	}

	public void setOptionId(int optionId) {
		this.optionId = optionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AnswerAdmin [id=" + id + ", optionId=" + optionId + ", userId=" + userId + "]";
	}

}
