package pl.edu.uph.ii.mik_laj.sondaze.server.actions.poll;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.PollDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.PollMapper;

public class PatchPollAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/([0-9]+)/$");
	private PollDao entityDao;

	public PatchPollAction(PollDao userDao) {
		this.entityDao = userDao;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("PATCH") && URL_PATTERN.matcher(request.getPath()).matches();
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) throws IOException {
		Matcher matcher = URL_PATTERN.matcher(request.getPath());
		
		if (!matcher.matches()) {
			return new HttpException(501);
		}
		
		int id = Integer.parseInt(matcher.group(1));
		
		Optional<Poll> entity_op = entityDao.getAll()//
				.stream()//
				.filter(t -> t.getId() == id)//
				.findFirst();

		if (!entity_op.isPresent()) {
			throw new HttpException(404);
		}
		
		JsonObject body = (JsonObject) parseBody(request);
		
		Poll entity = entity_op.get();
		PollMapper.to(body, entity);

		entityDao.update(entity);
	
		return PollMapper.from(entity);
	}

}
