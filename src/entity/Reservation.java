package entity;

import manager.FormatManager;
import java.util.ArrayList;
import java.util.Date;

public class Reservation {
	private int id;
	private Person guest;
	private Room room;
	private RoomType roomType;
	private Date checkInDate;
	private Date checkOutDate;
	private ArrayList<AdditionalService> additionalServices;
	private ReservationStatus status;
	private double price;
	private boolean active;
	private ArrayList<RoomCriteria> criteria;
	private Date dateConfirmed;
	private Date dateCanceled;
	
	public Reservation() {
		this.setStatus(ReservationStatus.WAITING);
	}

	public Reservation(int id, Person guest, RoomType roomType, Date checkInDate, Date checkOutDate) {
		this.id = id; 
		this.guest = guest;
		this.roomType = roomType;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.setStatus(ReservationStatus.WAITING);
		this.setActive(true);
	}
	
	public Reservation(int id, Person guest, RoomType roomType, Date checkInDate, Date checkOutDate, 
			ArrayList<AdditionalService> benefits, ReservationStatus status, double price, boolean active) {
		this.id = id; 
		this.guest = guest;
		this.roomType = roomType;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.additionalServices = benefits;
		this.status = status;
		this.price = price;
		this.active = active;
	}
	
	public Reservation(int id, Person guest, RoomType roomType, Date checkInDate, Date checkOutDate, 
			ReservationStatus status, double price, boolean active) {
		this.id = id; 
		this.guest = guest;
		this.roomType = roomType;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.status = status;
		this.price = price;
		this.active = active;
	}
	
	@Override
	public String toString() {
		FormatManager formatManager = new FormatManager();
		String checkIn = formatManager.dateToString(this.checkInDate);
		String checkOut = formatManager.dateToString(this.checkOutDate);
		String s = String.format("Guest: %s %s\n%s\nCheck in: %s  Check out: %s  Price: %.2f  Status: %-10s", 
				this.guest.getFirstName(), this.guest.getLastName() ,this.roomType, checkIn, checkOut, this.price, this.status);
		return s;
	}
	
	public String toFileString() {
		FormatManager formatManager = new FormatManager();
		String id = String.valueOf(this.id);
		String guest;
		if (this.getGuest() == null) {
			guest = "";
		} else {
			String firstName = this.getGuest().getFirstName();
			String lastName = this.getGuest().getLastName();
			guest = firstName + "," + lastName;
		}
		String roomNumber;
		if (this.getRoom() == null) {
			roomNumber = "";
		} else {
			roomNumber = String.valueOf(this.getRoom().getRoomNumber());
		}
		String services = "";
		if (this.getBenefits() != null) {
			for (AdditionalService s : this.getBenefits()) {
				if (s != null) {
					services += s.getType() + ",";
				}
			}
		}
		String criteria = "";
		if (this.getCriteria() != null) {
			for (int i = 0; i < this.getCriteria().size(); i++) {
				criteria += String.valueOf(this.getCriteria().get(i)) + ",";
			}
		}
		String dateConfirmed = "";
		if (this.getDateConfirmed() != null) {
			dateConfirmed = formatManager.dateToString(this.getDateConfirmed());
		}
		String dateCanceled = "";
		if (this.getDateCanceled() != null) {
			dateCanceled = formatManager.dateToString(this.getDateCanceled());
		}
		
		return id + ";" + guest + ";" + roomNumber + ";" + this.roomType.toString() + ";" 
		+ formatManager.dateToString(this.getCheckInDate()) + ";" + formatManager.dateToString(this.getCheckOutDate()) + ";" 
		+ services + ";" + this.status.toString() + ";" + String.valueOf(this.price) + ";" + String.valueOf(this.active) + ";"
		+ criteria + ";" + dateConfirmed + ";" + dateCanceled;
	}
	
	public Person getGuest() {
		return guest;
	}
	public void setGuest(Person guest) {
		this.guest = guest;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public ReservationStatus getStatus() {
		return status;
	}
	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public ArrayList<AdditionalService> getBenefits() {
		return additionalServices;
	}

	public void setBenefits(ArrayList<AdditionalService> benefits) {
		this.additionalServices = benefits;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public ArrayList<RoomCriteria> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<RoomCriteria> criteria) {
		this.criteria = criteria;
	}

	public Date getDateConfirmed() {
		return dateConfirmed;
	}

	public void setDateConfirmed(Date dateConfirmed) {
		this.dateConfirmed = dateConfirmed;
	}

	public Date getDateCanceled() {
		return dateCanceled;
	}

	public void setDateCanceled(Date dateCanceled) {
		this.dateCanceled = dateCanceled;
	}
	
	
}
