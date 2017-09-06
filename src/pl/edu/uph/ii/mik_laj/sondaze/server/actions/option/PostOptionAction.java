package pl.edu.uph.ii.mik_laj.sondaze.server.actions.option;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.OptionDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.OptionMapper;

/**
 * Zapisuje nowa odpowiedz
 * 
 * @author andrzej
 *
 */
public class PostOptionAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/([0-9]+)/option/$");
	private OptionDao entityDao;

	public PostOptionAction(OptionDao pollDao) {
		this.entityDao = pollDao;
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

		int poll_id = Integer.parseInt(matcher.group(1));

		JsonObject body = (JsonObject) parseBody(request);
		
		Option entity = new Option();
		OptionMapper.to(body, entity);
		entity.setPollId(poll_id);
		entityDao.create(entity);
		
		return OptionMapper.from(entity);
	}

}
