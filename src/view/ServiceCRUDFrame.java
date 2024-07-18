package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entity.AdditionalService;
import entity.Admin;
import manager.ManagerFactory;

public class ServiceCRUDFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Admin admin;
	private ManagerFactory managers;
	private JFrame adminFrame;
	private AdditionalService service;
	
	public ServiceCRUDFrame(ManagerFactory managers, Admin admin, JFrame frame, AdditionalService service) {
		this.managers = managers;
		this.admin = admin;
		this.adminFrame = frame;
		this.service = service;
		serviceFrame();
	}
	
	private void serviceFrame() {
		JFrame main = new JFrame();
		String title = String.format("New room");
		main.setTitle(title);
		main.setSize(200, 225);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setAlwaysOnTop(true);
		initService(main);
		main.setVisible(true);
	}
	
	private void initService(JFrame frame) {
		Container content = frame.getContentPane();
		content.setLayout(null);
		
		JLabel lblTitle = new JLabel("Service form");
		lblTitle.setSize(110, 30);
		lblTitle.setLocation(10, 11);
		content.add(lblTitle);
		
		JLabel lblNewService = new JLabel("Service name:");
		lblNewService.setSize(110, 30);
		lblNewService.setLocation(10, 49);
		content.add(lblNewService);
		
		JTextField serviceNameTf;
		
		if (service == null) {
			serviceNameTf = new JTextField();
		} else {
			serviceNameTf = new JTextField(service.getType());
		}
		
		serviceNameTf.setSize(164, 30);
		serviceNameTf.setLocation(10, 90);
		content.add(serviceNameTf);
		
		JButton addServiceBtn = new JButton("CONFIRM");
		addServiceBtn.setSize(110, 30);
		addServiceBtn.setLocation(64, 145);
		content.add(addServiceBtn);
		
		addServiceBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (service == null) {
					managers.getServiceManager().createService(managers.getServiceManager().findNextServiceID(), serviceNameTf.getText());
					frame.dispose(); 
					adminFrame.dispose();
					new AdminFrame(managers, admin); 
					new InfoDialog(managers, "Service added successfully!");
				} else {
					managers.getServiceManager().getAllServices().replace(service.getId(), 
							new AdditionalService(service.getId(), serviceNameTf.getText()));
					frame.dispose(); 
					adminFrame.dispose();
					new AdminFrame(managers, admin); 
					new InfoDialog(managers, "Service updated successfully!");
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
	public AdditionalService getService() {
		return service;
	}
	public void setService(AdditionalService service) {
		this.service = service;
	}

}
