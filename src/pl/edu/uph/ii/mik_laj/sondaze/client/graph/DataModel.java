package pl.edu.uph.ii.mik_laj.sondaze.client.graph;

import java.util.List;

/**
 * Interfejs modelu dla wykresu
 * @author andrzej
 *
 */
public interface DataModel {

	/**
	 * Pobiera wartosc elementu w danym wierszu i kolumnie
	 * @param row
	 * @param column
	 * @return
	 */
	double getValue(int row, int column);

	/**
	 * Pobiera ilosc wierszy
	 * @return ilosc wierszy
	 */
	int getRowCount();

	/**
	 * Pobiera ilosc kolumn
	 * 
	 * @return ilosc kolumn
	 */
	int getColumnCount();
	
	
	/**
	 * Dodaje pusta kolumne na koncu
	 */
	void addZeroColumn();

	/**
	 * Dodaje nowy wiersz. Ilosc elementow w tablicy musi byc zgodna z `getColumnCount()`
	 * @param row
	 */
	void addRow(List<Integer> row);

	/**
	 * Kasuje wiersz z danej pozycji
	 * @param index
	 */
	void removeRow(int index);

	/**
	 * Pobiera maksymalna, ktora jest przechowywana w modelu
	 * @return
	 */
	double getMaxValue();

}