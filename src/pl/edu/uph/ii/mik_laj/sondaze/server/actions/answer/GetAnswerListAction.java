package pl.edu.uph.ii.mik_laj.sondaze.server.actions.answer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.AnswerDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.OptionDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.AnswerMapper;

/**
 * Zwraca liste ankiet
 */
public class GetAnswerListAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/([0-9]+)/answer/$");
	private AnswerDao answerDao;
	private OptionDao optionDao;

	public GetAnswerListAction(AnswerDao answerDao, OptionDao optionDao) {
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
		
		List<Integer> options_ids = optionDao.getAll().stream()//
				.filter(t-> t.getPollId() == poll_id)//
				.mapToInt(Option::getId)// 
				.boxed()//
				.collect(Collectors.toList());
		
		List<JsonObject> list = answerDao.getAll().stream()// 
			.filter(t -> options_ids.contains(t.getOptionId()))
			.map(AnswerMapper::from)//
			.collect(Collectors.toList());
		return list;
	}

}
