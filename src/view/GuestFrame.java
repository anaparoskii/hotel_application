package view;

import java.awt.BorderLayout;
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

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.ReservationControl;
import entity.AdditionalService;
import entity.Guest;
import entity.Reservation;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomType;
import manager.FormatManager;
import manager.ManagerFactory;

public class GuestFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	private Guest guest;
	
	public GuestFrame(ManagerFactory managers, Guest guest) {
		this.managers = managers;
		this.guest = guest;
		guestFrame();
	}
	
	private void guestFrame() {
		JFrame main = new JFrame();
		String title = String.format("Guest Panel - %s %s", guest.getFirstName(), guest.getLastName());
		main.setTitle(title);
		main.setSize(650, 450);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initGuest(main);
		main.setVisible(true);
	}
	
	private void initGuest(JFrame frame) {
		ReservationControl reservationControl = new ReservationControl(managers);
		frame.getContentPane().setLayout(new BorderLayout());
	
		Container container = frame.getContentPane();
		JPanel header = new JPanel();
		header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
		
		JButton logoutBtn = new JButton("LOG OUT");
		header.add(logoutBtn);
		container.add(header, BorderLayout.NORTH);
		
		JTabbedPane contentPane = new JTabbedPane();
		contentPane.setBackground(new Color(240, 240, 240));
		Container reservationContainer = new Container();
		reservationContainer.setForeground(new Color(240, 240, 240));
		reservationContainer.setBackground(new Color(255, 255, 255));
		reservationContainer.setLayout(null);
		
		JLabel label1 = new JLabel("My Reservations");
		label1.setSize(154, 30);
		label1.setLocation(162, 11);
		reservationContainer.add(label1);
		
		ArrayList<Reservation> myReservations = reservationControl.getGuestReservation(guest);
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[] {
					"Check-in", "Check-out", "Room type", "Services", "Status", "Price"
				}, 0
				);
		JTable reservationTable = new JTable(tableModel);
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reservationTable.setDefaultEditor(Object.class, null);
		
		FormatManager formatManager = new FormatManager();
		for (Reservation r : myReservations) {
			String benefits = "";
			for (AdditionalService s : r.getBenefits()) {
				if (s != null) {
					benefits += s.getType() + ",";
				}
			}
			tableModel.addRow(new Object[] {formatManager.dateToString(r.getCheckInDate()),
											formatManager.dateToString(r.getCheckOutDate()),
											r.getRoomType(),
											benefits,
											r.getStatus(), r.getPrice()});
		}
		reservationTable.setBackground(new Color(240, 240, 240));
		
		JScrollPane reservationScroll = new JScrollPane(reservationTable);
		reservationScroll.setBounds(10, 50, 383, 250);
		
		reservationContainer.add(reservationScroll);
		
		Double total = 0.0;
		for (Reservation r : myReservations) {
			total += r.getPrice();
		}
		
		String t = String.format("Total: %.2f", total);
		JLabel totalSpending = new JLabel(t);
		totalSpending.setSize(95, 30);
		totalSpending.setLocation(298, 311);
		reservationContainer.add(totalSpending);
		
		JLabel label2 = new JLabel("Reservation form");
		label2.setSize(180, 30);
		label2.setLocation(428, 11);
		reservationContainer.add(label2);
		
		JLabel lblCriteria = new JLabel("<html>Pick<br>criteria:</html>");
		lblCriteria.setBounds(403, 50, 65, 30);
		reservationContainer.add(lblCriteria);
		
		DefaultListModel<String> listModel1 = new DefaultListModel<>();
		JList<String> criteriaList = new JList<>(listModel1);
		
		for (RoomCriteria c : RoomCriteria.values()) {
			listModel1.addElement(String.valueOf(c));
		}
		
		JScrollPane criteriaScroll = new JScrollPane(criteriaList);
		criteriaScroll.setBounds(478, 50, 130, 50);
		reservationContainer.add(criteriaScroll);
		
		JLabel roomType = new JLabel("<html>Room<br>type: </html>");
		roomType.setBounds(403, 110, 65, 30);
		reservationContainer.add(roomType);
		
		String[] roomTypes = new String[5];
		int i = 0;
		for (RoomType type : RoomType.values()) {
			roomTypes[i] = type.toString();
			i++;
		}
		JComboBox<String> dropDown = new JComboBox<>(roomTypes);
		dropDown.setBounds(478, 110, 130, 30);
		reservationContainer.add(dropDown);
		
		JLabel benefits = new JLabel("<html>Extra<br>services:<html>");
		benefits.setBounds(403, 150, 65, 30);
		reservationContainer.add(benefits);
		
		contentPane.addTab("Reservations", reservationContainer);
		
		DefaultListModel<String> listModel = new DefaultListModel<>(); 
		JList<String> benefitsList = new JList<>(listModel); 
		ArrayList<AdditionalService> allServices = reservationControl.getServices(); 
		for (AdditionalService s : allServices) { 
			listModel.addElement(s.getType()); 
		}
		
		JScrollPane serviceScroll = new JScrollPane(benefitsList);
		serviceScroll.setBounds(478, 150, 130, 50);
		reservationContainer.add(serviceScroll);
		
		JLabel lblCheckIn = new JLabel("Check in: \r\n");
		lblCheckIn.setBounds(403, 206, 65, 30);
		reservationContainer.add(lblCheckIn);
		
		JLabel lblCheckOut = new JLabel("Check out: \r\n");
		lblCheckOut.setBounds(403, 242, 65, 30);
		reservationContainer.add(lblCheckOut);
		
		TextField checkInTf = new TextField();
		checkInTf.setForeground(new Color(0, 0, 0));
		checkInTf.setBounds(478, 206, 130, 30);
		reservationContainer.add(checkInTf);
		
		TextField checkOutTf = new TextField();
		checkOutTf.setForeground(new Color(0, 0, 0));
		checkOutTf.setBounds(478, 242, 130, 30);
		reservationContainer.add(checkOutTf);
		
		JButton btnReserve = new JButton("RESERVE");
		btnReserve.setBounds(513, 278, 95, 30);
		reservationContainer.add(btnReserve);
		
		JButton btnCancel = new JButton("CANCEL RESERVATION");
		btnCancel.setBounds(428, 319, 180, 30);
		reservationContainer.add(btnCancel);
		
		container.add(contentPane, BorderLayout.CENTER);
		
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
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = reservationTable.getSelectedRow();
				System.out.println(selectedRow);
				if (selectedRow != -1) {
					Reservation r = myReservations.get(selectedRow);
					int id = r.getId();
					reservationControl.cancelReservation(managers.getReservationManager().findReservationByID(id));
					frame.dispose();
					new GuestFrame(managers, guest);
					new InfoDialog(managers, "Reservation canceled!!!");
				} else {
					new InfoDialog(managers, "Reservation not selected");
				}
			}
		});
		
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				managers.saveData();
				frame.setVisible(false);
				new LoginDialog(managers);
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
				managers.getReservationManager().createReservation(guest, type, checkIn, checkOut, chosenServices, 
						managers.getPricingManager(), allCriteria);
				frame.dispose();
				new GuestFrame(managers, guest);
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

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}
}
