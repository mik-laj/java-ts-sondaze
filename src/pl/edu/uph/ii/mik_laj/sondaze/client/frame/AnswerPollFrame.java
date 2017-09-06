package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.Api;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonNumber;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;

/**
 * Umozliwia udzielenie odpowiedzi na ankiete
 * @author andrzej
 *
 */
public class AnswerPollFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Api api;
	private Poll poll;
	private JPanel mainList;

	/**
	 * Uruuchamia okno
	 */
	public static void startFrame(Api api, Poll poll) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AnswerPollFrame frame = new AnswerPollFrame(api, poll);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Tworzy okno.
	 */
	public AnswerPollFrame(Api api, Poll poll) {
		this.api = api;
		this.poll = poll;

		createUi();
		fetchOptions();
	}

	/**
	 * Tworzy interfejs
	 */
	private void createUi() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		mainList = new JPanel();
		scrollPane.setViewportView(mainList);
		GridBagLayout gbl_mainList = new GridBagLayout();
		gbl_mainList.columnWidths = new int[] { 10, 0 };
		gbl_mainList.rowHeights = new int[] { 10, 0 };
		gbl_mainList.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_mainList.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		mainList.setLayout(gbl_mainList);
	}

	/**
	 * Laczy się z api i pobiera element aktualizujaca je na UI
	 */
	void fetchOptions() {

		try {
			List<Option> options = api.getPollService().getOptionService(poll.getId()).getOptions();

			mainList.removeAll();
			for (Option option : options) {
				JPanel rowItem = new JPanel();
				GridBagConstraints gbc_rowItem = new GridBagConstraints();
				gbc_rowItem.fill = GridBagConstraints.HORIZONTAL;
				gbc_rowItem.gridx = 0;
				mainList.add(rowItem, gbc_rowItem);
				rowItem.setBorder(new EmptyBorder(5, 5, 5, 5));
				GridBagLayout gbl_rowItem = new GridBagLayout();
				gbl_rowItem.columnWidths = new int[] { 0, 0 };
				gbl_rowItem.rowHeights = new int[] { 25, 0 };
				gbl_rowItem.columnWeights = new double[] { 1.0, 0.0 };
				gbl_rowItem.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
				rowItem.setLayout(gbl_rowItem);

				JLabel lblText = new JLabel();
				lblText.setText(option.getText());
				lblText.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_lblText = new GridBagConstraints();
				gbc_lblText.fill = GridBagConstraints.VERTICAL;
				gbc_lblText.insets = new Insets(0, 0, 0, 5);
				gbc_lblText.gridx = 0;
				gbc_lblText.gridy = 0;
				rowItem.add(lblText, gbc_lblText);

				JButton btnVote = new JButton("Vote");
				btnVote.addActionListener(ev -> onVoteButton(option));
				GridBagConstraints gbc_btnVote = new GridBagConstraints();
				gbc_btnVote.gridx = 1;
				gbc_btnVote.gridy = 0;
				rowItem.add(btnVote, gbc_btnVote);
			}
			contentPane.validate();
			contentPane.repaint();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Wysyla zapytanie do serwera, aby zapisac wybrana opcje
	 * @param option
	 */
	private void onVoteButton(Option option) {
		JsonObject json = new JsonObject();
		json.addElement("option_id", new JsonNumber(option.getId()));
		try {
			this.api.getPollService().getAnswerService(poll.getId()).createAnswer(json);
			JOptionPane.showMessageDialog(this, "Thank you");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dispose();
	}

}
