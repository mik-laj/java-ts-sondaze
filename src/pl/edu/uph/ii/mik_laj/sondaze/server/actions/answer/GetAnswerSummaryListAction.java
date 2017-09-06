package pl.edu.uph.ii.mik_laj.sondaze.server.actions.answer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.AnswerDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.OptionDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.AnswerSummary;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.AnswerSummaryMapper;

/**
 * Pobiera podsumwaonei
 * @author andrzej
 *
 */
public class GetAnswerSummaryListAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/([0-9]+)/answer/summary/$");
	private AnswerDao answerDao;
	private OptionDao optionDao;

	public GetAnswerSummaryListAction(AnswerDao answerDao, OptionDao optionDao) {
		this.answerDao = answerDao;
		this.optionDao = optionDao;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("GET") && URL_PATTERN.matcher(request.getPath()).matches();
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) {
		Matcher matcher = URL_PATTERN.matcher(request.getPath());

		if (!matcher.matches()) {
			return new HttpException(501);
		}

		int poll_id = Integer.parseInt(matcher.group(1));

		List<JsonObject> option_summary = optionDao.getAll().stream()//
				.filter(t -> t.getPollId() == poll_id)//
				.map(t -> {
					AnswerSummary r = new AnswerSummary();
					int option_id = t.getId();
					int count = getAnswerCountByOptionId(option_id);

					r.setOptionId(option_id);
					r.setCount(count);

					return r;
				})
				.map(AnswerSummaryMapper::from)//
				.collect(Collectors.toList());

		return option_summary;
	}

	public int getAnswerCountByOptionId(int option_id) {
		return (int) answerDao.getAll().stream()//
				.filter(t -> t.getOptionId() == option_id).count();
	}

}
