package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.Admin;
import entity.Employee;
import entity.EmployeeTitle;
import entity.Gender;
import entity.Maid;
import entity.Receptionist;

public class EmployeeManager {
	
	private HashMap<Integer, Employee> allEmployees = new HashMap<>();
	private String employeeFileName;
	
	public EmployeeManager() {
		this.allEmployees = new HashMap<>();
	}
	
	public EmployeeManager(String employeeFileName) {
		this.employeeFileName = employeeFileName;
		this.allEmployees = new HashMap<>();
	}
	
	public HashMap<Integer, Employee> getAllEmployees() {
		return allEmployees;
	}

	public void setAllEmployees(HashMap<Integer, Employee> allEmployees) {
		this.allEmployees = allEmployees;
	}

	public void createAdmin(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password,
			int levelOfEducation, int yearsOfExperience, double salary) {
		FormatManager formatManager = new FormatManager();
		Admin admin = new Admin(id, firstName, lastName, gender, dateOfBirth, 
				phoneNumber, adress, username, password, EmployeeTitle.ADMINISTRATOR, levelOfEducation, yearsOfExperience, salary);
		admin.setWork(true);
		admin.setDateStartedWork(formatManager.asDate(LocalDate.now()));
		this.allEmployees.put(id, admin);
	}
	
	public void createReceptionist(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password,
			int levelOfEducation, int yearsOfExperience, double salary) {
		FormatManager formatManager = new FormatManager();
		Receptionist receptionist = new Receptionist(id, firstName, lastName, gender, dateOfBirth, 
				phoneNumber, adress, username, password, EmployeeTitle.RECEPTIONIST, levelOfEducation, yearsOfExperience, salary);
		receptionist.setWork(true);
		receptionist.setDateStartedWork(formatManager.asDate(LocalDate.now()));
		this.allEmployees.put(id, receptionist);
	}
	
	public void createMaid(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password,
			int levelOfEducation, int yearsOfExperience, double salary) {
		FormatManager formatManager = new FormatManager();
		Maid maid = new Maid(id, firstName, lastName, gender, dateOfBirth, 
				phoneNumber, adress, username, password, EmployeeTitle.MAID, levelOfEducation, yearsOfExperience, salary);
		maid.setWork(true);
		maid.setDateStartedWork(formatManager.asDate(LocalDate.now()));
		this.allEmployees.put(id, maid);
	}
	
