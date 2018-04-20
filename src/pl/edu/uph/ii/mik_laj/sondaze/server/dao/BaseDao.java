package pl.edu.uph.ii.mik_laj.sondaze.server.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Function;
import java.util.stream.Collectors;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonParserException;
import pl.edu.uph.ii.mik_laj.sondaze.json.JsonReader;
import pl.edu.uph.ii.mik_laj.sondaze.json.JsonWriter;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;

/**
 * Bazowa klasa dostepowa. Wykorzystuuje pliki w formacie JSON
 * @author andrzej
 *
 */
public abstract class BaseDao<T extends HaveIdInterface> {

	protected String filename;
	protected List<T> data;

	public BaseDao(String filename) throws IOException, JsonParserException {
		this.filename = filename;
		this.data = readFromDrive();
	}

	protected List<T> readFromDrive() throws IOException {
		JsonReader reader = new JsonReader(new FileInputStream(filename));
		List<T> r = reader.readArray().toList().stream().map(getToMapper()).collect(Collectors.toList());
		reader.close();
		return r;
	}

	protected abstract Function<T, ? extends JsonElement> getFromMapper();

	protected abstract Function<? super JsonElement, T> getToMapper();

	private void saveToDrive() throws IOException {
		JsonWriter writer = new JsonWriter(new FileOutputStream(filename));
		List<JsonElement> json_data = data.stream().map(getFromMapper()).collect(Collectors.toList());
		writer.writeObject(json_data);
		writer.close();
	}

	public List<T> getAll() {
		return Collections.unmodifiableList(data);
	}

	public Optional<T> get(int id) {
		return data.stream().filter(t -> t.getId() == id).findFirst();
	}

	public void create(T p) throws IOException {
		if (p.getId() != 0) {
			throw new RuntimeException("Only new object can be saved");
		}
		p.setId(getMaxId() + 1);
		data.add(p);
		saveToDrive();
	}

	public void update(T p) throws IOException {
		if (p.getId() == 0) {
			throw new RuntimeException("Id cann't be 0");
		}
		Optional<T> p2 = get(p.getId());
		if (!p2.isPresent()) {
			throw new IOException("You must save object before update");
		}
		copyFieldToUpdate(p, p2.get());
		saveToDrive();
	}

	protected abstract void copyFieldToUpdate(T new_p, T old_p);

	public void delete(T p) throws IOException {
		if (p.getId() == 0) {
			throw new RuntimeException("Id cann't be 0");
		}
		Optional<T> p2 = get(p.getId());
		if (!p2.isPresent()) {
			throw new IOException("You must save object before delete");
		}
		data.remove(p2.get());
		saveToDrive();
	}

	public int getMaxId() {
		OptionalInt max_id = data.stream().mapToInt(HaveIdInterface::getId).max();
		if (max_id.isPresent()) {
			return max_id.getAsInt();
		}
		return 0;
	}

}