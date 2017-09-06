package pl.edu.uph.ii.mik_laj.sondaze.server.dao;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonParserException;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.PollMapper;

/**
 * Klasa dostepowa do zarządzania ankietami. Wykorzystuuje pliki w formacie JSON
 * @author andrzej
 *
 */
public class PollDao extends BaseDao<Poll> {

	private OptionDao optionDao;

	public PollDao(String filename, OptionDao optionDao) throws IOException, JsonParserException {
		super(filename);
		this.optionDao = optionDao;
	}

	@Override
	public void delete(Poll p) throws IOException {
		super.delete(p);
		List<Option> relatedOption = optionDao.getAll().stream()//
			.filter(d -> d.getPollId() == p.getId())//
			.collect(Collectors.toList());
		for(Option option: relatedOption){
			optionDao.delete(option);
		}
	}

	protected Function<Poll, ? extends JsonElement> getFromMapper() {
		return PollMapper::from;
	}

	protected Function<? super JsonElement, Poll> getToMapper() {
		return PollMapper::to;
	}

	protected void copyFieldToUpdate(Poll new_p, Poll old_p) {
		old_p.setText(new_p.getText());
	}

}
