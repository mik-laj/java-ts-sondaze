package pl.edu.uph.ii.mik_laj.sondaze.server.actions.poll;

import java.io.IOException;
import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.PollDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.PollMapper;

public class PostPollAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/$");
	private PollDao entityDao;

	public PostPollAction(PollDao pollDao) {
		this.entityDao = pollDao;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("POST") && URL_PATTERN.matcher(request.getPath()).matches();
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) throws IOException {
		JsonObject body = (JsonObject) parseBody(request);
		
		Poll entity = new Poll();
		PollMapper.to(body, entity);
		entityDao.create(entity);
		
		return PollMapper.from(entity);
	}

}
