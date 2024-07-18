package entity;

import java.util.ArrayList;
import java.util.Date;

public class Maid extends Employee{
	private ArrayList<Room> roomsToClean = new ArrayList<>();
	final EmployeeTitle title = EmployeeTitle.MAID;
	private ArrayList<Date> cleanedRooms;
	
	public Maid() {
		super();
		this.setTitle(EmployeeTitle.MAID);
		this.cleanedRooms = null;
	}
	
	public Maid(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		this.setTitle(EmployeeTitle.MAID);
		this.cleanedRooms = null;
	}
	
	public Maid(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password, 
			EmployeeTitle title, int levelOfEducation, int yearsOfExperience, double salary) {
		super(id, firstName, lastName, gender, dateOfBirth, phoneNumber, adress, username, password, 
				title, levelOfEducation, yearsOfExperience, salary);
		this.cleanedRooms = null;
	}
	
	public Maid(ArrayList<Room> rooms) {
		this.roomsToClean = rooms;
	}

	public ArrayList<Room> getRoomsToClean() {
		return roomsToClean;
	}

	public void setRoomsToClean(ArrayList<Room> roomsToClean) {
		this.roomsToClean = roomsToClean;
	}

	public ArrayList<Date> getCleanedRooms() {
		return cleanedRooms;
	}

	public void setCleanedRooms(ArrayList<Date> cleanedRooms) {
		this.cleanedRooms = cleanedRooms;
	}
	
	
		
}
