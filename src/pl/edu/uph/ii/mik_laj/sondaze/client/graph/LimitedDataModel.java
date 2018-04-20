package pl.edu.uph.ii.mik_laj.sondaze.client.graph;

import java.util.List;

/**
 * Klasa ograniczajaca ilosc przechowywanych elementow do `rowLimit`. W przypadku dodania nowych elementow najstarsze elementy sa kasowane
 * @author andrzej
 *
 */
public class LimitedDataModel implements DataModel {
	private final DataModel model;
	private int rowLimit;

	public LimitedDataModel(DataModel model, int rowLimit) {
		this.rowLimit = rowLimit;
		this.model = model;
	}

	public double getValue(int row, int column) {
		return model.getValue(row, column);
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
		if (getRowCount() > rowLimit) {
			this.removeRow(0);
		}
	}

	public void removeRow(int index) {
		model.removeRow(index);
	}

	public double getMaxValue() {
		return model.getMaxValue();
	}

}
