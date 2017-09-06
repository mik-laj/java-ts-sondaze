package pl.edu.uph.ii.mik_laj.sondaze.server.dao;

import java.io.IOException;
import java.util.function.Function;

import pl.edu.uph.ii.mik_laj.sondaze.json.JsonParserException;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonElement;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.mapper.OptionMapper;

/**
 * Klasa dostepowa do zarządzania opcjami. Wykorzystuuje pliki w formacie JSON
 * @author andrzej
 *
 */
public class OptionDao extends BaseDao<Option> {

	public OptionDao(String filename) throws IOException, JsonParserException {
		super(filename);
	}

	protected Function<Option, ? extends JsonElement> getFromMapper() {
		return OptionMapper::from;
	}

	protected Function<? super JsonElement, Option> getToMapper() {
		return OptionMapper::to;
	}

	protected void copyFieldToUpdate(Option new_p, Option old_p) {
		old_p.setText(new_p.getText());
	}

}
