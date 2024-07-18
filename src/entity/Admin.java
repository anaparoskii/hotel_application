package entity;

import java.util.Date;

public class Admin extends Employee{
	final EmployeeTitle title = EmployeeTitle.ADMINISTRATOR;
	
	public Admin() {
		super();
		this.setTitle(EmployeeTitle.ADMINISTRATOR);
	}
	
	public Admin(int id, String firstName, String lastName) {
		super(id, firstName, lastName);
		this.setTitle(EmployeeTitle.ADMINISTRATOR);	
	}
	
	public Admin(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password, 
			EmployeeTitle title, int levelOfEducation, int yearsOfExperience, double salary) {
		super(id, firstName, lastName, gender, dateOfBirth, phoneNumber, adress, username, password, 
				title, levelOfEducation, yearsOfExperience, salary);
	}

}
