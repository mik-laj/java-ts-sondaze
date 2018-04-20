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
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.Api;
import pl.edu.uph.ii.mik_laj.sondaze.client.api.services.OptionService;
import pl.edu.uph.ii.mik_laj.sondaze.client.api.services.PollService;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.AnswerSummary;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Option;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;

/**
 * Okno edycji ankiety
 * @author andrzej
 *
 */
public class EditPollFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnDeletePoll;
	private JButton btnEditText;
	private JLabel lblText;
	private JPanel mainList;
	private Api api;
	private Poll poll;
	private JButton btnAddOption;
	private JButton btnSummary;

	/**
	 * Launch the application.
	 */
	public static void startFrame(Api api, Poll poll) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditPollFrame frame = new EditPollFrame(api, poll);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EditPollFrame(Api api, Poll poll) {
		this.api = api;
		this.poll = poll;

		createUi();

		lblText.setText(poll.getText());
		btnDeletePoll.addActionListener(t -> onDeletePoll());
		btnEditText.addActionListener(t -> onEditText());
		btnAddOption.addActionListener(d -> onAddOption());
		btnSummary.addActionListener(ev-> onSummaryButton());
		fetchOptions();
	}

	private void onSummaryButton() {
		AnswerSummaryFrame.startFrame(api, poll);
	}

	private void onEditText() {
		String newText = JOptionPane.showInputDialog("What would you like to name?");
		if (newText != null) {
			try {
				JsonObject j = new JsonObject();
				j.addElement("text", new JsonString(newText));
				api.getPollService().updatePoll(poll.getId(), j);
				lblText.setText(newText);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onDeletePoll() {
		int option = JOptionPane.showConfirmDialog(this, "Would you like delete a poll?", "Delete poll",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			try {
				api.getPollService().deletePoll(poll.getId());
				setVisible(false);
				dispose();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onDeleteOption(Option option) {
		int selection = JOptionPane.showConfirmDialog(this, "Would you like delete a option?", "Delete option",
				JOptionPane.YES_NO_OPTION);
		if (selection == JOptionPane.YES_OPTION) {
			try {
				int pollId = this.poll.getId();
				int optionId = option.getId();
				PollService pollService = api.getPollService();
				OptionService optionService = pollService.getOptionService(pollId);
				optionService.deleteOption(optionId);
				fetchOptions();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void onEditOption(Option option) {
		String newText = JOptionPane.showInputDialog("What would you like to name?");
		if (newText != null) {
			try {
				JsonObject j = new JsonObject();
				j.addElement("text", new JsonString(newText));
				int pollId = this.poll.getId();
				int optionId = option.getId();
				PollService pollService = api.getPollService();
				OptionService optionService = pollService.getOptionService(pollId);
				optionService.updateOption(optionId, j);
				fetchOptions();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createUi() {
		setTitle("Edit poll");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Text", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.NORTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 335, 75, 0 };
		gbl_panel.rowHeights = new int[] { 25, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		lblText = new JLabel("New label");
		GridBagConstraints gbc_lblText = new GridBagConstraints();
		gbc_lblText.anchor = GridBagConstraints.WEST;
		gbc_lblText.insets = new Insets(0, 0, 0, 5);
		gbc_lblText.gridx = 0;
		gbc_lblText.gridy = 0;
		panel.add(lblText, gbc_lblText);

		btnEditText = new JButton("Edit");
		GridBagConstraints gbc_btnEditText = new GridBagConstraints();
		gbc_btnEditText.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnEditText.gridx = 1;
		gbc_btnEditText.gridy = 0;
		panel.add(btnEditText, gbc_btnEditText);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		btnDeletePoll = new JButton("Delete poll");
		panel_1.add(btnDeletePoll);

		btnAddOption = new JButton("Add Option");
		panel_1.add(btnAddOption);
		
		btnSummary = new JButton("Summary");
		panel_1.add(btnSummary);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Option", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		mainList = new JPanel();
		scrollPane.setViewportView(mainList);
		GridBagLayout gbl_mainList = new GridBagLayout();
		gbl_mainList.columnWidths = new int[] { 400, 0 };
		gbl_mainList.rowHeights = new int[] { 25, 0 };
		gbl_mainList.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_mainList.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		mainList.setLayout(gbl_mainList);
	}

	private void onAddOption() {
		String newText = JOptionPane.showInputDialog("What would you like to name?");
		if (newText != null) {
			try {
				JsonObject j = new JsonObject();
				j.addElement("text", new JsonString(newText));
				int pollId = this.poll.getId();
				PollService pollService = api.getPollService();
				OptionService optionService = pollService.getOptionService(pollId);
				optionService.createOption(j);
				fetchOptions();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void fetchOptions() {

		int pollId = this.poll.getId();
		PollService pollService = api.getPollService();
		OptionService optionService = pollService.getOptionService(pollId);
		mainList.removeAll();
		try {
			List<Option> options = optionService.getOptions();
			for (Option option : options) {
				JPanel row_item = new JPanel();
				row_item.setBorder(new EmptyBorder(5, 0, 5, 0));
				GridBagConstraints gbc_row_item = new GridBagConstraints();
				// gbc_row_item.fill = GridBagConstraints.HORIZONTAL;
				// gbc_row_item.anchor = GridBagConstraints.NORTH;
				gbc_row_item.gridx = 0;
				mainList.add(row_item, gbc_row_item);
				GridBagLayout gbl_row_item = new GridBagLayout();
				gbl_row_item.columnWidths = new int[] { 220, 75, 75, 0 };
				gbl_row_item.rowHeights = new int[] { 25, 0 };
				gbc_row_item.anchor = GridBagConstraints.NORTH;
				gbl_row_item.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
				gbl_row_item.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
				row_item.setLayout(gbl_row_item);

				JLabel lblOption = new JLabel("New label");
				lblOption.setText(option.getText());
				GridBagConstraints gbc_lblOption = new GridBagConstraints();
				gbc_lblOption.fill = GridBagConstraints.BOTH;
				gbc_lblOption.insets = new Insets(0, 0, 0, 5);
				gbc_lblOption.gridx = 0;
				gbc_lblOption.gridy = 0;
				row_item.add(lblOption, gbc_lblOption);

				JButton btnEditOption = new JButton("Edit");
				btnEditOption.addActionListener(ev -> onEditOption(option));
				GridBagConstraints gbc_btnEditOption = new GridBagConstraints();
				gbc_btnEditOption.fill = GridBagConstraints.BOTH;
				gbc_btnEditOption.insets = new Insets(0, 0, 0, 5);
				gbc_btnEditOption.gridx = 1;
				gbc_btnEditOption.gridy = 0;
				row_item.add(btnEditOption, gbc_btnEditOption);

				JButton btnDeleteOption = new JButton("Delete");
				btnDeleteOption.addActionListener(ev -> onDeleteOption(option));
				GridBagConstraints gbc_btnDeleteOption = new GridBagConstraints();
				gbc_btnDeleteOption.fill = GridBagConstraints.BOTH;
				gbc_btnDeleteOption.gridx = 2;
				gbc_btnDeleteOption.gridy = 0;
				row_item.add(btnDeleteOption, gbc_btnDeleteOption);
			}
			contentPane.validate();
			contentPane.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
