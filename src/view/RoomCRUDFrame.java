package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import entity.Admin;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomType;
import manager.ManagerFactory;

public class RoomCRUDFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Admin admin;
	private ManagerFactory managers;
	private JFrame adminFrame;
	private Room room;
	
	public RoomCRUDFrame(ManagerFactory managers, Admin admin, JFrame frame, Room room) {
		this.managers = managers;
		this.admin = admin;
		this.adminFrame = frame;
		this.room = room;
		addRoomFrame();
	}
	
	private void addRoomFrame() {
		JFrame main = new JFrame();
		String title = String.format("New room");
		main.setTitle(title);
		main.setSize(300, 275);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setAlwaysOnTop(true);
		initRoom(main);
		main.setVisible(true);
	}
	
	private void initRoom(JFrame frame) {
		Container content = frame.getContentPane();
		content.setLayout(null);
		
		JTextField roomNumberTf;
		
		if (room == null) {
			roomNumberTf = new JTextField();
		} else {
			roomNumberTf = new JTextField(String.valueOf(room.getRoomNumber()));
			roomNumberTf.setEditable(false);
		}
		
		JLabel lblRoomNumber = new JLabel("Room number:");
		lblRoomNumber.setSize(110, 30);
		lblRoomNumber.setLocation(10, 11);
		content.add(lblRoomNumber);
		
		roomNumberTf.setSize(149, 30);
		roomNumberTf.setLocation(125, 11);
		content.add(roomNumberTf);
		
		JLabel lblRoomType = new JLabel("Room type:");
		lblRoomType.setSize(110, 30);
		lblRoomType.setLocation(10, 50);
		content.add(lblRoomType);
		
		String[] roomTypes = new String[5];
		int j = 0;
		for (RoomType type : RoomType.values()) {
			roomTypes[j] = type.toString();
			j++;
		}
		JComboBox<String> roomCb = new JComboBox<>(roomTypes);
		roomCb.setBounds(125, 50, 149, 30);
		content.add(roomCb);
		
		JLabel lblCriteria = new JLabel("Pick criteria:");
		lblCriteria.setBounds(10, 90, 110, 30);
		content.add(lblCriteria);
		
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> criteriaList = new JList<>(listModel);
		
		for (RoomCriteria c : RoomCriteria.values()) {
			listModel.addElement(String.valueOf(c));
		}
		
		JScrollPane criteriaScroll = new JScrollPane(criteriaList);
		criteriaScroll.setBounds(125, 90, 149, 86);
		content.add(criteriaScroll);
		
		JButton addRoomBtn = new JButton("CONFIRM");
		addRoomBtn.setSize(110, 30);
		addRoomBtn.setLocation(164, 195);
		content.add(addRoomBtn);
		
		addRoomBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object s = (String) roomCb.getEditor().getItem();
				RoomType type;
				if (s.equals("single bed")) {
					type = RoomType.SINGLE_BED;
				} else if (s.equals("double bed")) {
					type = RoomType.DOUBLE_BED;
				} else if (s.equals("king bed")) {
					type = RoomType.KING_BED;
				} else if (s.equals("triple bed")) {
					type = RoomType.TRIPLE_BED;
				} else {
					type = RoomType.QUAD_BED;
				}
				ArrayList<RoomCriteria> allCriteria = new ArrayList<>();
				for (int i = 0; i < RoomCriteria.values().length; i++) {
					if (criteriaList.isSelectedIndex(i)) {
						allCriteria.add(RoomCriteria.values()[i]);
					}
				}
				if (room == null) {
					managers.getRoomManager().createRoom(type, Integer.parseInt(roomNumberTf.getText()), allCriteria);
					frame.dispose();
					adminFrame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Room added successfully!");
				} else {
					managers.getRoomManager().getAllRooms().replace(room.getRoomNumber(), new Room(type, room.getRoomNumber(), allCriteria));
					frame.dispose();
					adminFrame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Room updated successfully!");
				}
			}
		});
	}
	
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public ManagerFactory getManagers() {
		return managers;
	}
	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}
	public JFrame getAdminFrame() {
		return adminFrame;
	}
	public void setAdminFrame(JFrame adminFrame) {
		this.adminFrame = adminFrame;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

}
