package view;

import javax.swing.JFrame;

import manager.ManagerFactory;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private ManagerFactory managers;
	
	public MainFrame(ManagerFactory managers) {
		this.managers = managers;
		new LoginDialog(managers);
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}

}
