package pl.edu.uph.ii.mik_laj.sondaze.client.api.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.ApiClient;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.UserMapper;

/**
 * Umozliwia zarzadzanie uzytkownikami
 * @author andrzej
 *
 */
public class UserService extends ApiService {

	public UserService() {
		super();
	}

	public UserService(ApiClient client) {
		super(client);
	}

	/**
	 * Pobiera wszystkich uzytkownikow
	 * @return
	 * @throws IOException
	 */
	public List<User> getUsers() throws IOException {
		JsonArray r = (JsonArray) this.client.get("user/");
		return mapEntityCollection(r);
	}

	/**
	 * Pobiera aktualnie zautoryzowanego uzytkownika
	 * @return
	 * @throws IOException
	 */
	public User getMe() throws IOException {
		JsonElement r = this.client.get("user/me/");
		return mapEntity(r);
	}

	/**
	 * Pobiera uzytkownika o wskazanym id
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public User getUser(int id) throws IOException {
		JsonElement r = this.client.get("user/" + id + "/");
		return mapEntity(r);
	}

	/**
	 * Tworzy uzytkownika
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 */
	public User createUser(User user) throws IOException {
		JsonObject json = UserMapper.from(user);
		JsonElement r = this.client.post("user/", json);
		return mapEntity(r);
	}
	
	
	/**
	 * Aktualizuje uzytkownika
	 * @param id
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public User updateUser(int id, JsonArray json) throws IOException {
		JsonElement r = this.client.patch("user/" + id + "/", json);
		return mapEntity(r);
	}
	

	/**
	 * Kasuje uzytkownika
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public User deleteUser(int id) throws IOException {
		JsonElement r = this.client.delete("user/" + id + "/");
		return mapEntity(r);
	}

	/**
	 * Przetwarza element JSON na natywny obiekt
	 * @param r
	 * @return
	 */
	private User mapEntity(JsonElement r) {
		return UserMapper.to(r);
	}

	/**
	 * Przetwarza kolekcje obiektow na kolekcje natywnych obiektow
	 * @param r
	 * @return
	 */
	private List<User> mapEntityCollection(JsonArray r) {
		return r.stream().map(UserMapper::to).collect(Collectors.toList());
	}
}
