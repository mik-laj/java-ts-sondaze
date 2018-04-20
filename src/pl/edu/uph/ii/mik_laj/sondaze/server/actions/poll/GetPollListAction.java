package pl.edu.uph.ii.mik_laj.sondaze.server.actions.poll;

import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.PollDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.PollMapper;

public class GetPollListAction extends BaseJsonAction {

	private PollDao entityDao;

	public GetPollListAction(PollDao userDao) {
		this.entityDao = userDao;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("GET") && request.getPath().matches("^/api/v1/poll/$");
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) {
		List<JsonObject> list = entityDao.getAll()//
				.stream()//
				.map(PollMapper::from)//
				.collect(Collectors.toList());
		return list;
	}

}
