package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import entity.Admin;
import entity.Employee;
import entity.EmployeeTitle;
import entity.Gender;
import entity.Maid;
import entity.Receptionist;
import manager.FormatManager;
import manager.ManagerFactory;

public class EmployeeCRUDFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	private JFrame adminFrame;
	private Admin admin;
	private Employee employee;
	
	public EmployeeCRUDFrame(ManagerFactory managers, JFrame frame, Admin admin, Employee employee) {
		this.managers = managers;
		this.adminFrame = frame;
		this.admin = admin;
		this.employee = employee;
		createEmployee();
	}
	
	private void createEmployee() {
		JFrame main = new JFrame();
		String title = String.format("New Employee");
		main.setTitle(title);
		main.setSize(300, 500);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setAlwaysOnTop(true);
		initCreate(main);
		main.setVisible(true);
	}
	
	private void initCreate(JFrame frame) {
		Container content = frame.getContentPane();
		content.setLayout(null);
		
		FormatManager formatManager = new FormatManager();
		
		String[] titles = new String[3];
		int i = 0;
		for (EmployeeTitle t : EmployeeTitle.values()) {
			titles[i] = t.toString();
			i++;
		}
		
		ButtonGroup bg = new ButtonGroup();
		JRadioButton maleRb = new JRadioButton("male");
		JRadioButton femaleRb = new JRadioButton("female");
		bg.add(maleRb);
		bg.add(femaleRb);
		maleRb.setBounds(59, 113, 75, 30);
		femaleRb.setBounds(136, 113, 75, 30);
		content.add(maleRb);
		content.add(femaleRb);
		
		JLabel label = new JLabel("Registration form");
		label.setSize(180, 30);
		label.setLocation(10, 11);
		content.add(label);
		
		JLabel lblIme = new JLabel("Name: ");
		lblIme.setBounds(10, 50, 124, 30);
		content.add(lblIme);
		
		TextField imeTf;
		TextField prezimeTf;
		TextField datumTf;
		JComboBox<String> titleCb;
		TextField telefonTf;
		TextField adresaTf;
		TextField usernameTf;
		TextField passwordTf;
		TextField educationTf;
		TextField experienceTf;
		
		if (employee == null) {
			imeTf = new TextField();
			prezimeTf = new TextField();
			datumTf = new TextField();
			titleCb = new JComboBox<>(titles);
			telefonTf = new TextField();
			adresaTf = new TextField();
			usernameTf = new TextField();
			passwordTf = new TextField();
			educationTf = new TextField();
			experienceTf = new TextField();
		} else {
			imeTf = new TextField(employee.getFirstName());
			prezimeTf = new TextField(employee.getLastName());
			datumTf = new TextField(formatManager.dateToString(employee.getDateOfBirth()));
			String[] title = {String.valueOf(employee.getTitle())};
			titleCb = new JComboBox<>(title);
			if (String.valueOf(employee.getGender()).equals("male")) {
				maleRb.setSelected(true);
			} else {
				femaleRb.setSelected(true);
			}
			telefonTf = new TextField(employee.getPhoneNumber());
			adresaTf = new TextField(employee.getAdress());
			usernameTf = new TextField(employee.getUsername());
			passwordTf = new TextField(employee.getPassword());
			educationTf = new TextField(String.valueOf(employee.getLevelOfEducation()));
			experienceTf = new TextField(String.valueOf(employee.getYearsOfExperience()));
		}
		
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
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setBounds(150, 154, 124, 30);
		content.add(lblTitle);
		
		titleCb.setBounds(150, 180, 124, 30);
		content.add(titleCb);		
		
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
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(10, 279, 124, 30);
		content.add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(150, 279, 124, 30);
		content.add(lblPassword);
		
		usernameTf.setForeground(Color.BLACK);
		usernameTf.setBounds(10, 305, 124, 30);
		content.add(usernameTf);
		
		passwordTf.setForeground(Color.BLACK);
		passwordTf.setBounds(150, 305, 124, 30);
		content.add(passwordTf);
		
		JLabel lblEducation = new JLabel("Education level:");
		lblEducation.setBounds(10, 340, 124, 30);
		content.add(lblEducation);
		
		JLabel lblExperience = new JLabel("Work experience:");
		lblExperience.setBounds(150, 340, 124, 30);
		content.add(lblExperience);
		
		educationTf.setBounds(10, 365, 124, 30);
		content.add(educationTf);
		
		experienceTf.setBounds(150, 365, 124, 30);
		content.add(experienceTf);
		
		JButton btnRegister = new JButton("CONFIRM");
		btnRegister.setBounds(179, 420, 95, 30);
		content.add(btnRegister);
		
		btnRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (employee == null) {
					Gender gender;
					if (maleRb.isSelected()) {
						gender = Gender.MALE;
					} else {
						gender = Gender.FEMALE;
					}
					Object s = (String) titleCb.getEditor().getItem();
					if (s.equals("maid")) {
						managers.getEmployeeManager().createMaid(managers.getEmployeeManager().findNextEmployeeID(),
								imeTf.getText(), prezimeTf.getText(), gender, formatManager.asDate(datumTf.getText()), 
								telefonTf.getText(), adresaTf.getText(), 
								usernameTf.getText(), passwordTf.getText(), Integer.parseInt(educationTf.getText()), 
								Integer.parseInt(experienceTf.getText()), 50000.00);
					} else if (s.equals("administrator")) {
						managers.getEmployeeManager().createAdmin(managers.getEmployeeManager().findNextEmployeeID(),
								imeTf.getText(), prezimeTf.getText(), gender, formatManager.asDate(datumTf.getText()), 
								telefonTf.getText(), adresaTf.getText(), 
								usernameTf.getText(), passwordTf.getText(), Integer.parseInt(educationTf.getText()), 
								Integer.parseInt(experienceTf.getText()), 50000.00);
					} else {
						managers.getEmployeeManager().createReceptionist(managers.getEmployeeManager().findNextEmployeeID(),
								imeTf.getText(), prezimeTf.getText(), gender, formatManager.asDate(datumTf.getText()), 
								telefonTf.getText(), adresaTf.getText(), 
								usernameTf.getText(), passwordTf.getText(), Integer.parseInt(educationTf.getText()), 
								Integer.parseInt(experienceTf.getText()), 50000.00);
					}
					
					frame.dispose();
					adminFrame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Employee added");
				} else {
					Gender gender;
					if (maleRb.isSelected()) {
						gender = Gender.MALE;
					} else {
						gender = Gender.FEMALE;
					}
					if (employee.getTitle().equals(EmployeeTitle.RECEPTIONIST)) {
						Receptionist receptionist = new Receptionist(employee.getId(), 
								imeTf.getText(), prezimeTf.getText(), gender, 
								formatManager.asDate(datumTf.getText()), telefonTf.getText(), adresaTf.getText(), 
								usernameTf.getText(), passwordTf.getText(), employee.getTitle(), Integer.parseInt(educationTf.getText()), 
								Integer.parseInt(experienceTf.getText()), 50000.00);
						receptionist.setWork(true);
						receptionist.setDateStartedWork(employee.getDateStartedWork());
						managers.getEmployeeManager().getAllEmployees().replace(employee.getId(), receptionist);
					} else if (employee.getTitle().equals(EmployeeTitle.MAID)) {
						Maid maid = new Maid(employee.getId(), 
								imeTf.getText(), prezimeTf.getText(), gender, 
								formatManager.asDate(datumTf.getText()), telefonTf.getText(), adresaTf.getText(), 
								usernameTf.getText(), passwordTf.getText(), employee.getTitle(), Integer.parseInt(educationTf.getText()), 
								Integer.parseInt(experienceTf.getText()), 50000.00);
						maid.setWork(true);
						maid.setDateStartedWork(employee.getDateStartedWork());
						managers.getEmployeeManager().getAllEmployees().replace(employee.getId(), maid);
					} else {
						Admin admin = new Admin(employee.getId(), 
								imeTf.getText(), prezimeTf.getText(), gender, 
								formatManager.asDate(datumTf.getText()), telefonTf.getText(), adresaTf.getText(), 
								usernameTf.getText(), passwordTf.getText(), employee.getTitle(), Integer.parseInt(educationTf.getText()), 
								Integer.parseInt(experienceTf.getText()), 50000.00);
						admin.setWork(true);
						admin.setDateStartedWork(employee.getDateStartedWork());
						managers.getEmployeeManager().getAllEmployees().replace(employee.getId(), admin);
					}
					
					frame.dispose();
					adminFrame.dispose();
					new AdminFrame(managers, admin);
					new InfoDialog(managers, "Employee updated");
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

	public JFrame getAdminFrame() {
		return adminFrame;
	}

	public void setAdminFrame(JFrame adminFrame) {
		this.adminFrame = adminFrame;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
