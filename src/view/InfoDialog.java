package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import manager.ManagerFactory;

public class InfoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private ManagerFactory managers;
	private String message;
	
	public InfoDialog(ManagerFactory managers, String message) {
		this.managers = managers;
		this.message = message;
		infoDialog();
	}

	void infoDialog() {
		JDialog d = new JDialog();
		d.setAlwaysOnTop(true);
		d.setLocationRelativeTo(null);
		d.setSize(250, 200);
		d.setLocation(300, 200);
		d.setResizable(false);
		initInfo(d);
		d.setModal(true);
		d.setVisible(true);
	}
	
	private void initInfo(JDialog dialog) {
		dialog.getContentPane().setLayout(null);
		
		JLabel information = new JLabel(this.message);
		information.setBounds(10, 11, 214, 59);
		dialog.getContentPane().add(information);
		
		JButton btnOK = new JButton("OK");
		btnOK.setBounds(70, 81, 90, 30);
		dialog.getContentPane().add(btnOK);
		
		btnOK.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dialog.dispose();
				dialog.setVisible(false);
			}
		});
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
