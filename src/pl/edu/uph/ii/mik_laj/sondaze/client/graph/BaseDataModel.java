package pl.edu.uph.ii.mik_laj.sondaze.client.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa bazowa dla obiektu modelu dla wykresu
 * @author andrzej
 *
 */
public class BaseDataModel implements DataModel {
	List<List<Integer>> data = new ArrayList<>();


	@Override
	public double getValue(int row, int column) {
		return data.get(row).get(column);
	}


	@Override
	public int getRowCount() {
		return data.size();
	}


	@Override
	public int getColumnCount() {
		if (getRowCount() == 0) {
			return 0;
		}
		return data.get(0).size();
	}


	@Override
	public void addZeroColumn() {
		for (int i = 0; i < getRowCount(); i++) {
			data.get(i).add(0);
		}
	}

	@Override
	public void addRow(List<Integer> row) {
		if (getRowCount() == 0) {
			this.data.add(row);
			return;
		}

		if (row.size() != getColumnCount()) {
			throw new RuntimeException();
		}
		this.data.add(row);
	}

	@Override
	public void removeRow(int index) {
		this.data.remove(0);
	}

	@Override
	public double getMaxValue() {
		return this.data.stream().flatMapToDouble(t -> t.stream().mapToDouble(i -> i)).max().orElse(0);
	}
}