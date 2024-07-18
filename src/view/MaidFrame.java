package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.MaidControl;
import entity.Maid;
import entity.Room;
import manager.ManagerFactory;

public class MaidFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	private Maid maid;
	
	public MaidFrame(ManagerFactory managers, Maid maid) {
		this.managers = managers;
		this.maid = maid;
		maidFrame();
	}
	
	private void maidFrame() {
		JFrame main = new JFrame();
		String title = String.format("Maid Panel - %s %s", maid.getFirstName(), maid.getLastName());
		main.setTitle(title);
		main.setSize(650, 400);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initMaid(main);
		main.setVisible(true);
	}
	
	private void initMaid(JFrame frame) {
		frame.getContentPane().setLayout(new BorderLayout());
		
		Container container = frame.getContentPane();
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		JButton logoutBtn = new JButton("LOG OUT");
		header.add(logoutBtn);
		container.add(header, BorderLayout.NORTH);
		
		JTabbedPane contentPane = new JTabbedPane();
		contentPane.setBackground(new Color(240, 240, 240));
		Container maidContainer = new Container();
		maidContainer.setForeground(new Color(240, 240, 240));
		maidContainer.setBackground(new Color(255, 255, 255));
		maidContainer.setLayout(null);
		
		JLabel label1 = new JLabel("Rooms");
		label1.setSize(154, 30);
		label1.setLocation(175, 11);
		maidContainer.add(label1);
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[] {
					"Room number", "Room type"
				}, 0
				);
		JTable roomTable = new JTable(tableModel);
		roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomTable.setDefaultEditor(Object.class, null);
		
		ArrayList<Room> rooms = maid.getRoomsToClean();
		
		for (Room r : rooms) {
			tableModel.addRow(new Object[] {r.getRoomNumber(), r.getType().toString()});
		}
		roomTable.setBackground(new Color(240, 240, 240));

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.setBounds(10, 50, 360, 249);
		
		tablePanel.add(roomTable.getTableHeader(), BorderLayout.NORTH);
		tablePanel.add(roomTable, BorderLayout.CENTER);
		maidContainer.add(tablePanel);
		
		JLabel label2 = new JLabel("Mark room as cleaned");
		label2.setBounds(400, 11, 150, 30);
		maidContainer.add(label2);
		
		JLabel pick = new JLabel("Choose room number: ");
		pick.setBounds(400, 50, 150, 30);
		maidContainer.add(pick);
		
		String[] roomNumbers = new String[5];
		int i = 0;
		for (Room r : rooms) {
			roomNumbers[i] = String.valueOf(r.getRoomNumber());
			i++;
		}
		JComboBox<String> dropDown = new JComboBox<>(roomNumbers);
		dropDown.setBounds(399, 91, 141, 30);
		maidContainer.add(dropDown);
		
		JButton btnAction = new JButton("MARK DONE");
		btnAction.setBounds(409, 132, 131, 30);
		maidContainer.add(btnAction);
		
		contentPane.addTab("Rooms", maidContainer);
		
		container.add(contentPane, BorderLayout.CENTER);
		
		MaidControl maidControl = new MaidControl(managers);
		
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				managers.saveData();
				frame.setVisible(false);
				new LoginDialog(managers);
			}
		});
		
		btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object s = (String) dropDown.getEditor().getItem();
				int id = -1;
				for (String i : roomNumbers) {
					if (s.equals(i)) {
						id = Integer.parseInt(i);
					}
				}
				maidControl.cleanRoom(maid.getFirstName(), maid.getLastName(), managers.getRoomManager().findRoomByID(id));
				frame.dispose();
				new MaidFrame(managers, maid);
				new InfoDialog(managers, "Room cleaned!!!");
			}
		});
	}
	
	public ManagerFactory getManagers() {
		return managers;
	}
	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}
	public Maid getMaid() {
		return maid;
	}
	public void setMaid(Maid maid) {
		this.maid = maid;
	}

}
