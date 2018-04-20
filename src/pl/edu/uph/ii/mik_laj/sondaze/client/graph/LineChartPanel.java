package pl.edu.uph.ii.mik_laj.sondaze.client.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 * Panel umozlowiajaca narysowanie wykresu
 * @author andrzej
 *
 */
public class LineChartPanel extends JPanel {

	private static final int PREF_W = 800;
	private static final int PREF_H = 800;
	private static final int BORDER_GAP = 30;
	private static final Color GRAPH_COLOR = Color.green;
	private static final Color GRAPH_POINT_COLOR = new Color(70, 90, 70, 180);
	private static final Stroke GRAPH_STROKE = new BasicStroke(5f);
	private static final int GRAPH_POINT_WIDTH = 12;
	private static final int Y_HATCH_CNT = 10;
	private DataModel model;

	public LineChartPanel() {
	}

	public DataModel getModel() {
		return model;
	}

	public void setModel(DataModel model) {
		this.model = model;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double max_score = model.getMaxValue() + 1;
		double xScale = ((double) getWidth() - 2 * BORDER_GAP) / (model.getRowCount() - 1);
		double yScale = ((double) getHeight() - 2 * BORDER_GAP) / (max_score - 1);

		List<List<Point>> linesPoints = new ArrayList<>();
		for (int j = 0; j < model.getColumnCount(); j++) {

			ArrayList<Point> linePoint = new ArrayList<>();
			for (int i = 0; i < model.getRowCount(); i++) {
				int x1 = (int) (i * xScale + BORDER_GAP);
				int y1 = (int) ((max_score - (model.getValue(i, j) + 1)) * yScale + BORDER_GAP);
				linePoint.add(new Point(x1, y1));
			}
			linesPoints.add(linePoint);
		}

		// create x and y axes
		g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
		g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

		// create hatch marks for y axis.
		for (int i = 0; i < Y_HATCH_CNT; i++) {
			int x0 = BORDER_GAP;
			int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
			int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);
			double value = (max_score - 1) / Y_HATCH_CNT * (i + 1);
			g2.drawString(String.format("%.3f", value), x1, y1);
		}

		// and for x axis
		for (int i = 0; i < model.getRowCount() - 1; i++) {
			int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (model.getRowCount() - 1) + BORDER_GAP;
			int x1 = x0;
			int y0 = getHeight() - BORDER_GAP;
			int y1 = y0 - GRAPH_POINT_WIDTH;
			g2.drawLine(x0, y0, x1, y1);
		}

		Stroke oldStroke = g2.getStroke();
		g2.setColor(GRAPH_COLOR);
		g2.setStroke(GRAPH_STROKE);
		for (List<Point> linePoints : linesPoints) {
			for (int i = 0; i < linePoints.size() - 1; i++) {
				int x1 = linePoints.get(i).x;
				int y1 = linePoints.get(i).y;
				int x2 = linePoints.get(i + 1).x;
				int y2 = linePoints.get(i + 1).y;
				g2.drawLine(x1, y1, x2, y2);
			}
		}

		g2.setStroke(oldStroke);
		g2.setColor(GRAPH_POINT_COLOR);
		for (List<Point> linePoints : linesPoints) {
			for (int i = 0; i < linePoints.size(); i++) {
				int x = linePoints.get(i).x - GRAPH_POINT_WIDTH / 2;
				int y = linePoints.get(i).y - GRAPH_POINT_WIDTH / 2;

				int ovalW = GRAPH_POINT_WIDTH;
				int ovalH = GRAPH_POINT_WIDTH;
				g2.fillOval(x, y, ovalW, ovalH);
			}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PREF_W, PREF_H);
	}

	private static void createAndShowGui() {

		LineChartPanel mainPanel = new LineChartPanel();
		mainPanel.model = new StackedDataModel(
				new PercentageRowDataModel(new LimitedDataModel(new BaseDataModel(), 10)));
		// mainPanel.scores = new PercentageRowDataModel(new
		// LimitedDataModel(new BaseDataModel(), 10));
		mainPanel.model.addRow(Arrays.asList(1, 2, 3));
		mainPanel.model.addRow(Arrays.asList(10, 10, 10));
		// mainPanel.scores.addRow(Arrays.asList(1, 2, 2));

		Random r = new Random();
		Timer timer = new Timer(500, ev -> {
			mainPanel.model.addRow(Arrays.asList(r.nextInt(10), r.nextInt(10), r.nextInt(10)));
			mainPanel.repaint();
		}); // fire every half second
		timer.setRepeats(true);
		timer.start();

		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}
