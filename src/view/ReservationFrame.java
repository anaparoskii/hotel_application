package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import controller.ReservationControl;
import entity.AdditionalService;
import entity.Receptionist;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomType;
import manager.ManagerFactory;

public class ReservationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	private JFrame receptionistFrame;
	private Receptionist receptionist;
	
	public ReservationFrame(ManagerFactory managers, JFrame frame, Receptionist receptionist) {
		this.managers = managers;
		this.receptionistFrame = frame;
		this.receptionist = receptionist;
		reservationFrame();
	}
	
	private void reservationFrame() {
		JFrame main = new JFrame();
		String title = String.format("New reservation");
		main.setTitle(title);
		main.setSize(250, 400);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setAlwaysOnTop(true);
		initReservation(main);
		main.setVisible(true);
	}
	
	private void initReservation(JFrame frame) {
		ReservationControl reservationControl = new ReservationControl(managers);
		Container content = frame.getContentPane();
		content.setLayout(null);
		
		JLabel label = new JLabel("Reservation form");
		label.setSize(180, 30);
		label.setLocation(10, 11);
		content.add(label);
		
		JLabel lblCriteria = new JLabel("<html>Pick<br>criteria:</html>");
		lblCriteria.setBounds(10, 50, 65, 30);
		content.add(lblCriteria);
		
		DefaultListModel<String> listModel1 = new DefaultListModel<>();
		JList<String> criteriaList = new JList<>(listModel1);
		
		for (RoomCriteria c : RoomCriteria.values()) {
			listModel1.addElement(String.valueOf(c));
		}
		
		JScrollPane criteriaScroll = new JScrollPane(criteriaList);
		criteriaScroll.setBounds(85, 50, 130, 60);
		content.add(criteriaScroll);
		
		JLabel roomType = new JLabel("Room type: ");
		roomType.setBounds(10, 120, 65, 30);
		content.add(roomType);
		
		String[] roomTypes = new String[5];
		int i = 0;
		for (RoomType type : RoomType.values()) {
			roomTypes[i] = type.toString();
			i++;
		}
		JComboBox<String> dropDown = new JComboBox<>(roomTypes);
		dropDown.setBounds(85, 120, 130, 30);
		content.add(dropDown);
		
		JLabel benefits = new JLabel("<html>Extra<br>services:<html>");
		benefits.setBounds(10, 160, 65, 30);
		content.add(benefits);
		
		DefaultListModel<String> listModel = new DefaultListModel<>(); 
		JList<String> benefitsList = new JList<>(listModel); 
		ArrayList<AdditionalService> allServices = reservationControl.getServices(); 
		for (AdditionalService s : allServices) { 
			listModel.addElement(s.getType()); 
		}
		
		JScrollPane serviceScroll = new JScrollPane(benefitsList);
		serviceScroll.setBounds(85, 160, 130, 60);
		content.add(serviceScroll);
		
		JLabel lblCheckIn = new JLabel("Check in: \r\n");
		lblCheckIn.setBounds(10, 226, 65, 30);
		content.add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("Check out: \r\n");
		lblCheckOut.setBounds(10, 262, 65, 30);
		content.add(lblCheckOut);
		
		TextField checkInTf = new TextField();
		checkInTf.setForeground(new Color(0, 0, 0));
		checkInTf.setBounds(85, 226, 130, 30);
		content.add(checkInTf);
		
		TextField checkOutTf = new TextField();
		checkOutTf.setForeground(new Color(0, 0, 0));
		checkOutTf.setBounds(85, 262, 130, 30);
		content.add(checkOutTf);
		
		JButton btnReserve = new JButton("RESERVE");
		btnReserve.setBounds(120, 320, 95, 30);
		content.add(btnReserve);
		
		criteriaList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ArrayList<RoomCriteria> allCriteria = new ArrayList<>();
					for (int i = 0; i < RoomCriteria.values().length; i++) {
						if (criteriaList.isSelectedIndex(i)) {
							allCriteria.add(RoomCriteria.values()[i]);
						}
					}
					dropDown.removeAllItems();
					for (Map.Entry<Integer, Room> set : managers.getRoomManager().getAllRooms().entrySet()) {
						if (set.getValue().getCriteria().containsAll(allCriteria)) {
							dropDown.addItem(String.valueOf(set.getValue().getType()));
						}
					}
				}
			}
		});
		
		btnReserve.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object s = (String) dropDown.getEditor().getItem();
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
				ArrayList<AdditionalService> chosenServices = new ArrayList<>();
				for (int i = 0; i < allServices.size(); i++) {
					if (benefitsList.isSelectedIndex(i)) {
						chosenServices.add(allServices.get(i));
					}
				}
				ArrayList<RoomCriteria> allCriteria = new ArrayList<>();
				for (int i = 0; i < RoomCriteria.values().length; i++) {
					if (criteriaList.isSelectedIndex(i)) {
						allCriteria.add(RoomCriteria.values()[i]);
					}
				}
				LocalDate checkIn = LocalDate.parse(checkInTf.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
				LocalDate checkOut = LocalDate.parse(checkOutTf.getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
				managers.getReservationManager().createReservation(null, type, checkIn, checkOut, chosenServices, 
						managers.getPricingManager(), allCriteria);
				frame.dispose();
				receptionistFrame.dispose();
				new ReceptionistFrame(managers, receptionist);
				new InfoDialog(managers, "Reservation successful!!!");
			}
		});
	}
	
	public ManagerFactory getManagers() {
		return managers;
	}
	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}

	public JFrame getReceptionistFrame() {
		return receptionistFrame;
	}

	public void setReceptionistFrame(JFrame receptionistFrame) {
		this.receptionistFrame = receptionistFrame;
	}

	public Receptionist getReceptionist() {
		return receptionist;
	}

	public void setReceptionist(Receptionist receptionist) {
		this.receptionist = receptionist;
	}

}
