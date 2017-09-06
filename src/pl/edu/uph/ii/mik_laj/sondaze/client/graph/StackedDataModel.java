package pl.edu.uph.ii.mik_laj.sondaze.client.graph;

import java.util.List;

/**
 * Uklasa wykresy stosowo (jeden na drugim)
 * @author andrzej
 *
 */
public class StackedDataModel implements DataModel {
	private final DataModel model;

	public StackedDataModel(DataModel model) {
		this.model = model;
	}

	public double getValue(int row, int column) {
		if (column == 0) {
			return model.getValue(row, column);
		}
		return this.getValue(row, column - 1) + model.getValue(row, column);
	}

	public int getRowCount() {
		return model.getRowCount();
	}

	public int getColumnCount() {
		return model.getColumnCount();
	}

	public void addZeroColumn() {
		model.addZeroColumn();
	}

	public void addRow(List<Integer> row) {
		model.addRow(row);
	}

	public void removeRow(int index) {
		model.removeRow(index);
	}

	public double getMaxValue() {
		if (getRowCount() == 0) {
			return 0;
		}
		double max = getValue(0, getColumnCount() - 1);
		for (int i = 1; i < getRowCount(); i++) {
			max = Math.max(max, getValue(i, getColumnCount() - 1));
		}
		return max;
	}

}
