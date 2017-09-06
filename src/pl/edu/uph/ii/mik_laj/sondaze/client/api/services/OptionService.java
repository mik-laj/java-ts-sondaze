package pl.edu.uph.ii.mik_laj.sondaze.client.api.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.ApiClient;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.OptionMapper;

/**
 * Usługa umożliwiająca zarzadzanie opcjami
 * @author andrzej
 *
 */
public class OptionService extends ApiService {

	/**
	 * Identyfikator ankiety, ktorej instancji usługi dotyczy
	 */
	private final int pollId;

	public OptionService(int pollId) {
		super();
		this.pollId = pollId;
	}

	public OptionService(ApiClient client, int pollId) {
		super(client);
		this.pollId = pollId;
	}

	/**
	 * Pobiera wszystkie opcje dla konkretnego pollId
	 * @return
	 * @throws IOException
	 */
	public List<Option> getOptions() throws IOException {
		JsonArray r = (JsonArray) this.client.get("poll/" + this.pollId + "/option/");
		return r.stream().map(OptionMapper::to).collect(Collectors.toList());
	}

	/**
	 * Pobiera konkretna opcje 
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public Option getOption(int id) throws IOException {
		JsonElement r = this.client.get(createUpdateUri(id));
		return OptionMapper.to(r);
	}

	/**
	 * Tworzy nowa opcje
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Option createOption(JsonObject json) throws IOException {
		JsonElement r = this.client.post("poll/" + this.pollId + "/option/", json);
		return OptionMapper.to(r);
	}

	/**
	 * Aktualizuje opcje
	 * @param id
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Option updateOption(int id, JsonObject json) throws IOException {
		JsonElement r = this.client.patch(createUpdateUri(id), json);
		return OptionMapper.to(r);
	}

	/**
	 * Kasuje obiekt o wskazanym id
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public Option deleteOption(int id) throws IOException {
		JsonElement r = this.client.delete(createUpdateUri(id));
		return OptionMapper.to(r);
	}

	/**
	 * Tworzy adres Uri konrentego elementu
	 * @param id
	 * @return
	 */
	private String createUpdateUri(int id) {
		return "poll/" + this.pollId + "/option/" + id + "/";
	}
}
