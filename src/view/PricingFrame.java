package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entity.Admin;
import manager.FormatManager;
import manager.ManagerFactory;

public class PricingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private ManagerFactory managers;
	private Admin admin;
	private JFrame adminFrame;
	
	public PricingFrame(ManagerFactory managers, Admin admin, JFrame frame) {
		this.managers = managers;
		this.admin = admin;
		this.adminFrame = frame;
		pricingFrame();
	}
	
	private void pricingFrame() {
		JFrame main = new JFrame();
		String title = String.format("New pricing");
		main.setTitle(title);
		main.setSize(250, 200);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setAlwaysOnTop(true);
		initPricing(main);
		main.setVisible(true);
	}
	
	private void initPricing(JFrame frame) {
		Container content = frame.getContentPane();
		content.setLayout(null);
		
		JLabel lblTitle = new JLabel("Create new pricing");
		lblTitle.setBounds(10, 10, 214, 30);
		content.add(lblTitle);
		
		JLabel lblStart = new JLabel("Start date: ");
		lblStart.setBounds(10, 39, 84, 30);
		content.add(lblStart);
		
		JTextField startTf = new JTextField();
		startTf.setBounds(104, 39, 120, 30);
		content.add(startTf);
		
		JLabel lblEnd = new JLabel("End date: ");
		lblEnd.setBounds(10, 80, 84, 30);
		content.add(lblEnd);
		
		JTextField endTf = new JTextField();
		endTf.setBounds(104, 80, 120, 30);
		content.add(endTf);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(159, 120, 65, 30);
		content.add(btnOk);
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FormatManager formatManager = new FormatManager();
				managers.getPricingManager().createPricing(formatManager.asLocalDate(startTf.getText()), 
														   formatManager.asLocalDate(endTf.getText()), 
														   managers.getServiceManager().getAllServices());
				frame.dispose();
				adminFrame.dispose();
				new AdminFrame(managers, admin);
				new InfoDialog(managers, "Pricing created successfully!");
			}
		});
	}
	
	public ManagerFactory getManagers() {
		return managers;
	}
	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}
	public Admin getAdmin() {
		return admin;
	}
	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	public JFrame getAdminFrame() {
		return adminFrame;
	}
	public void setAdminFrame(JFrame adminFrame) {
		this.adminFrame = adminFrame;
	}

}
