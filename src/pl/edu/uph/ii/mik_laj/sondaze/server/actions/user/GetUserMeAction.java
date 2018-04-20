package pl.edu.uph.ii.mik_laj.sondaze.server.actions.user;

import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.server.auth.CurrentUserStorage;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.UserMapper;

public class GetUserMeAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/user/me/$");
	private CurrentUserStorage currentUserStorage;

	public GetUserMeAction(CurrentUserStorage currentUserStorage) {
		this.currentUserStorage = currentUserStorage;
	}

	@Override
	public boolean support(RequestInterface request) {
		return request.getMethod().equals("GET") && URL_PATTERN.matcher(request.getPath()).matches();
	}

	@Override
	public Object getJson(RequestInterface request, ResponseInterface response) {
		User currentUser = currentUserStorage.getCurrentUser();
		return UserMapper.fromSecure(currentUser);
	}

}
