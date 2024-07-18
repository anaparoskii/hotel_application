package entity;

import java.util.ArrayList;
import java.util.Date;

import manager.FormatManager;

public class Room {
	private int roomNumber;
	private RoomType type;
	private String roomCode;
	private ArrayList<Date> checkInDate;
	private ArrayList<Date> checkOutDate;
	private RoomStatus status;
	private ArrayList<RoomCriteria> criteria;
	
	public Room() {}
	
	public Room(RoomType type, int roomNumber, ArrayList<RoomCriteria> criteria) {
		this.type = type;
		this.roomNumber = roomNumber;
		this.criteria = criteria;
		this.setRoomCode(type.showCode());
		this.setStatus(RoomStatus.AVAILABLE);
	}
	
	public Room(int roomNumber, RoomType type, String roomCode, RoomStatus status, ArrayList<Date> checkIn, ArrayList<Date> checkOut) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.roomCode = roomCode;
		this.status = status;
		this.checkInDate = checkIn;
		this.checkOutDate = checkOut;
	}
	
	public Room(int roomNumber, RoomType type, String roomCode, RoomStatus status) {
		this.roomNumber = roomNumber;
		this.type = type;
		this.roomCode = roomCode;
		this.status = status;
	}
	
	@Override
	public String toString() {
		String s = String.format("Room type: %-15s Number: %-5d Code: %-5s", this.getType(), this.getRoomNumber(), this.getRoomCode());
		return s;
	}
	
	public String toFileString() {
		FormatManager formatManager = new FormatManager();
		String id = String.valueOf(this.roomNumber);
		String checkIns = "";
		if (this.checkInDate != null) {
			for (int i = 0; i < this.checkInDate.size(); i++) {
				String s = formatManager.dateToString(this.checkInDate.get(i));
				checkIns += s + ",";
			}
		}
		String checkOuts = "";
		if (this.checkOutDate != null) {
			for (int i = 0; i < this.checkOutDate.size(); i++) {
				String s = formatManager.dateToString(this.checkOutDate.get(i));
				checkOuts += s + ",";
			}
		}
		String criteria = "";
		if (this.getCriteria() != null) {
			for (int i = 0; i < this.getCriteria().size(); i++) {
				criteria += String.valueOf(this.getCriteria().get(i)) + ",";
			}
		}
		
		return id + ";" + this.type.toString() + ";" + this.roomCode + ";" + this.status.toString() + ";"
		 + criteria + ";" + checkIns + ";" + checkOuts;
	}
	
	public RoomType getType() {
		return type;
	}
	public void setType(RoomType type) {
		this.type = type;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getRoomCode() {
		return roomCode;
	}
	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public ArrayList<Date> getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(ArrayList<Date> checkInDate) {
		this.checkInDate = checkInDate;
	}

	public ArrayList<Date> getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(ArrayList<Date> checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public ArrayList<RoomCriteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<RoomCriteria> criteria) {
		this.criteria = criteria;
	}

}
