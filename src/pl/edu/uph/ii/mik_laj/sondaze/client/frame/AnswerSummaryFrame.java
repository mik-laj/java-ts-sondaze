package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.Api;
import pl.edu.uph.ii.mik_laj.sondaze.client.graph.BaseDataModel;
import pl.edu.uph.ii.mik_laj.sondaze.client.graph.DataModel;
import pl.edu.uph.ii.mik_laj.sondaze.client.graph.LimitedDataModel;
import pl.edu.uph.ii.mik_laj.sondaze.client.graph.LineChartPanel;
import pl.edu.uph.ii.mik_laj.sondaze.client.graph.PercentageRowDataModel;
import pl.edu.uph.ii.mik_laj.sondaze.client.graph.StackedDataModel;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.AnswerSummary;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import javax.swing.JCheckBox;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * Wyswietla wykres z podsumowanie
 * 
 * @author andrzej
 *
 */
public class AnswerSummaryFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private LineChartPanel chartPanel;
	private JCheckBox chckbxLimited;
	private SpinnerModel spinnerModelLimit;
	private JCheckBox chckbxStack;
	private JCheckBox chckbxPercentage;
	private Api api;
	private Poll poll;

	/**
	 * Uruchamia okno
	 */
	public static void startFrame(Api api, Poll poll) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnswerSummaryFrame frame = new AnswerSummaryFrame(api, poll);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Tworzy oknp
	 */
	public AnswerSummaryFrame(Api api, Poll poll) {
		this.api = api;
		this.poll = poll;
		createUi();

		rebuildModel();
		// Random r = new Random();
		Timer timer = new Timer(500, ev -> {
			fetchAnswerSummary();
			// chartPanel.getModel().addRow(Arrays.asList(r.nextInt(10),
			// r.nextInt(10), r.nextInt(10)));
			// chartPanel.repaint();
		}); // fire every half second
		timer.setRepeats(true);
		timer.start();
	}
	
	/**
	 * Pobiera informacje z serwera i dodaje do wykresu
	 */
	private void fetchAnswerSummary() {
		List<AnswerSummary> summaryList;
		try {
			summaryList = api.getPollService().getAnswerService(poll.getId()).getAnswerSummary();

			List<Integer> row = summaryList.stream()//
					.sorted((a, b) -> a.getOptionId() - b.getOptionId())//
					.map(t -> t.getCount())//
					.collect(Collectors.toList());//
			chartPanel.getModel().addRow(row);
			chartPanel.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Tworzy interfejs graficzny
	 */
	private void createUi() {
		setTitle("Chart");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		chartPanel = new LineChartPanel();
		panel.add(chartPanel);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 77, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 23, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		chckbxLimited = new JCheckBox("Limited");
		GridBagConstraints gbc_chckbxLimited = new GridBagConstraints();
		gbc_chckbxLimited.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxLimited.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxLimited.gridx = 0;
		gbc_chckbxLimited.gridy = 0;
		panel_1.add(chckbxLimited, gbc_chckbxLimited);
		chckbxLimited.addActionListener(ev -> rebuildModel());
		spinnerModelLimit = new SpinnerNumberModel(10, 1, 50, 1);
		spinnerModelLimit.addChangeListener(ev -> rebuildModel());
		JSpinner spinner = new JSpinner(spinnerModelLimit);
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinner.insets = new Insets(0, 0, 5, 0);
		gbc_spinner.gridx = 0;
		gbc_spinner.gridy = 1;
		panel_1.add(spinner, gbc_spinner);

		chckbxStack = new JCheckBox("Stack");
		GridBagConstraints gbc_chckbxStack = new GridBagConstraints();
		gbc_chckbxStack.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxStack.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxStack.gridx = 0;
		gbc_chckbxStack.gridy = 2;
		panel_1.add(chckbxStack, gbc_chckbxStack);
		chckbxStack.addChangeListener(ev -> rebuildModel());

		chckbxPercentage = new JCheckBox("Percentage");
		GridBagConstraints gbc_chckbxPercentage = new GridBagConstraints();
		gbc_chckbxPercentage.anchor = GridBagConstraints.NORTHWEST;
		gbc_chckbxPercentage.gridx = 0;
		gbc_chckbxPercentage.gridy = 3;
		panel_1.add(chckbxPercentage, gbc_chckbxPercentage);
		chckbxPercentage.addChangeListener(ev -> rebuildModel());
	}

	/**
	 * Tworzy model na podstawie wybranych opcji np. limit
	 */
	private void rebuildModel() {
		DataModel model = new BaseDataModel();
		if (chckbxLimited.isSelected()) {
			int rowLimit = (int) spinnerModelLimit.getValue();
			model = new LimitedDataModel(model, rowLimit);
		}
		if (chckbxStack.isSelected()) {
			model = new StackedDataModel(model);
		}
		if (chckbxPercentage.isSelected()) {
			model = new PercentageRowDataModel(model);
		}
		chartPanel.setModel(model);
	}

}
