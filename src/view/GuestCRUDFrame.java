package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import controller.CheckInControl;
import entity.AdditionalService;
import entity.Admin;
import entity.Employee;
import entity.Gender;
import entity.Guest;
import entity.Receptionist;
import entity.Reservation;
import manager.FormatManager;
import manager.ManagerFactory;

public class GuestCRUDFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	private JFrame employeeFrame;
	private Employee employee;
	private Reservation reservation;
	private ArrayList<AdditionalService> services;
	private Guest guest;
	
	public GuestCRUDFrame(ManagerFactory managers, JFrame frame, Employee employee, 
			Reservation reservation, ArrayList<AdditionalService> services, Guest guest) {
		this.managers = managers;
		this.employeeFrame = frame;
		this.employee = employee;
		this.reservation = reservation;
		this.services = services;
		this.guest = guest;
		registerGuest();
	}
	
	private void registerGuest() {
		JFrame main = new JFrame();
		String title = String.format("New Guest");
		main.setTitle(title);
		main.setSize(300, 450);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setAlwaysOnTop(true);
		initRegister(main);
		main.setVisible(true);
	}
	
	private void initRegister(JFrame frame) {
		Container content = frame.getContentPane();
		content.setLayout(null);
		
		FormatManager formatManager = new FormatManager();
		
		JLabel label = new JLabel("Registration form");
		label.setSize(180, 30);
		label.setLocation(10, 11);
		content.add(label);
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton maleRb = new JRadioButton("male");
		JRadioButton femaleRb = new JRadioButton("female");
		bg.add(maleRb);
		bg.add(femaleRb);
		maleRb.setBounds(59, 113, 75, 30);
		femaleRb.setBounds(136, 113, 75, 30);
		content.add(maleRb);
		content.add(femaleRb);
		
		TextField imeTf;
		TextField prezimeTf;
		TextField datumTf;
		TextField telefonTf;
		TextField adresaTf;
		TextField emailTf;
		TextField pasosTf;
		
		if (guest == null) {
			imeTf = new TextField();
			prezimeTf = new TextField();
			datumTf = new TextField();
			telefonTf = new TextField();
			adresaTf = new TextField();
			emailTf = new TextField();
			pasosTf = new TextField();
		} else {
			imeTf = new TextField(guest.getFirstName());
			prezimeTf = new TextField(guest.getLastName());
			datumTf = new TextField(formatManager.dateToString(guest.getDateOfBirth()));
			telefonTf = new TextField(guest.getPhoneNumber());
			adresaTf = new TextField(guest.getAdress());
			emailTf = new TextField(guest.getUsername());
			pasosTf = new TextField(guest.getPassword());
			if (String.valueOf(guest.getGender()).equals("male")) {
				maleRb.setSelected(true);
			} else {
				femaleRb.setSelected(true);
			}
		}
		
		JLabel lblIme = new JLabel("Name: ");
		lblIme.setBounds(10, 50, 124, 30);
		content.add(lblIme);
		
		imeTf.setForeground(Color.BLACK);
		imeTf.setBounds(10, 77, 124, 30);
		content.add(imeTf);
		
		JLabel lblPrezime = new JLabel("Last Name: ");
		lblPrezime.setBounds(150, 50, 124, 30);
		content.add(lblPrezime);
		
		prezimeTf.setForeground(Color.BLACK);
		prezimeTf.setBounds(150, 77, 124, 30);
		content.add(prezimeTf);
		
		JLabel lblPol = new JLabel("Gender: ");
		lblPol.setBounds(10, 113, 65, 30);
		content.add(lblPol);
		
		JLabel lblDatum = new JLabel("Date of Birth: ");
		lblDatum.setBounds(10, 154, 124, 30);
		content.add(lblDatum);
		
		datumTf.setForeground(Color.BLACK);
		datumTf.setBounds(10, 180, 124, 30);
		content.add(datumTf);	
		
		JLabel lblBrojTelefona = new JLabel("Phone number:");
		lblBrojTelefona.setBounds(10, 216, 124, 30);
		content.add(lblBrojTelefona);
		
		JLabel lblAdresa = new JLabel("Address:");
		lblAdresa.setBounds(150, 216, 124, 30);
		content.add(lblAdresa);
		
		telefonTf.setForeground(Color.BLACK);
		telefonTf.setBounds(10, 243, 124, 30);
		content.add(telefonTf);
		
		adresaTf.setForeground(Color.BLACK);
		adresaTf.setBounds(150, 243, 124, 30);
		content.add(adresaTf);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 279, 124, 30);
		content.add(lblEmail);
		
		JLabel lblPasos = new JLabel("Passport no:");
		lblPasos.setBounds(150, 279, 124, 30);
		content.add(lblPasos);
		
		emailTf.setForeground(Color.BLACK);
		emailTf.setBounds(10, 305, 124, 30);
		content.add(emailTf);
		
		pasosTf.setForeground(Color.BLACK);
		pasosTf.setBounds(150, 305, 124, 30);
		content.add(pasosTf);
		
		JButton btnRegister = new JButton("CONFIRM");
		btnRegister.setBounds(179, 370, 95, 30);
		content.add(btnRegister);
		
		CheckInControl checkInControl = new CheckInControl(managers);
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (guest == null) {
					Gender gender;
					if (maleRb.isSelected()) {
						gender = Gender.MALE;
					} else {
						gender = Gender.FEMALE;
					}
					Guest guest = new Guest(managers.getGuestManager().findNextGuestID(), imeTf.getText(), prezimeTf.getText(), gender, 
							formatManager.asDate(datumTf.getText()), telefonTf.getText(), adresaTf.getText(), 
							emailTf.getText(), pasosTf.getText());
					managers.getGuestManager().getAllGuests().put(managers.getGuestManager().findNextGuestID(), guest);
					reservation.setGuest(guest);
					checkInControl.checkInProcess(reservation.getId(), services);
					frame.dispose();
					employeeFrame.dispose();
					new ReceptionistFrame(managers, (Receptionist) employee);
					new InfoDialog(managers, "<html>Check in successful!!!<br>Price: " + 
							String.valueOf(reservation.getPrice()) + "</html>");
				} else {
					Gender gender;
					if (maleRb.isSelected()) {
						gender = Gender.MALE;
					} else {
						gender = Gender.FEMALE;
					}
					managers.getGuestManager().getAllGuests().replace(guest.getId(), new Guest(guest.getId(), imeTf.getText(), 
							prezimeTf.getText(), gender, formatManager.asDate(datumTf.getText()), telefonTf.getText(), adresaTf.getText(), 
							emailTf.getText(), pasosTf.getText()));
					frame.dispose();
					employeeFrame.dispose();
					new AdminFrame(managers, (Admin) employee);
					new InfoDialog(managers, "Guest updated");
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
	public JFrame getEmployeeFrame() {
		return employeeFrame;
	}
	public void setEmployeeFrame(JFrame employeeFrame) {
		this.employeeFrame = employeeFrame;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public ArrayList<AdditionalService> getServices() {
		return services;
	}

	public void setServices(ArrayList<AdditionalService> services) {
		this.services = services;
	}
	
	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}
}
