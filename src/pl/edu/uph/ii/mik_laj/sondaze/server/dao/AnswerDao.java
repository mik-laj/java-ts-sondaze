package pl.edu.uph.ii.mik_laj.sondaze.server.dao;

import java.io.IOException;
import java.util.function.Function;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonParserException;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Answer;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.AnswerMapper;

/**
 * Klasa dostepowa do odpowiedzi. Wykorzystuuje pliki w formacie JSON
 * @author andrzej
 *
 */
public class AnswerDao extends BaseDao<Answer> {

	public AnswerDao(String filename) throws IOException, JsonParserException {
		super(filename);
	}

	protected Function<Answer, ? extends JsonElement> getFromMapper() {
		return AnswerMapper::from;
	}

	protected Function<? super JsonElement, Answer> getToMapper() {
		return AnswerMapper::to;
	}

	protected void copyFieldToUpdate(Answer new_p, Answer old_p) {
		old_p.setOptionId(new_p.getOptionId());
		old_p.setUserId(new_p.getUserId());
	}

}
