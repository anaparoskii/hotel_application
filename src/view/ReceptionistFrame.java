package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
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

import controller.CheckInControl;
import controller.CheckOutControl;
import controller.ReservationControl;
import entity.AdditionalService;
import entity.Receptionist;
import entity.Reservation;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomType;
import manager.FormatManager;
import manager.ManagerFactory;

public class ReceptionistFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ManagerFactory managers;
	private Receptionist receptionist;
	
	public ReceptionistFrame(ManagerFactory managers, Receptionist receptionist) {
		this.setManagers(managers);
		this.receptionist = receptionist;
		receptionistFrame();
	}
	
	private void receptionistFrame() {
		JFrame main = new JFrame();
		String title = String.format("Receptionist Panel - %s %s", receptionist.getFirstName(), receptionist.getLastName());
		main.setTitle(title);
		main.setSize(650, 400);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initReceptionist(main);
		main.setVisible(true);
	}
	
	private void initReceptionist(JFrame frame) {
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
		
		JButton btn1 = new JButton("SHOW ALL");
		btn1.setBounds(519, 225, 100, 25);
		reservationContainer.add(btn1);
		
		JLabel label1 = new JLabel("All Reservations");
		label1.setSize(100, 30);
		label1.setLocation(10, 45);
		reservationContainer.add(label1);
		
		String[] filter = {"prices ascending", "prices descending", "none"};
		JComboBox<String> filterCb = new JComboBox<>(filter);
		filterCb.setBounds(215, 11, 130, 25);
		reservationContainer.add(filterCb);
		
		String[] roomTypes = new String[5];
		int l = 0;
		for (RoomType type : RoomType.values()) {
			roomTypes[l] = type.toString();
			l++;
		}
		JComboBox<String> roomCb = new JComboBox<>(roomTypes);
		roomCb.setBounds(355, 44, 115, 25);
		reservationContainer.add(roomCb);
		
		ArrayList<Room> allRooms = managers.getRoomManager().readAllRooms();
		String[] roomNumbers = new String[allRooms.size()];
		int m = 0;
		for (Room r : allRooms) {
			roomNumbers[m] = String.valueOf(r.getRoomNumber());
			m++;
		}
		JComboBox<String> roomNumberCb = new JComboBox<>(roomNumbers);
		roomNumberCb.setBounds(355, 11, 115, 25);
		reservationContainer.add(roomNumberCb);
		
		DefaultListModel<String> listModel = new DefaultListModel<>(); 
		JList<String> benefitsList = new JList<>(listModel); 
		ArrayList<AdditionalService> allServices = reservationControl.getServices(); 
		for (AdditionalService s : allServices) { 
			listModel.addElement(s.getType()); 
		}
		
		JScrollPane service1Scroll = new JScrollPane(benefitsList);
		service1Scroll.setBounds(480, 9, 135, 60);
		reservationContainer.add(service1Scroll);
		
		ArrayList<Reservation> reservations = reservationControl.getReservation();
		
		DefaultTableModel tableModel = new DefaultTableModel(new String[] {
					"Code", "Check-in", "Check-out", "Room type", "Room no", "Services", "Status", "Price", "Confirmed"
				}, 0);
		JTable reservationTable = new JTable(tableModel);
		reservationTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		reservationTable.setDefaultEditor(Object.class, null);
		
		FormatManager formatManager = new FormatManager();
		for (Reservation r : reservations) {
			String benefits = "";
			for (AdditionalService s : r.getBenefits()) {
				if (s != null) {
					benefits += s.getType() + ",";
				}
			}
			String room;
			if (r.getRoom() == null) {
				room = "";
			} else {
				room = String.valueOf(r.getRoom().getRoomNumber());
			}
			String date;
			if (r.getDateConfirmed() == null) {
				date = "";
			} else {
				date = formatManager.dateToString(r.getDateConfirmed());
			}
			tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
											formatManager.dateToString(r.getCheckOutDate()),
											r.getRoomType(), room,
											benefits,
											r.getStatus(), r.getPrice(), date});
		}
		reservationTable.setBackground(new Color(240, 240, 240));
		
		JScrollPane reservationScroll = new JScrollPane(reservationTable);
		reservationScroll.setBounds(10, 76, 609, 143);
		reservationContainer.add(reservationScroll);
		
		JLabel pick = new JLabel("Choose reservation ID: ");
		pick.setBounds(10, 228, 150, 30);
		reservationContainer.add(pick);
		
		String[] reservationIDs = new String[5];
		int i = 0;
		for (Reservation r : reservations) {
			if (r.getStatus().equals(ReservationStatus.WAITING)) {
				reservationIDs[i] = String.valueOf(r.getId());
				i++;
			}
		}
		JComboBox<String> dropDown = new JComboBox<>(reservationIDs);
		dropDown.setBounds(10, 269, 150, 30);
		reservationContainer.add(dropDown);
		
		JButton btnAction = new JButton("CONFIRM");
		btnAction.setBounds(170, 269, 100, 30);
		reservationContainer.add(btnAction);
		container.add(contentPane, BorderLayout.CENTER);
		
		contentPane.addTab("Reservations", reservationContainer);
		
		JButton btnNewReservation = new JButton("NEW RESERVATION");
		btnNewReservation.setBounds(469, 269, 150, 30);
		reservationContainer.add(btnNewReservation);
		
		Container checkInContainer = new Container();
		checkInContainer.setForeground(new Color(240, 240, 240));
		checkInContainer.setBackground(new Color(255, 255, 255));
		checkInContainer.setLayout(null);
		
		JLabel label3 = new JLabel("Check in process");
		label3.setSize(200, 30);
		label3.setLocation(185, 11);
		checkInContainer.add(label3);
		
		ArrayList<Reservation> acceptedReservations = reservationControl.getAcceptedReservations();
		
		DefaultTableModel tableCheckInModel = new DefaultTableModel(new String[] {
					"Code", "Check-in", "Guest", "Room", "Room type"
				}, 0);
		JTable checkInTable = new JTable(tableCheckInModel);
		checkInTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		checkInTable.setDefaultEditor(Object.class, null);
		
		for (Reservation r : acceptedReservations) {
			if (r.isActive()) {
				String username;
				if (r.getGuest() == null) {
					username = "";
				} else {
					username = r.getGuest().getUsername();
				}
				tableCheckInModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
												username,
												String.valueOf(r.getRoom().getRoomNumber()),
												r.getRoomType(),
												r.getStatus()});
			}
		}
		checkInTable.setBackground(new Color(240, 240, 240));
		
		JScrollPane checkInScroll = new JScrollPane(checkInTable);
		checkInScroll.setBounds(10, 50, 435, 250);
		checkInContainer.add(checkInScroll);
		
		JLabel check = new JLabel("Choose reservation ID: ");
		check.setBounds(455, 50, 150, 30);
		checkInContainer.add(check);
		
		String[] checkInIDs = new String[5];
		int j = 0;
		for (Reservation r : acceptedReservations) {
			checkInIDs[j] = String.valueOf(r.getId());
			j++;
		}
		JComboBox<String> checkInDropDown = new JComboBox<>(checkInIDs);
		checkInDropDown.setBounds(455, 80, 141, 30);
		checkInContainer.add(checkInDropDown);
		
		JLabel label4 = new JLabel("Choose extra services:");
		label4.setSize(200, 30);
		label4.setLocation(455, 121);
		checkInContainer.add(label4);
		
		DefaultListModel<String> checkInListModel = new DefaultListModel<>(); 
		JList<String> addServiceList = new JList<>(checkInListModel);
		for (AdditionalService s : allServices) { 
			checkInListModel.addElement(s.getType()); 
		}
		
		JScrollPane serviceScroll = new JScrollPane(addServiceList);
		serviceScroll.setBounds(455, 150, 141, 70);
		checkInContainer.add(serviceScroll);
		
		contentPane.addTab("Check in", checkInContainer);
		
		JButton btnCheckIn = new JButton("CHECK IN");
		btnCheckIn.setBounds(496, 231, 100, 30);
		checkInContainer.add(btnCheckIn);
		
		Container checkOutContainer = new Container();
		
		CheckInControl checkInControl = new CheckInControl(managers);
		checkOutContainer.setForeground(new Color(240, 240, 240));
		checkOutContainer.setBackground(new Color(255, 255, 255));
		checkOutContainer.setLayout(null);
		
		JLabel label5 = new JLabel("Check out proces");
		label5.setSize(200, 30);
		label5.setLocation(184, 11);
		checkOutContainer.add(label5);
		
		ArrayList<Reservation> toCheckOut = reservationControl.getCheckOutReservations();
		
		DefaultTableModel tableCheckOutModel = new DefaultTableModel(new String[] {
					"Code", "Check-in", "Guest", "Room", "Room type"
				}, 0);
		JTable checkOutTable = new JTable(tableCheckOutModel);
		checkOutTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		checkOutTable.setDefaultEditor(Object.class, null);
		
		for (Reservation r : toCheckOut) {
			String username;
			if (r.getGuest() == null) {
				username = "";
			} else {
				username = r.getGuest().getUsername();
			}
			tableCheckOutModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
											username,
											String.valueOf(r.getRoom().getRoomNumber()),
											r.getRoomType(),
											r.getStatus()});
		}
		checkOutTable.setBackground(new Color(240, 240, 240));
		
		JScrollPane checkOutScroll = new JScrollPane(checkOutTable);
		checkOutScroll.setBounds(10, 50, 435, 250);
		checkOutContainer.add(checkOutScroll);
		
		JLabel checkOut = new JLabel("Choose reservation ID: ");
		checkOut.setBounds(455, 50, 150, 30);
		checkOutContainer.add(checkOut);
		
		String[] checkOutIDs = new String[5];
		int k = 0;
		for (Reservation r : toCheckOut) {
			checkOutIDs[k] = String.valueOf(r.getId());
			k++;
		}
		JComboBox<String> checkOutDropDown = new JComboBox<>(checkOutIDs);
		checkOutDropDown.setBounds(455, 80, 141, 30);
		checkOutContainer.add(checkOutDropDown);
		
		JButton btnCheckOut = new JButton("CHECK OUT");
		btnCheckOut.setBounds(496, 121, 100, 30);
		checkOutContainer.add(btnCheckOut);
		
		contentPane.addTab("Check out", checkOutContainer);
		
		Container dailyContainer = new Container();
		dailyContainer.setForeground(new Color(240, 240, 240));
		dailyContainer.setBackground(new Color(255, 255, 255));
		dailyContainer.setLayout(null);
		
		JLabel lblDailyIn = new JLabel("Check-ins today");
		lblDailyIn.setSize(154, 30);
		lblDailyIn.setLocation(270, 11);
		dailyContainer.add(lblDailyIn);
		
		ArrayList<Reservation> allReservation = managers.getReservationManager().readAllApprovedReservation();
		
		DefaultTableModel inTableModel = new DefaultTableModel(new String[] {
					"Code", "Date", "Guest", "Room", "Room Type", "Status"
				}, 0
				);
		JTable inTable = new JTable(inTableModel);
		inTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		inTable.setDefaultEditor(Object.class, null);
		
		for (Reservation r : allReservation) {
			if (LocalDate.now().isEqual(formatManager.asLocalDate(r.getCheckInDate()))) {
				String guest = "";
				if (r.getGuest() != null) {
					guest = r.getGuest().getUsername();
				}
				inTableModel.addRow(new Object[] {r.getId(), formatManager.dateToString(r.getCheckInDate()), guest, 
												  r.getRoom().getRoomNumber(), String.valueOf(r.getRoom().getType()), r.getStatus()});
			}
		}
		inTable.setBackground(new Color(240, 240, 240));

		JScrollPane inScroll = new JScrollPane(inTable);
		inScroll.setBounds(10, 37, 598, 115);
		
		dailyContainer.add(inScroll);
		
		JLabel lblDailyOut = new JLabel("Check-outs today");
		lblDailyOut.setSize(154, 30);
		lblDailyOut.setLocation(270, 158);
		dailyContainer.add(lblDailyOut);
		
		ArrayList<Reservation> outReservation = managers.getReservationManager().readCheckOutReservation();
		
		DefaultTableModel outTableModel = new DefaultTableModel(new String[] {
					"Code", "Date", "Guest", "Room", "Room Type", "Status"
				}, 0
				);
		JTable outTable = new JTable(outTableModel);
		outTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		outTable.setDefaultEditor(Object.class, null);
		
		for (Reservation r : outReservation) {
			if (LocalDate.now().isEqual(formatManager.asLocalDate(r.getCheckOutDate()))) {
				String guest = r.getGuest().getUsername();
				inTableModel.addRow(new Object[] {r.getId(), formatManager.dateToString(r.getCheckOutDate()), guest, 
												  r.getRoom().getRoomNumber(), String.valueOf(r.getRoom().getType()), 
												  String.valueOf(r.getRoom().getStatus())});
			}
			
		}
		outTable.setBackground(new Color(240, 240, 240));

		JScrollPane outScroll = new JScrollPane(outTable);
		outScroll.setBounds(10, 185, 598, 114);
		
		dailyContainer.add(outScroll);
		
		contentPane.addTab("Daily report", dailyContainer);
		
		Container availableContainer = new Container();
		availableContainer.setForeground(new Color(240, 240, 240));
		availableContainer.setBackground(new Color(255, 255, 255));
		availableContainer.setLayout(null);
		
		JLabel lblRoom = new JLabel("Rooms available today");
		lblRoom.setSize(154, 30);
		lblRoom.setLocation(270, 11);
		availableContainer.add(lblRoom);
		
		DefaultTableModel roomTableModel = new DefaultTableModel(new String[] {
					"Number", "Type", "Code", "Status", "Criteria"
				}, 0
				);
		JTable roomTable = new JTable(roomTableModel);
		roomTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		roomTable.setDefaultEditor(Object.class, null);
		
		for (Room r : allRooms) {
			if (managers.getRoomManager().isAvailable(LocalDate.now(), r)) {
				String s = "";
				for (RoomCriteria c : r.getCriteria()) {
					s += String.valueOf(c) + ",";
				}
				roomTableModel.addRow(new Object[] {r.getRoomNumber(), r.getType().toString(), 
														r.getRoomCode(), r.getStatus().toString(), s});
			}
			
		}
		roomTable.setBackground(new Color(240, 240, 240));

		JScrollPane roomScroll = new JScrollPane(roomTable);
		roomScroll.setBounds(10, 50, 598, 249);
		
		availableContainer.add(roomScroll);
		
		contentPane.addTab("Available rooms", availableContainer);
		
		CheckOutControl checkOutControl = new CheckOutControl(managers);
		
		filterCb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String value = (String) filterCb.getSelectedItem();
				ArrayList<Reservation> sorted = new ArrayList<>();
				if (value.contains("ascending")) {
					sorted = reservationControl.sortAscending(managers.getReservationManager().readAllReservations());
				} else if (value.contains("descending")) {
					sorted = reservationControl.sortDescending(managers.getReservationManager().readAllReservations());
				} else {
					sorted = managers.getReservationManager().readAllReservations();
				}
				tableModel.setRowCount(0);
				for (Reservation r : sorted) {
					String benefits = "";
					for (AdditionalService s : r.getBenefits()) {
						if (s != null) {
							benefits += s.getType() + ",";
						}
					}
					String room;
					if (r.getRoom() == null) {
						room = "";
					} else {
						room = String.valueOf(r.getRoom().getRoomNumber());
					}
					String date;
					if (r.getDateConfirmed() == null) {
						date = "";
					} else {
						date = formatManager.dateToString(r.getDateConfirmed());
					}
					tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
													formatManager.dateToString(r.getCheckOutDate()),
													r.getRoomType(), room,
													benefits,
													r.getStatus(), r.getPrice(), date});
				}
			}
		});
		
		btn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);
				for (Reservation r : reservations) {
					String benefits = "";
					for (AdditionalService s : r.getBenefits()) {
						if (s != null) {
							benefits += s.getType() + ",";
						}
					}
					String room;
					if (r.getRoom() == null) {
						room = "";
					} else {
						room = String.valueOf(r.getRoom().getRoomNumber());
					}
					String date;
					if (r.getDateConfirmed() == null) {
						date = "";
					} else {
						date = formatManager.dateToString(r.getDateConfirmed());
					}
					tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
													formatManager.dateToString(r.getCheckOutDate()),
													r.getRoomType(), room,
													benefits,
													r.getStatus(), r.getPrice(), date});
				}
			}
		});
		
		benefitsList.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					ArrayList<AdditionalService> services = new ArrayList<>();
					for (int i = 0; i < allServices.size(); i++) {
						if (benefitsList.isSelectedIndex(i)) {
							services.add(allServices.get(i));
						}
					}
					tableModel.setRowCount(0);
					for (Map.Entry<Integer, Reservation> set : managers.getReservationManager().getAllReservations().entrySet()) {
						if (set.getValue().getBenefits().containsAll(services)) {
							Reservation r = set.getValue();
							String benefits = "";
							for (AdditionalService s : r.getBenefits()) {
								if (s != null) {
									benefits += s.getType() + ",";
								}
							}
							String room;
							if (r.getRoom() == null) {
								room = "";
							} else {
								room = String.valueOf(r.getRoom().getRoomNumber());
							}
							String date;
							if (r.getDateConfirmed() == null) {
								date = "";
							} else {
								date = formatManager.dateToString(r.getDateConfirmed());
							}
							tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
															formatManager.dateToString(r.getCheckOutDate()),
															r.getRoomType(), room,
															benefits,
															r.getStatus(), r.getPrice(), date});
						}
					}
				}
			}
		});
		
		roomCb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);
				String value = (String) roomCb.getSelectedItem();
				RoomType type;
				if (value.equals("single bed")) {
					type = RoomType.SINGLE_BED;
				} else if (value.equals("double bed")) {
					type = RoomType.DOUBLE_BED;
				} else if (value.equals("king bed")) {
					type = RoomType.KING_BED;
				} else if (value.equals("triple bed")) {
					type = RoomType.TRIPLE_BED;
				} else {
					type = RoomType.QUAD_BED;
				}
				for (Map.Entry<Integer, Reservation> set : managers.getReservationManager().getAllReservations().entrySet()) {
					if (set.getValue().getRoomType().equals(type)) {
						Reservation r = set.getValue();
						String benefits = "";
						for (AdditionalService s : r.getBenefits()) {
							if (s != null) {
								benefits += s.getType() + ",";
							}
						}
						String room;
						if (r.getRoom() == null) {
							room = "";
						} else {
							room = String.valueOf(r.getRoom().getRoomNumber());
						}
						String date;
						if (r.getDateConfirmed() == null) {
							date = "";
						} else {
							date = formatManager.dateToString(r.getDateConfirmed());
						}
						tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
														formatManager.dateToString(r.getCheckOutDate()),
														r.getRoomType(), room,
														benefits,
														r.getStatus(), r.getPrice(), date});
					}
				}
			}
		});
		
		roomNumberCb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tableModel.setRowCount(0);
				String value = (String) roomNumberCb.getSelectedItem();
				for (Map.Entry<Integer, Reservation> set : managers.getReservationManager().getAllReservations().entrySet()) {
					if (set.getValue().getRoom() != null) {
						if (set.getValue().getRoom().getRoomNumber() == Integer.parseInt(value)) {
							Reservation r = set.getValue();
							String benefits = "";
							for (AdditionalService s : r.getBenefits()) {
								if (s != null) {
									benefits += s.getType() + ",";
								}
							}
							String room;
							if (r.getRoom() == null) {
								room = "";
							} else {
								room = String.valueOf(r.getRoom().getRoomNumber());
							}
							String date;
							if (r.getDateConfirmed() == null) {
								date = "";
							} else {
								date = formatManager.dateToString(r.getDateConfirmed());
							}
							tableModel.addRow(new Object[] {String.valueOf(r.getId()), formatManager.dateToString(r.getCheckInDate()),
															formatManager.dateToString(r.getCheckOutDate()),
															r.getRoomType(), room,
															benefits,
															r.getStatus(), r.getPrice(), date});
						}
					}
				}
			}
		});
		
		btnNewReservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ReservationFrame(managers, frame, receptionist);
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
		
		btnCheckOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object s = (String) checkOutDropDown.getEditor().getItem();
				int id = -1;
				for (String i : checkOutIDs) {
					if (s.equals(i)) {
						id = Integer.parseInt(i);
						break;
					}
				}
				checkOutControl.checkOutProcess(managers.getReservationManager().findReservationByID(id));
				frame.dispose();
				new ReceptionistFrame(managers, receptionist);
				new InfoDialog(managers, "Check out successful!!!");
			}
		});
		
		btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object s = (String) dropDown.getEditor().getItem();
				int id = -1;
				for (String i : reservationIDs) {
					if (s.equals(i)) {
						id = Integer.parseInt(i);
					}
				}
				String message = reservationControl.checkReservation(managers.getReservationManager().findReservationByID(id));
				frame.dispose();
				new ReceptionistFrame(managers, receptionist);
				new InfoDialog(managers, message);
			}
		});
		
		btnCheckIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object s = (String) checkInDropDown.getEditor().getItem();
				int id = -1;
				for (String i : checkInIDs) {
					if (s.equals(i)) {
						id = Integer.parseInt(i);
						break;
					}
				}
				ArrayList<AdditionalService> chosenServices = new ArrayList<>();
				for (int i = 0; i < allServices.size(); i++) {
					if (addServiceList.isSelectedIndex(i)) {
						chosenServices.add(allServices.get(i));
					}
				}
				if (managers.getReservationManager().findReservationByID(id).getGuest() == null) {
					new GuestCRUDFrame(managers, frame, receptionist, 
							managers.getReservationManager().findReservationByID(id), chosenServices, null);
				} else {
					checkInControl.checkInProcess(id, chosenServices);
					frame.dispose();
					new ReceptionistFrame(managers, receptionist);
					new InfoDialog(managers, "<html>Check in successful!!!<br>Price: " + 
							String.valueOf(managers.getReservationManager().findReservationByID(id).getPrice()) + "</html>");
				}
			}
		});
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}

	public Receptionist getReceptionist() {
		return receptionist;
	}

	public void setReceptionist(Receptionist receptionist) {
		this.receptionist = receptionist;
	}
}
