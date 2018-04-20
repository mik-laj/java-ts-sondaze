package pl.edu.uph.ii.mik_laj.sondaze.server.actions.user;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.UserDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.UserMapper;

public class GetUserAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/user/([0-9]+)/$");
	private UserDao userDao;

	public GetUserAction(UserDao pollDao) {
		this.userDao = pollDao;
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

		int id = Integer.parseInt(matcher.group(1));

		Optional<User> entity_op = userDao.getAll()//
				.stream()//
				.filter(t -> t.getId() == id)//
				.findFirst();
		userDao.getAll().forEach(System.out::println);
		if (!entity_op.isPresent()) {
			throw new HttpException(404);
		}

		User user = entity_op.get();

		return UserMapper.fromSecure(user);
	}

}
