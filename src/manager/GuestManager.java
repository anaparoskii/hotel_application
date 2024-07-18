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

import entity.Gender;
import entity.Guest;

public class GuestManager {
	
	private HashMap<Integer, Guest> allGuests = new HashMap<>();
	private String guestFileName;
	
	public GuestManager() {
		this.allGuests = new HashMap<>();
	}
	
	public GuestManager(String guestFileName) {
		this.guestFileName = guestFileName;
		this.allGuests = new HashMap<>();
	}
	
	public HashMap<Integer, Guest> getAllGuests() {
		return allGuests;
	}

	public void setAllGuests(HashMap<Integer, Guest> allGuests) {
		this.allGuests = allGuests;
	}

	public void createGuest(int id, String firstName, String lastName, Gender gender, Date dateOfBirth, 
			String phoneNumber, String adress, String username, String password) {
		this.allGuests.put(id, new Guest(id, firstName, lastName, gender, dateOfBirth, phoneNumber, adress, username, password));
	}
	
	public ArrayList<Guest> readAllGuests() {
		System.out.println("*** GUESTS ***");
		ArrayList<Guest> allGuests = new ArrayList<>();
		for (Map.Entry<Integer, Guest> set : this.allGuests.entrySet()) {
			allGuests.add(set.getValue());		
		}
		return allGuests;
	}
	
	public void deleteGuest(int id) {
		Guest guestToDelete = findGuestByID(id);
		if (guestToDelete != null) {
			System.out.printf("Removed guest: %s %s", guestToDelete.getFirstName(), guestToDelete.getLastName());
			System.out.println();
			this.allGuests.remove(id);
		}
		System.out.println();
	}
	
	public Guest findGuestByName(String firstName, String lastName) {
		for (Map.Entry<Integer, Guest> set : this.allGuests.entrySet()) {
			Guest guest = set.getValue();
			if ((guest.getFirstName().equals(firstName)) && (guest.getLastName().equals(lastName))) {
				return guest;
			}
		}
		System.out.println("Couldn't find a guest with this name!");
		return null;
	}
	
	public int findGuestIDByName(String firstName, String lastName) {
		for (Map.Entry<Integer, Guest> set : this.allGuests.entrySet()) {
			Guest guest = set.getValue();
			if ((guest.getFirstName().equals(firstName)) && (guest.getLastName().equals(lastName))) {
				return guest.getId();
			}
		}
		System.out.println("Couldn't find a guest with this name!");
		return -1;
	}
	
	public Guest findGuestByID(int id) {
		for (Map.Entry<Integer, Guest> set : this.allGuests.entrySet()) {
			if (set.getKey() == id) {
				return this.allGuests.get(id);
			}
		}
		System.out.println("Couldn't find a guest with this ID!");
		return null;
	}
	
	public int findNextGuestID() {
		int id = 0;
		if (this.allGuests.size() == 0) {
			return 1000;
		} else {
			for (Map.Entry<Integer, Guest> set : this.allGuests.entrySet()) {
				id = set.getKey() + 1;
			}
			return id;
		}
	}

	public String getGuestFileName() {
		return guestFileName;
	}

	public void setGuestFileName(String guestFileName) {
		this.guestFileName = guestFileName;
	}
	
	public boolean loadData() {
		FormatManager formatManager = new FormatManager();
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.guestFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				Gender gender;
				if (token[3].equals("male")) {
					gender = Gender.MALE;
				} else {
					gender = Gender.FEMALE;
				}
				LocalDate dateOfBirth = LocalDate.parse(token[4], DateTimeFormatter.ofPattern("dd.MM.yyyy."));
				Guest guest = new Guest(id, token[1], token[2], gender, formatManager.asDate(dateOfBirth),
						token[5], token[6], token[7], token[8]);
				this.allGuests.put(id, guest);
				System.out.println("gost učitan");
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
			pw = new PrintWriter(new FileWriter(this.guestFileName, false));
			for (Map.Entry<Integer, Guest> set : this.allGuests.entrySet()) {
				pw.println(set.getValue().toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
}
