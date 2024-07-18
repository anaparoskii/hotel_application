package controller;

import java.util.Map;

import entity.Employee;
import entity.Guest;
import manager.EmployeeManager;
import manager.GuestManager;
import manager.ManagerFactory;

public class LoginControl {
	
	private EmployeeManager employeeManager;
	private GuestManager guestManager;
	
	public LoginControl(ManagerFactory managers) {
		this.employeeManager = managers.getEmployeeManager();
		this.guestManager = managers.getGuestManager();
	}
	
	public boolean loginGuestSuccess(String username, String password) {
		for (Map.Entry<Integer, Guest> set : this.guestManager.getAllGuests().entrySet()) {
			if (set.getValue().getUsername().equals(username) && set.getValue().getPassword().equals(password)) { 
				return true; 
			}
		}
		return false;
	}
	
	public boolean loginEmployeeSuccess(String username, String password) {
		for (Map.Entry<Integer, Employee> set : this.employeeManager.getAllEmployees().entrySet()) { 
			if (set.getValue().getUsername().equals(username) && set.getValue().getPassword().equals(password)) { 
				return true; 
			} 
		}
		return false;
	}
	
	public Guest whichGuest(String username, String password) {
		for (Map.Entry<Integer, Guest> set : this.guestManager.getAllGuests().entrySet()) {
			if (set.getValue().getUsername().equals(username) && set.getValue().getPassword().equals(password)) { 
				return set.getValue();
			}
		}
		return null;
	}
	
	public Employee whichEmployee(String username, String password) {
		for (Map.Entry<Integer, Employee> set : this.employeeManager.getAllEmployees().entrySet()) { 
			if (set.getValue().getUsername().equals(username) && set.getValue().getPassword().equals(password)) { 
				return set.getValue(); 
			} 
		}
		return null;
	}
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}
	public GuestManager getGuestManager() {
		return guestManager;
	}
	public void setGuestManager(GuestManager guestManager) {
		this.guestManager = guestManager;
	}

}
