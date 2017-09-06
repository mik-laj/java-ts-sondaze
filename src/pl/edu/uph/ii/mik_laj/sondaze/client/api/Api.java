package pl.edu.uph.ii.mik_laj.sondaze.client.api;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.services.PollService;
import pl.edu.uph.ii.mik_laj.sondaze.client.api.services.UserService;

/**
 * Odpowiada za obsługę API. Jest to pomoc do innych usług. 
 * @author andrzej
 *
 */
public class Api {

	protected final ApiClient client;

	public Api() {
		this.client = ApiClient.factoryDefault();
	}

	public Api(ApiClient client) {
		this.client = client;
	}

	public ApiClient getClient() {
		return client;
	}

	public UserService getUserService() {
		return new UserService(this.client);
	}

	public PollService getPollService() {
		return new PollService(this.client);
	}

}
