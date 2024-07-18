package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LoginControl;
import entity.Admin;
import entity.Employee;
import entity.EmployeeTitle;
import entity.Guest;
import entity.Maid;
import entity.Receptionist;
import manager.ManagerFactory;

public class LoginDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	
	public LoginDialog(ManagerFactory managers) {
		this.managers = managers;
		loginDialog();
	}

	void loginDialog() {
		JDialog d = new JDialog();
		d.setTitle("Prijava");
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setSize(250, 250);
		d.setLocation(300, 200);
		d.setResizable(false);
		initLogin(d);
		d.setModal(true);
		d.setVisible(true);
	}
	
	private void initLogin(JDialog dialog) { 
		dialog.getContentPane().setLayout(null);
		
		JTextField tfKorisnickoIme = new JTextField(20);
		tfKorisnickoIme.setSize(214, 20);
		tfKorisnickoIme.setLocation(10, 85);
		JPasswordField pfLozinka = new JPasswordField(20);
		pfLozinka.setSize(214, 20);
		pfLozinka.setLocation(10, 135);
		JButton btnOk = new JButton("OK");
		btnOk.setSize(100, 30);
		btnOk.setLocation(10, 166);
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setSize(104, 30);
		btnCancel.setLocation(120, 166);
		JLabel loginFailed = new JLabel("<html><b>Username or password doesn't exist!</b></html>");
		loginFailed.setForeground(new Color(210, 0, 0));
		loginFailed.setSize(214, 30);
		loginFailed.setLocation(10, 37);
		loginFailed.setVisible(false);

		JLabel lblwelcomePleaseLog = new JLabel("<html><b>Welcome! Please log in</b></html>");
		lblwelcomePleaseLog.setSize(180, 30);
		lblwelcomePleaseLog.setLocation(44, 10);
		dialog.getContentPane().add(lblwelcomePleaseLog);
		dialog.getContentPane().add(loginFailed);
		JLabel label_1 = new JLabel("Username");
		label_1.setSize(135, 25);
		label_1.setLocation(89, 65);
		dialog.getContentPane().add(label_1);
		dialog.getContentPane().add(tfKorisnickoIme);
		JLabel label_2 = new JLabel("Password");
		label_2.setSize(135, 25);
		label_2.setLocation(89, 114);
		dialog.getContentPane().add(label_2);
		dialog.getContentPane().add(pfLozinka);
		JLabel label_3 = new JLabel();
		label_3.setSize(0, 0);
		label_3.setLocation(0, 0);
		dialog.getContentPane().add(label_3);
		dialog.getContentPane().add(btnOk);
		dialog.getContentPane().add(btnCancel);
		
		dialog.getRootPane().setDefaultButton(btnOk);

		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginControl loginControl = new LoginControl(managers);
				String korisnickoIme = tfKorisnickoIme.getText().trim();
				String lozinka = new String(pfLozinka.getPassword()).trim();
				System.out.println(korisnickoIme+" "+lozinka);
				if (loginControl.loginGuestSuccess(korisnickoIme, lozinka)) {
					dialog.setVisible(false);
					dialog.dispose();
					Guest guest = loginControl.whichGuest(korisnickoIme, lozinka);
					new GuestFrame(managers, guest);
				}
				else if (loginControl.loginEmployeeSuccess(korisnickoIme, lozinka)) {
					dialog.setVisible(false);
					dialog.dispose();
					Employee employee = loginControl.whichEmployee(korisnickoIme, lozinka);
					if (employee.getTitle().equals(EmployeeTitle.RECEPTIONIST)) {
						new ReceptionistFrame(managers, (Receptionist) employee);
					} else if (employee.getTitle().equals(EmployeeTitle.MAID)) {
						new MaidFrame(managers, (Maid) employee);
					} else {
						new AdminFrame(managers, (Admin) employee);
					}
				}
				else {
					loginFailed.setVisible(true);
				}
			}
		});
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
				dialog.dispose();
				managers.saveData();
				System.exit(0);
			}
		});

	}

}
