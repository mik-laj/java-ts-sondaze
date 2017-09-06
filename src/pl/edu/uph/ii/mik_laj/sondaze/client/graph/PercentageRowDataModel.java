package pl.edu.uph.ii.mik_laj.sondaze.client.graph;

import java.util.List;

/**
 * Przetwarza dane w modelu na wartosci procentowe
 * @author andrzej
 *
 */
public class PercentageRowDataModel implements DataModel {

	final DataModel model;

	public PercentageRowDataModel(DataModel model) {
		super();
		this.model = model;
	}

	public double getValue(int row, int column) {
		return ((model.getValue(row, column)) / getRowSum(row) * 100);
	}

	public double getRowSum(int row) {
		double s = 0;
		for (int i = 0; i < getColumnCount(); i++) {
			s += model.getValue(row, i);
		}
		return s;
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
		return 100;
	}

}
