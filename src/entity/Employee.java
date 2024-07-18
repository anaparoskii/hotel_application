package entity;

import java.util.Date;

import manager.FormatManager;

public abstract class Employee extends Person {
	private int id;
	private EmployeeTitle title;
	private int levelOfEducation;
	private int yearsOfExperience;
	private double salary;
	private boolean work;
	private Date dateStartedWork;
	private Date dateFinishedWork;
	
	public Employee() {
		super();
	}
	
	public Employee(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password, 
			EmployeeTitle title, int levelOfEducation, int yearsOfExperience, double salary) {
		super(firstName, lastName, gender, dateOfBirth, phoneNumber, adress, username, password);
		this.title = title;
		this.levelOfEducation = levelOfEducation;
		this.yearsOfExperience = yearsOfExperience;
		this.salary = salary;	
		this.id = id;
	}
	
	public Employee(int id, String firstName, String lastName, EmployeeTitle title) {
		super(firstName, lastName);
		this.title = title; 
		this.id = id;
	}
	
	public Employee(int id, String firstName, String lastName) {
		super(firstName, lastName);
		this.id = id;
	}
	
	@Override
	public String toString() {
		String s = String.format("First name: %-15s Last name: %-15s Title: %-15s", this.getFirstName(), this.getLastName(), this.getTitle());
		return s;
	}
	
	public String toFileString() {
		String idString = String.valueOf(this.id);
		FormatManager formatManager = new FormatManager();
		String date = formatManager.dateToString(getDateOfBirth());
		String dateFinished = "";
		if (this.getDateFinishedWork() != null) {
			dateFinished = formatManager.dateToString(this.getDateFinishedWork());
		}
		return idString + ";" + this.getFirstName() + ";" + this.getLastName() + ";" + this.getTitle() + ";" + this.getGender() + ";" 
				+ date + ";" + this.getPhoneNumber() + ";" + this.getAdress() + ";" + this.getUsername() + ";" 
				+ this.getPassword() + ";" + String.valueOf(this.getLevelOfEducation()) + ";"
				+ String.valueOf(this.getYearsOfExperience()) + ";" + String.valueOf(this.getSalary() + ";" + this.isWork() + ";"
				+ formatManager.dateToString(this.getDateStartedWork()) + ";" + dateFinished);
	}

	public EmployeeTitle getTitle() {
		return title;
	}

	public void setTitle(EmployeeTitle title) {
		this.title = title;
	}

	public int getLevelOfEducation() {
		return levelOfEducation;
	}

	public void setLevelOfEducation(int levelOfEducation) {
		this.levelOfEducation = levelOfEducation;
	}

	public int getYearsOfExperience() {
		return yearsOfExperience;
	}

	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

  public Date getDateStartedWork() {
    return dateStartedWork;
  }

  public void setDateStartedWork(Date dateStartedWork) {
    this.dateStartedWork = dateStartedWork;
  }

public Date getDateFinishedWork(){return dateFinishedWork;}

public void setDateFinishedWork(Date dateFinishedWork){this.dateFinishedWork = dateFinishedWork;}

public boolean isWork(){return work;}

public void setWork(boolean work){this.work = work;}
}
