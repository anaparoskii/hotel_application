package entity;

import java.util.ArrayList;
import java.util.Date;

public class Receptionist extends Employee{
	private ArrayList<Reservation> allReservations = new ArrayList<>();
	
	public Receptionist() {
		super();
		this.setTitle(EmployeeTitle.RECEPTIONIST);
	}
	
	public Receptionist(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		this.setTitle(EmployeeTitle.RECEPTIONIST);
	}
	
	public Receptionist(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password, 
			EmployeeTitle title, int levelOfEducation, int yearsOfExperience, double salary) {
		super(id, firstName, lastName, gender, dateOfBirth, phoneNumber, adress, username, password, 
				title, levelOfEducation, yearsOfExperience, salary);
	}
	
	public Receptionist(ArrayList<Reservation> reservations) {
		this.allReservations = reservations;
	}

	public ArrayList<Reservation> getAllReservations() {
		return allReservations;
	}

	public void setAllReservations(ArrayList<Reservation> allReservations) {
		this.allReservations = allReservations;
	}

}
