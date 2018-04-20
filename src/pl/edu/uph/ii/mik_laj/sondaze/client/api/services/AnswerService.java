package pl.edu.uph.ii.mik_laj.sondaze.client.api.services;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.ApiClient;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonArray;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Answer;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.AnswerSummary;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.AnswerMapper;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.AnswerSummaryMapper;

/*
 * Usługa API, która umożliwia zarządzanie pytaniami
 */
public class AnswerService extends ApiService {

	private final int pollId;

	public AnswerService(int pollId) {
		super();
		this.pollId = pollId;
	}

	public AnswerService(ApiClient client, int pollId) {
		super(client);
		this.pollId = pollId;
	}

	/**
	 * Pobiera listę wszystkich zapytań dla `pollId`
	 * @return
	 * @throws IOException
	 */
	public List<Answer> getAnswer() throws IOException {
		JsonArray r = (JsonArray) this.client.get("poll/" + this.pollId + "/answer/");
		return r.stream().map(AnswerMapper::to).collect(Collectors.toList());
	}

	/**
	 * Pobiera podsumowanie odpowiedzi dla konkretnego `pollId`
	 * @return
	 * @throws IOException
	 */
	public List<AnswerSummary> getAnswerSummary() throws IOException {
		JsonArray r = (JsonArray) this.client.get("poll/" + this.pollId + "/answer/summary/");
		return r.stream().map(AnswerSummaryMapper::to).collect(Collectors.toList());
	}
	
	/**
	 * Wysyła zapytanie, aby zapisać odpowiedz uzytkownika
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public Answer createAnswer(JsonObject json) throws IOException {
		JsonElement r = this.client.post("poll/" + this.pollId + "/answer/", json);
		return AnswerMapper.to(r);
	}

}
