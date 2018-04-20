package pl.edu.uph.ii.mik_laj.sondaze.client.api.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.ApiClient;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.PollMapper;

/**
 * Usługa umożliwajaca zarządzaie ankitami
 * @author andrzej
 *
 */
public class PollService extends ApiService {

	public PollService() {
		super();
	}

	public PollService(ApiClient client) {
		super(client);
	}

	/**
	 * Pobiera liste ankiet
	 * @return
	 * @throws IOException
	 */
	public List<Poll> getPolls() throws IOException {
		JsonArray r = (JsonArray) this.client.get("poll/");
		return r.stream().map(PollMapper::to).collect(Collectors.toList());
	}

	/**
	 * Pobiera ankiete o wskazanym id
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public Poll getPoll(int id) throws IOException {
		JsonElement r = this.client.get(createUpdateUri(id));
		return PollMapper.to(r);
	}

	/**
	 * Tworzy ankiete
	 * 
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Poll createPoll(JsonObject json) throws IOException {
		JsonElement r = this.client.post("poll/", json);
		return PollMapper.to(r);
	}

	/**
	 * Aktualizuje ankiete
	 * 
	 * @param id
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Poll updatePoll(int id, JsonObject json) throws IOException {
		JsonElement r = this.client.patch(createUpdateUri(id), json);
		return PollMapper.to(r);
	}

	/**
	 * Kasuje ankiete o wskazanym id
	 * @param id
	 * @return
	 * @throws IOException
	 */
	public Poll deletePoll(int id) throws IOException {
		JsonElement r = this.client.delete(createUpdateUri(id));
		return PollMapper.to(r);
	}

	/**
	 * Tworzy adres Uri do konreketnego elementu
	 * @param id
	 * @return
	 */
	private String createUpdateUri(int id) {
		return "poll/" + id + "/";
	}
	

	/**
	 * Tworzy uslugue umozliwiajaca zarzania opcjami 
	 * @param pollId
	 * @return
	 */
	public OptionService getOptionService(int pollId) {
		return new OptionService(this.client, pollId);
	}
	
	/**
	 * Tworzy ankiete, ktora umozliwia zarzanie odpowiedziami
	 * @param pollId
	 * @return
	 */
	public AnswerService getAnswerService(int pollId) {
		return new AnswerService(this.client, pollId);
	}
	
	
	

}
