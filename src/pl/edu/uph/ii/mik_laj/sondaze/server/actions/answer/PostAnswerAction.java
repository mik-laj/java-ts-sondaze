package pl.edu.uph.ii.mik_laj.sondaze.server.actions.answer;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.auth.CurrentUserStorage;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.AnswerDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.OptionDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Answer;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.AnswerMapper;

/**
 * Zapisuje odpowiedz
 * 
 * @author andrzej
 *
 */
public class PostAnswerAction extends BaseJsonAction {
	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/([0-9]+)/answer/$");
	private AnswerDao answerDao;
	// private OptionDao optionDao;
	private CurrentUserStorage current_user_storage;

	public PostAnswerAction(AnswerDao answerDao, OptionDao optionDao, CurrentUserStorage current_user_storage) {
		this.answerDao = answerDao;
		// this.optionDao = optionDao;
		this.current_user_storage = current_user_storage;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("POST") && URL_PATTERN.matcher(request.getPath()).matches();
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) throws IOException {
		Matcher matcher = URL_PATTERN.matcher(request.getPath());

		if (!matcher.matches()) {
			return new HttpException(501);
		}
		JsonObject body = (JsonObject) parseBody(request);

		User currentUser = current_user_storage.getCurrentUser();
		int user_id = currentUser == null ? 0 : currentUser.getId();
		// int poll_id = Integer.parseInt(matcher.group(1));
		int option_id = ((JsonNumber) body.get("option_id")).getValue();

		Answer answer = new Answer();
		answer.setOptionId(option_id);
		answer.setUserId(user_id);
		answerDao.create(answer);

		return AnswerMapper.from(answer);
	}

}
