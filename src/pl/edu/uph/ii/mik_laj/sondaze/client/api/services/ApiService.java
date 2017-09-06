package pl.edu.uph.ii.mik_laj.sondaze.client.api.services;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.ApiClient;

/**
 * Klasa bazowa dla wszystkich innych usług API
 * @author andrzej
 *
 */
public abstract class ApiService {

	protected final ApiClient client;

	public ApiService() {
		this(ApiClient.factoryDefault());
	}

	public ApiService(ApiClient client) {
		this.client = client;
	}

}
