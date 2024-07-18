package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.Pricing;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomPrice;
import entity.RoomStatus;
import entity.RoomType;


public class RoomManager {
	
	private HashMap<Integer, Room> allRooms = new HashMap<>();
	private String roomFileName;
	
	public RoomManager() {
		this.allRooms = new HashMap<>();
	}
	
	public RoomManager(String roomFileName) {
		this.roomFileName = roomFileName;
		this.allRooms = new HashMap<>();
	}
	
	public HashMap<Integer, Room> getAllRooms() {
		return allRooms;
	}

	public void setAllRooms(HashMap<Integer, Room> allRooms) {
		this.allRooms = allRooms;
	}

	public void createRoom(RoomType type, int roomNumber, ArrayList<RoomCriteria> allCriteria) {
		this.allRooms.put(roomNumber, new Room(type, roomNumber, allCriteria));
	}
	
	public boolean isAvailable(LocalDate date, Room room) {
		FormatManager formatManager = new FormatManager();
		if (room.getCheckInDate() == null) {
			return true;
		} else {
			for (int i = 0; i < room.getCheckInDate().size(); i++) {
				if (formatManager.asLocalDate(room.getCheckInDate().get(i)).isBefore(date) 
						&& formatManager.asLocalDate(room.getCheckOutDate().get(i)).isAfter(date)) {
					return false;
				} else if (formatManager.asLocalDate(room.getCheckInDate().get(i)).isEqual(date) 
						|| formatManager.asLocalDate(room.getCheckOutDate().get(i)).isEqual(date)) {
					return false;
				}
			}
			return true;
		}
	}
	
	public ArrayList<Room> readAllRooms() {
		ArrayList<Room> allRooms = new ArrayList<>();
		System.out.println("*** ROOMS ***");
		Room room;
		for (Map.Entry<Integer, Room> set : this.allRooms.entrySet()) {
			room = set.getValue();
			allRooms.add(room);
		}
		System.out.println();
		return allRooms;
	}
	
	public void updateRoom(int id, RoomType newType) {
		Room roomToUpdate = findRoomByID(id);
		if (roomToUpdate != null) {
			this.allRooms.get(id).setType(newType);
			this.allRooms.get(id).setRoomCode(newType.showCode());
			System.out.println("Edited: ");
			System.out.println(this.allRooms.get(id).toString());
			System.out.println();
		}
	}
	
	public void deleteRoom(int id) {
		Room roomToDelete = findRoomByID(id);
		if (roomToDelete != null) {
			System.out.printf("Removed room: %d %s", id, roomToDelete.getType());
			System.out.println();
			this.allRooms.remove(id);
		}
	}
	
	public Integer[] findNightsIn(LocalDate dateFrom, LocalDate dateTo, Room r, PricingManager pricingManager) {
		FormatManager formatManager = new FormatManager();
		int nightsIn = 0;
		int price = 0;
		for (int i = 0; i < r.getCheckInDate().size(); i++) {
			do {
				LocalDate d1 = formatManager.asLocalDate(r.getCheckInDate().get(i));
				LocalDate d2 = formatManager.asLocalDate(r.getCheckOutDate().get(i));
				if ((d1.isBefore(dateFrom) && d2.isAfter(dateFrom)) || d1.isEqual(dateFrom)) {
					nightsIn++;
					Pricing pricing = pricingManager.findPricingByID(pricingManager.findPricingIDforDate(dateFrom));
					ArrayList<RoomPrice> roomPrice = pricing.getRoomPrice();
					for (RoomPrice p : roomPrice) {
						if (p.getRoom().equals(r.getType())) {
							price += p.getPrice();
						}
					}
					
				}
				dateFrom = dateFrom.plusDays(1);
			} while (dateFrom.isBefore(dateTo));
		}
		Integer[] returnValue = {nightsIn, price};
		return returnValue;
	}
	
	public Room findRoomByType(RoomType type) {
		for (Map.Entry<Integer, Room> set : this.allRooms.entrySet()) {
			if (set.getValue().getType().equals(type)) {
				return set.getValue();
			}
		}
		System.out.println("Couldn't find a room of this type!");
		return null;
	}
	
	public Room findRoomByID(int id) {
		for (Map.Entry<Integer, Room> set : this.allRooms.entrySet()) {
			if (set.getKey() == id) {
				return this.allRooms.get(id);
			}
		}
		System.out.println("Couldn't a room with this number!");
		return null;
	}
	
	public int findRoomIDByType(RoomType type) {
		for (Map.Entry<Integer, Room> set : this.allRooms.entrySet()) {
			if (set.getValue().getType().equals(type)) {
				return set.getKey();
			}
		}
		System.out.println("Couldn't find a room of this type!");
		return -1;
	}

	public String getRoomFileName() {
		return roomFileName;
	}

	public void setRoomFileName(String roomFileName) {
		this.roomFileName = roomFileName;
	}
	
	public boolean loadData() {
		FormatManager formatManager = new FormatManager();
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.roomFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				RoomType type;
				if (token[1].equals("single bed")) {
					type = RoomType.SINGLE_BED;
				} else if (token[1].equals("double bed")) {
					type = RoomType.DOUBLE_BED;
				} else if (token[1].equals("king bed")) {
					type = RoomType.KING_BED;
				} else if (token[1].equals("triple bed")) {
					type = RoomType.TRIPLE_BED;
				} else {
					type = RoomType.QUAD_BED;
				}
				RoomStatus status;
				if (token[3].equals("available")) {
					status = RoomStatus.AVAILABLE;
				} else if (token[3].equals("taken")) {
					status = RoomStatus.TAKEN;
				} else {
					status = RoomStatus.PREPARATION;
				}
				RoomCriteria criteria;
				ArrayList<Date> checkIns = new ArrayList<>();
				ArrayList<Date> checkOuts = new ArrayList<>();
				ArrayList<RoomCriteria> allCriteria = new ArrayList<>();
				String[] tokenCriteria = token[4].split(",");
				for (int i = 0; i < tokenCriteria.length; i++) {
					if (tokenCriteria[i].equals("balcony")) {
						criteria = RoomCriteria.BALCONY;
					} else if (tokenCriteria[i].equals("tv")) {
						criteria = RoomCriteria.TV;
					} else if (tokenCriteria[i].equals("smoking")) {
						criteria = RoomCriteria.SMOKING;
					} else {
						criteria = RoomCriteria.AIR_CONDITION;
					}
					allCriteria.add(criteria);
				}
				if (token.length > 5) {
					String[] tokenCheckIn = token[5].split(",");
					for (int i = 0; i < tokenCheckIn.length; i++) {
						checkIns.add(formatManager.asDate(tokenCheckIn[i]));
					}
					String[] tokenCheckOut = token[6].split(",");
					for (int i = 0; i < tokenCheckOut.length; i++) {
						checkOuts.add(formatManager.asDate(tokenCheckOut[i]));
					}
				}
				Room room = new Room(id, type, token[2], status);
				room.setCriteria(allCriteria);
				room.setCheckInDate(checkIns);
				room.setCheckOutDate(checkOuts);
				this.allRooms.put(id, room);
				System.out.println("soba učitana");
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
			pw = new PrintWriter(new FileWriter(this.roomFileName, false));
			for (Map.Entry<Integer, Room> set : this.allRooms.entrySet()) {
				pw.println(set.getValue().toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	

}
