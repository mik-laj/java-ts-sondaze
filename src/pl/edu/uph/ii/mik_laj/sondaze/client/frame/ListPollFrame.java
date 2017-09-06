package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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

import pl.edu.uph.ii.mik_laj.sondaze.client.api.Api;
import pl.edu.uph.ii.mik_laj.sondaze.client.api.services.PollService;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonObject;
import pl.edu.uph.ii.mik_laj.sondaze.json.types.JsonString;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.Poll;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

/**
 * Okno z liste ankiet. W zaleznosci od przekazanego uzytkownika i jego uprawnien pokazuje lub ukrywa mozliwe akcje
 * @author andrzej
 *
 */
public class ListPollFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350220618682020144L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void startFrame(Api api, User user) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListPollFrame frame = new ListPollFrame(api, user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel mainList;
	private Api api;
	private JButton btnRefresh;
	private User user;
	private JPanel panel_1;
	private JLabel lblUsername;
	private JLabel lblRole;
	private JButton btnAddPoll;
	private JButton btnUsers;

	/**
	 * Create the frame.
	 * 
	 * @param api
	 * @param user
	 */
	public ListPollFrame(Api api, User user) {
		this.api = api;
		this.user = user;
		createUi();

		lblRole.setText(user.isAdmin() ? "Admin" : "User");
		lblUsername.setText(user.getLogin());
		btnRefresh.addActionListener(ev -> fetchPolls());
		btnAddPoll.addActionListener(d -> onAddPoll());
		btnUsers.addActionListener(ev -> onUsersList());
		if (!user.isAdmin()) {
			btnAddPoll.setVisible(false);
			btnUsers.setVisible(false);			
		}
		fetchPolls();
		addWindowFocusListener(FrameUtils.simplifyWindowListener(d -> fetchPolls(), d -> fetchPolls()));
	}

	private void onUsersList() {
		UserListFrame.startFrame(api);
	}

	private void onAddPoll() {
		String newText = JOptionPane.showInputDialog("What would you like to name?");
		if (newText != null) {
			try {
				JsonObject j = new JsonObject();
				j.addElement("text", new JsonString(newText));
				PollService pollService = api.getPollService();
				pollService.createPoll(j);
				fetchPolls();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createUi() {
		setTitle("List poll");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		lblUsername = new JLabel("New label");
		panel_1.add(lblUsername);

		lblRole = new JLabel("New label");
		panel_1.add(lblRole);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		mainList = new JPanel();
		scrollPane.setViewportView(mainList);
		GridBagLayout gbl_mainList = new GridBagLayout();
		gbl_mainList.columnWidths = new int[] { 350 };
		gbl_mainList.rowHeights = new int[] { 25, 0 };
		gbl_mainList.columnWeights = new double[] { 0.0 };
		gbl_mainList.rowWeights = new double[] { 0 };
		mainList.setLayout(gbl_mainList);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 25, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		btnUsers = new JButton("Users");
		GridBagConstraints gbc_btnUsers = new GridBagConstraints();
		gbc_btnUsers.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnUsers.insets = new Insets(0, 0, 0, 5);
		gbc_btnUsers.gridx = 0;
		gbc_btnUsers.gridy = 0;
		panel.add(btnUsers, gbc_btnUsers);

		btnRefresh = new JButton("Refresh");
		GridBagConstraints gbc_btnRefresh = new GridBagConstraints();
		gbc_btnRefresh.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnRefresh.insets = new Insets(0, 0, 0, 5);
		gbc_btnRefresh.gridx = 1;
		gbc_btnRefresh.gridy = 0;
		panel.add(btnRefresh, gbc_btnRefresh);

		btnAddPoll = new JButton("Add poll");
		GridBagConstraints gbc_btnAddPoll = new GridBagConstraints();
		gbc_btnAddPoll.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAddPoll.gridx = 2;
		gbc_btnAddPoll.gridy = 0;
		panel.add(btnAddPoll, gbc_btnAddPoll);
	}

	public void fetchPolls() {
		try {
			System.out.println("Fetch Polls");
			List<Poll> polls = api.getPollService().getPolls();
			polls.stream().forEach(System.out::println);
			mainList.removeAll();
			for (Poll p : polls) {
				JPanel row_item = new JPanel();
				GridBagConstraints gbc_row_item = new GridBagConstraints();
				gbc_row_item.anchor = GridBagConstraints.NORTH;
				gbc_row_item.insets = new Insets(0, 0, 0, 0);
				gbc_row_item.gridx = 0;
				mainList.add(row_item, gbc_row_item);
				GridBagLayout gbl_row_item = new GridBagLayout();
				gbl_row_item.columnWidths = new int[] { 250, 100, 0 };
				// gbl_row_item.rowHeights = new int[] { 25, 0 };
				gbl_row_item.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
				gbl_row_item.rowWeights = new double[] { 0.0 };
				row_item.setLayout(gbl_row_item);

				JLabel lblText = new JLabel(p.getText());
				GridBagConstraints gbc_lblText = new GridBagConstraints();
				gbc_lblText.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblText.insets = new Insets(10, 10, 10, 10);
				gbc_lblText.gridx = 0;
				gbc_lblText.gridy = 0;
				row_item.add(lblText, gbc_lblText);

				JButton btnOpen = new JButton("Open");
				GridBagConstraints gbc_btnAction = new GridBagConstraints();
				gbc_btnAction.anchor = GridBagConstraints.NORTHEAST;
				gbc_btnAction.gridx = 1;
				gbc_btnAction.gridy = 0;
				row_item.add(btnOpen, gbc_btnAction);
				btnOpen.addActionListener(ev -> onOpenButton(p));
			}
			contentPane.validate();
			contentPane.repaint();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void onOpenButton(Poll poll) {
		if (user.isAdmin()) {
			EditPollFrame.startFrame(api, poll);
		} else {
			AnswerPollFrame.startFrame(api, poll);
		}
	}

}