	public ArrayList<Employee> readActiveEmployees() {
		ArrayList<Employee> allEmployees = new ArrayList<>();
		System.out.println("*** EMPLOYEES ***");
		for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
			if (set.getValue().isWork()) {
				int id = set.getKey();
				Employee employee = set.getValue();
				System.out.printf("%d: %s", id, employee.toString());
				System.out.println();
				allEmployees.add(employee);
			}
		}
		System.out.println();
		return allEmployees;
	}
	
	public ArrayList<Employee> readAllEmployees() {
		ArrayList<Employee> allEmployees = new ArrayList<>();
		System.out.println("*** EMPLOYEES ***");
		for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
			int id = set.getKey();
			Employee employee = set.getValue();
			System.out.printf("%d: %s", id, employee.toString());
			System.out.println();
			allEmployees.add(employee);
		}
		System.out.println();
		return allEmployees;
	}
	
	public void deleteEmployee(int id) {
		FormatManager formatManager = new FormatManager();
		Employee employeeToDelete = findEmployeeByID(id);
		if (employeeToDelete != null) {
			System.out.printf("Removed employee: %s %s - %s", 
					employeeToDelete.getFirstName(), employeeToDelete.getLastName(), employeeToDelete.getTitle());
			System.out.println();
			employeeToDelete.setWork(false);
			employeeToDelete.setDateFinishedWork(formatManager.asDate(LocalDate.now()));
			this.allEmployees.replace(id, employeeToDelete);
		}
		System.out.println();
	}
	
	public Employee findEmployeeByName(String firstName, String lastName) {
		for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
			Employee employee = set.getValue();
			if ((employee.getFirstName().equals(firstName)) && (employee.getLastName().equals(lastName))) {
				return employee;
			}
		}
		System.out.println("Couldn't find an employee with this name!");
		return null;
	}
	
	public int findEmployeeIDByName(String firstName, String lastName) {
		for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
			Employee employee = set.getValue();
			if ((employee.getFirstName().equals(firstName)) && (employee.getLastName().equals(lastName))) {
				return employee.getId();
			}
		}
		System.out.println("Couldn't find an employee with this name!");
		return -1;
	}
	
	public Employee findEmployeeByID(int id) {
		for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
			if (set.getKey() == id) {
				return this.allEmployees.get(id);
			}
		}
		System.out.println("Couldn't find an employee with this ID!");
		return null;
	}
	
	public int findNextEmployeeID() {
		int id = 0;
		if (this.allEmployees.size() == 0) {
			return 1000;
		} else {
			for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
				id = set.getKey() + 1;
			}
			return id;
		}
	}
	
	public HashMap<Integer, Maid> getAllMaids() {
		HashMap<Integer, Maid> allMaids = new HashMap<>();
		for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
			if (set.getValue().getTitle().equals(EmployeeTitle.MAID)) {
				allMaids.put(set.getKey(), (Maid) set.getValue());
			}
		}
		return allMaids;
	}

	public String getEmployeesFilename() {
		return employeeFileName;
	}

	public void setEmployeesFilename(String employeesFilename) {
		this.employeeFileName = employeesFilename;
	}
	
	public boolean loadData() {
		FormatManager formatManager = new FormatManager();
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.employeeFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				String title = token[3];
				Gender gender;
				if (token[4].equals("male")) {
					gender = Gender.MALE;
				} else {
					gender = Gender.FEMALE;
				}
				Employee employee = null;
				LocalDate dateOfBirth = LocalDate.parse(token[5], DateTimeFormatter.ofPattern("dd.MM.yyyy."));
				if (title.equals(EmployeeTitle.ADMINISTRATOR.toString())) {
					employee = new Admin(id, token[1], token[2], gender, formatManager.asDate(dateOfBirth), 
							token[6], token[7], token[8], token[9], EmployeeTitle.ADMINISTRATOR,
							Integer.parseInt(token[10]), Integer.parseInt(token[11]), Double.parseDouble(token[12]));
					System.out.println("admin učitan");
				}
				else if (title.equals(EmployeeTitle.RECEPTIONIST.toString())) {
					employee = new Receptionist(id, token[1], token[2], gender, formatManager.asDate(dateOfBirth), 
							token[6], token[7], token[8], token[9], EmployeeTitle.RECEPTIONIST,
							Integer.parseInt(token[10]), Integer.parseInt(token[11]), Double.parseDouble(token[12]));
					System.out.println("recepcioner učitan");
				}
				else if (title.equals(EmployeeTitle.MAID.toString())) {
					employee = new Maid(id, token[1], token[2], gender, formatManager.asDate(dateOfBirth), 
							token[6], token[7], token[8], token[9], EmployeeTitle.MAID,
							Integer.parseInt(token[10]), Integer.parseInt(token[11]), Double.parseDouble(token[12]));
					System.out.println("spremačica učitana");
				}
				employee.setWork(true);
				Date dateStart = formatManager.asDate(token[14]);
				employee.setDateStartedWork(dateStart);
				Date dateFinish = null;
				if (token.length > 15) {
					dateFinish = formatManager.asDate(token[15]);
					employee.setWork(false);
				}
				employee.setDateFinishedWork(dateFinish);
				this.allEmployees.put(id, employee);
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean saveData() {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.employeeFileName, false));
			for (Map.Entry<Integer, Employee> set : this.allEmployees.entrySet()) {
				pw.println(set.getValue().toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
