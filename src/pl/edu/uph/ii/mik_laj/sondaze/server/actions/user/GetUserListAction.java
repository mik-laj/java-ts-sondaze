package pl.edu.uph.ii.mik_laj.sondaze.server.actions.user;

import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.UserDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.UserMapper;

public class GetUserListAction extends BaseJsonAction {

	private UserDao userDao;

	public GetUserListAction(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("GET") && request.getPath().matches("^/api/v1/user/$");
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) {
		List<JsonObject> list = userDao.getAll()//
				.stream()//
				.map(UserMapper::fromSecure)//
				.collect(Collectors.toList());
		return list;
	}

}
