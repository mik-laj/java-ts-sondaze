package pl.edu.uph.ii.mik_laj.sondaze.server.actions.option;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.uph.ii.mik_laj.sondaze.server.core.BaseJsonAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.HttpException;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.OptionDao;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.RequestInterface;
import pl.edu.uph.ii.mik_laj.sondaze.server.http.ResponseInterface;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.OptionMapper;

/**
 * Kasuje odpowiedz
 * @author andrzej
 *
 */
public class DeleteOptionAction extends BaseJsonAction {

	private final static Pattern URL_PATTERN = Pattern.compile("^/api/v1/poll/([0-9]+)/option/([0-9]+)/$");
	private OptionDao entityDao;

	public DeleteOptionAction(OptionDao pollDao) {
		this.entityDao = pollDao;
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

		int poll_id = Integer.parseInt(matcher.group(1));
		int option_id = Integer.parseInt(matcher.group(2));

		Optional<Option> entity_op = entityDao.getAll()//
				.stream()//
				.filter(t -> t.getId() == option_id && t.getPollId() == poll_id)//
				.findFirst();

		if (!entity_op.isPresent()) {
			throw new HttpException(404);
		}

		Option entity = entity_op.get();

		entityDao.delete(entity);

		return OptionMapper.from(entity);
	}

}
