package pl.edu.uph.ii.mik_laj.sondaze.server.actions.user;

import java.io.IOException;
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

public class DeleteUserAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/user/([0-9]+)/$");
	private UserDao userDao;

	public DeleteUserAction(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("DELETE") && URL_PATTERN.matcher(request.getPath()).matches();
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) throws IOException {
		Matcher matcher = URL_PATTERN.matcher(request.getPath());
		if (!matcher.matches()) {
			return new HttpException(501);
		}
		int id = Integer.parseInt(matcher.group(1));
		Optional<User> user_op = userDao.getAll()//
				.stream()//
				.filter(t -> t.getId() == id)//
				.findFirst();

		if (!user_op.isPresent()) {
			throw new HttpException(404);
		}
		User user = user_op.get();
		userDao.delete(user);

		return UserMapper.fromSecure(user);
	}

}
