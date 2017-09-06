package pl.edu.uph.ii.mik_laj.sondaze.client.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import pl.edu.uph.ii.mik_laj.sondaze.client.api.Api;
import pl.edu.uph.ii.mik_laj.sondaze.client.frame.CreateUserFrame.UserData;
import pl.edu.uph.ii.mik_laj.sondaze.shared.entity.User;

/**
 * Okno wyboru listy uzytkownikow
 * @author andrzej
 *
 */
public class UserListFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2405527746631692166L;
	private JPanel contentPane;
	private JPanel mainList;
	private JButton btnRefresh;
	private JButton btnAddUser;
	private Api api;
	private JButton btnAddAdmin;

	/**
	 * Launch the application.
	 */
	public static void startFrame(Api api) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserListFrame frame = new UserListFrame(api);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param api
	 */
	public UserListFrame(Api api) {
		this.api = api;

		createUi();

		fetchUsers();

		btnRefresh.addActionListener(ev -> fetchUsers());
		btnAddAdmin.addActionListener(ev -> onAddAdmin());
		btnAddUser.addActionListener(ev -> onAddUser());
	}

	private void onAddUser() {
		UserData data = CreateUserFrame.showDialog();
		if (data != null) {
			try {
				User u = new User();
				u.setAdmin(false);
				u.setLogin(data.login);
				u.setPassword(data.password);

				api.getUserService().createUser(u);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fetchUsers();
	}

	private void onAddAdmin() {
		UserData data = CreateUserFrame.showDialog();
		if (data != null) {
			try {
				User u = new User();
				u.setAdmin(true);
				u.setLogin(data.login);
				u.setPassword(data.password);

				api.getUserService().createUser(u);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fetchUsers();
	}

	private void createUi() {
		setTitle("User List");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);

		btnRefresh = new JButton("Refresh");
		panel_1.add(btnRefresh);

		btnAddUser = new JButton("Add user");
		panel_1.add(btnAddUser);

		btnAddAdmin = new JButton("Add admin");
		panel_1.add(btnAddAdmin);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);

		mainList = new JPanel();
		scrollPane.setViewportView(mainList);
		GridBagLayout gbl_mainList = new GridBagLayout();
		gbl_mainList.columnWidths = new int[] { 419, 0 };
		gbl_mainList.rowHeights = new int[] { 0, 0 };
		gbl_mainList.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_mainList.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		mainList.setLayout(gbl_mainList);
	}

	private void fetchUsers() {
		try {
			List<User> users = this.api.getUserService().getUsers();
			mainList.removeAll();

			for (User user : users) {

				JPanel rowItem = new JPanel();

				GridBagConstraints gbc_rowItem = new GridBagConstraints();
				gbc_rowItem.fill = GridBagConstraints.HORIZONTAL;
				gbc_rowItem.gridx = 0;
				mainList.add(rowItem, gbc_rowItem);
				rowItem.setBorder(new EmptyBorder(5, 5, 5, 5));
				GridBagLayout gbl_rowItem = new GridBagLayout();
				gbl_rowItem.columnWidths = new int[] { 0, 0, 0 };
				gbl_rowItem.rowHeights = new int[] { 25, 0 };
				gbl_rowItem.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
				gbl_rowItem.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
				rowItem.setLayout(gbl_rowItem);

				JLabel lblUsername = new JLabel();
				lblUsername.setText(user.getLogin());
				lblUsername.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_lblUsername = new GridBagConstraints();
				gbc_lblUsername.fill = GridBagConstraints.VERTICAL;
				gbc_lblUsername.insets = new Insets(0, 0, 0, 5);
				gbc_lblUsername.gridx = 0;
				gbc_lblUsername.gridy = 0;
				rowItem.add(lblUsername, gbc_lblUsername);

				JLabel lblRole = new JLabel();
				lblRole.setText(user.isAdmin() ? "Admin" : "User");
				lblRole.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_lblRole = new GridBagConstraints();
				gbc_lblRole.fill = GridBagConstraints.VERTICAL;
				gbc_lblRole.insets = new Insets(0, 0, 0, 5);
				gbc_lblRole.gridx = 1;
				gbc_lblRole.gridy = 0;
				rowItem.add(lblRole, gbc_lblRole);

				JButton btnDelete = new JButton("Delete");
				btnDelete.addActionListener(ev -> onUserDelete(user));
				GridBagConstraints gbc_btnDelete = new GridBagConstraints();
				gbc_btnDelete.gridx = 2;
				gbc_btnDelete.gridy = 0;
				rowItem.add(btnDelete, gbc_btnDelete);
			}

			contentPane.validate();
			contentPane.repaint();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void onUserDelete(User user) {
		try {
			api.getUserService().deleteUser(user.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fetchUsers();
	}
}
