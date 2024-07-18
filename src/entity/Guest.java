package entity;

import java.util.Date;

import manager.FormatManager;

public class Guest extends Person{
	private int id;
	
	public Guest() {
		super();
	}
	
	public Guest(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password) {
		super(firstName, lastName, gender, dateOfBirth, phoneNumber, adress, username, password);
		this.id = id;
	}
	
	public Guest(int id, String firstName, String lastName) {
		super(firstName, lastName);
		this.id = id;
	}
	
	@Override
	public String toString() {
		String s = String.format("First name: %-15s Last name: %-15s", this.getFirstName(), this.getLastName());
		return s;
	}
	
	public String toFileString() {
		String idString = String.valueOf(this.id);
		FormatManager formatManager = new FormatManager();
		String date = formatManager.dateToString(getDateOfBirth());
		return idString + ";" + this.getFirstName() + ";" + this.getLastName() + ";" + this.getGender() + ";" 
				+ date + ";" + this.getPhoneNumber() + ";" + this.getAdress() + ";" + this.getUsername() + ";" 
				+ this.getPassword();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
