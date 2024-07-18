package entity;

import java.time.LocalDate;
import java.util.ArrayList;

import manager.FormatManager;

public class Pricing {
	private int id;
	private LocalDate startDate;
	private LocalDate endDate;
	private ArrayList<RoomPrice> roomPrice;
	private ArrayList<ServicePrice> servicePrice;
	private boolean active;
	
	public Pricing() {
		this.active = true;	}
	
	public Pricing(int id, LocalDate start, LocalDate end) {
		this.id = id;
		this.startDate = start;
		this.endDate = end;
		this.active = true;
	}
	
	public Pricing(int id, LocalDate start, LocalDate end, ArrayList<RoomPrice> roomPrice, ArrayList<ServicePrice> servicePrice) {
		this.id = id;
		this.startDate = start;
		this.endDate = end;
		this.roomPrice = roomPrice;
		this.servicePrice = servicePrice;
		this.active = true;
	}
	
	public String toFileString() {
		FormatManager formatManager = new FormatManager();
		String id = String.valueOf(this.id);
		String start = formatManager.dateToString(this.getStartDate());
		String end = formatManager.dateToString(this.getEndDate());
		String roomPrice = "";
		for (RoomPrice rp : this.roomPrice) {
			roomPrice += rp.getRoom().toString() + "-" + String.valueOf(rp.getPrice()) + ",";
		}
		String servicePrice = "";
		for (ServicePrice sp : this.servicePrice) {
			servicePrice += sp.getService().getType() + "-" + String.valueOf(sp.getPrice()) + ",";
		}
		
		return id + ";" + start + ";" + end + ";" + roomPrice + ";" + servicePrice;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public ArrayList<RoomPrice> getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(ArrayList<RoomPrice> roomPrice) {
		this.roomPrice = roomPrice;
	}

	public ArrayList<ServicePrice> getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(ArrayList<ServicePrice> servicePrice) {
		this.servicePrice = servicePrice;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
