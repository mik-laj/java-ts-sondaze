package pl.edu.uph.ii.mik_laj.sondaze.server;

import java.io.IOException;

import pl.edu.uph.ii.mik_laj.sondaze.server.actions.RootEndpointAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.answer.GetAnswerListAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.answer.GetAnswerSummaryListAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.answer.PostAnswerAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.option.DeleteOptionAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.option.GetOptionAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.option.GetOptionListAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.option.PatchOptionAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.option.PostOptionAction;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.poll.*;
import pl.edu.uph.ii.mik_laj.sondaze.server.actions.user.*;
import pl.edu.uph.ii.mik_laj.sondaze.server.auth.BasicAuthorizationMiddleware;
import pl.edu.uph.ii.mik_laj.sondaze.server.auth.CurrentUserStorage;
import pl.edu.uph.ii.mik_laj.sondaze.server.core.App;
import pl.edu.uph.ii.mik_laj.sondaze.server.dao.*;

/**
 * Glowny plik serwoery
 * 
 * @author andrzej
 *
 */
public class MyApp extends App {

	protected CurrentUserStorage user_storage = new CurrentUserStorage();
	private UserDao userDao;
	private PollDao pollDao;
	private AnswerDao answerDao;
	private OptionDao optionDao;

	public MyApp() {
		super();

		configureDao();
		configureMiddleware();
		configureActions();

	}

	/**
	 * Konfigure obiekty dostępu do danych
	 */
	private void configureDao() {
		try {
			this.userDao = new UserDao("user.json");
			this.optionDao = new OptionDao("option.json");
			this.pollDao = new PollDao("poll.json", optionDao);
			this.answerDao = new AnswerDao("answer.json");
		} catch (IOException ex) {
			System.out.println(ex);
			throw new RuntimeException("Unable to create database");
		}
	}

	/**
	 * Konfigurje funckje pośredniczace
	 */
	private void configureMiddleware() {
		addMiddleware(new BasicAuthorizationMiddleware(user_storage, userDao));
	}

	/**
	 * Konfiguruje akcje dostepne w aplikacji
	 */
	private void configureActions() {
		addAction(new RootEndpointAction());

		// /user/
		addAction(new GetUserListAction(userDao));
		addAction(new GetUserAction(userDao));
		addAction(new AuthorizedAction(new GetUserMeAction(user_storage), user_storage));

		addAction(new AdminAction(new PostUserAction(userDao), user_storage));
		addAction(new AdminAction(new DeleteUserAction(userDao), user_storage));
		addAction(new AdminAction(new PatchUserAction(userDao), user_storage));

		// /poll
		addAction(new GetPollListAction(pollDao));
		addAction(new GetPollAction(pollDao));
		addAction(new AdminAction(new PostPollAction(pollDao), user_storage));
		addAction(new AdminAction(new DeletePollAction(pollDao), user_storage));
		addAction(new AdminAction(new PatchPollAction(pollDao), user_storage));

		// /poll/ /option/
		addAction(new GetOptionListAction(optionDao));
		addAction(new GetOptionAction(optionDao));
		addAction(new AdminAction(new PostOptionAction(optionDao), user_storage));
		addAction(new AdminAction(new DeleteOptionAction(optionDao), user_storage));
		addAction(new AdminAction(new PatchOptionAction(optionDao), user_storage));

		// /poll/ /answer/
		addAction(new AdminAction(new GetAnswerListAction(answerDao, optionDao), user_storage));
		addAction(new PostAnswerAction(answerDao, optionDao, user_storage));
		addAction(new AdminAction(new GetAnswerSummaryListAction(answerDao, optionDao), user_storage));
	}

}
