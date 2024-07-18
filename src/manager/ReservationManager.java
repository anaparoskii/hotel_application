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

import entity.AdditionalService;
import entity.Guest;
import entity.Pricing;
import entity.Reservation;
import entity.ReservationStatus;
import entity.Room;
import entity.RoomCriteria;
import entity.RoomPrice;
import entity.RoomStatus;
import entity.RoomType;
import entity.ServicePrice;


public class ReservationManager {
	
	private HashMap<Integer, Reservation> allReservations = new HashMap<>();
	private String reservationFileName;
	
	public ReservationManager() {
		this.allReservations = new HashMap<>();
	}
	
	public ReservationManager(String reservationFileName) {
		this.reservationFileName = reservationFileName;
		this.allReservations = new HashMap<>();
	}
	
	public HashMap<Integer, Reservation> getAllReservations() {
		return allReservations;
	}

	public void setAllReservations(HashMap<Integer, Reservation> allReservations) {
		this.allReservations = allReservations;
	}

	public boolean isAvailable(LocalDate date1, LocalDate date2, RoomType type, HashMap<Integer, Room> allRooms, Reservation reservation) {
		FormatManager formatManager = new FormatManager();
		Date d1 = formatManager.asDate(date1);
		Date d2 = formatManager.asDate(date2);
		for (Map.Entry<Integer, Room> set : allRooms.entrySet()) {
			System.out.println(set.getValue().getRoomNumber());
			if (set.getValue().getType().equals(type) && set.getValue().getCheckInDate() != null 
					&& set.getValue().getCheckInDate().size() != 0) {
				System.out.println("ima check ins");
				for (int i = 0; i < set.getValue().getCheckInDate().size(); i++) {
					if ((set.getValue().getCheckInDate().get(i).before(d1) && set.getValue().getCheckOutDate().get(i).after(d1)) || 
							(set.getValue().getCheckInDate().get(i).before(d2) && set.getValue().getCheckOutDate().get(i).after(d2)) ||
							(set.getValue().getCheckInDate().get(i).before(d1) && set.getValue().getCheckOutDate().get(i).after(d2)) ||
							(set.getValue().getCheckInDate().get(i).after(d1) && set.getValue().getCheckOutDate().get(i).before(d2))) {
						continue;
					} else {
						if (set.getValue().getCriteria() == null || set.getValue().getCriteria().containsAll(reservation.getCriteria())) {
							reservation.setRoom(set.getValue());
							set.getValue().getCheckInDate().add(d1);
							set.getValue().getCheckOutDate().add(d2);
							return true;
						} else {
							continue;
						}
					}
				}
				continue;
				
			} else if (set.getValue().getType().equals(type)) {
				System.out.println("nema check ins");
				if (set.getValue().getCriteria() == null || set.getValue().getCriteria().containsAll(reservation.getCriteria())) {
					reservation.setRoom(set.getValue());
					ArrayList<Date> checkIns = new ArrayList<>();
					checkIns.add(d1);
					ArrayList<Date> checkOuts = new ArrayList<>();
					checkOuts.add(d2);
					set.getValue().setCheckInDate(checkIns);
					set.getValue().setCheckOutDate(checkOuts);
					return true;
				} else {
					continue;
				}
			}
		}
		return false;
	}
	
	public void createReservation(Guest guest, RoomType type, LocalDate checkIn, LocalDate checkOut, 
			ArrayList<AdditionalService> services, PricingManager pricingManager, ArrayList<RoomCriteria> criteria) {
		FormatManager formatManager = new FormatManager();
		Date checkInDate = formatManager.asDate(checkIn);
		Date checkOutDate = formatManager.asDate(checkOut);
		int id = findNextReservationID();
		Reservation reservation = new Reservation(id, guest, type, checkInDate, checkOutDate);
		reservation.setBenefits(services);
		double price = 0;
		LocalDate day = checkIn;
		do { 
			Pricing pricing = pricingManager.findPricingByID(pricingManager.findPricingIDforDate(day));
			ArrayList<RoomPrice> roomPrice = pricing.getRoomPrice();
			for (RoomPrice p : roomPrice) {
				if (p.getRoom().equals(type)) {
					price += p.getPrice();
				}
			}
			ArrayList<ServicePrice> serviceCost = pricing.getServicePrice();
			for (ServicePrice p : serviceCost) {
				if (services != null) {
					for (AdditionalService s : services) {
						if (s.equals(p.getService())) {
							price += p.getPrice();
						}
					}
				}
			}
			day = day.plusDays(1);
		} while(day.isBefore(checkOut));
		reservation.setPrice(price);
		reservation.setActive(true);
		reservation.setCriteria(criteria);
		this.allReservations.put(id, reservation);
		System.out.println("Reservation successful!");
		System.out.println();	
	}
	
	public ArrayList<Reservation> readAllReservations() {
		ArrayList<Reservation> myReservations = new ArrayList<>();
		for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
			myReservations.add(set.getValue());
		}
		return myReservations;
	}
	
	public ArrayList<Reservation> readAllGuestReservations(Guest guest) {
		ArrayList<Reservation> myReservations = new ArrayList<>();
		for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
			Reservation r = set.getValue();
			if (r.getGuest() != null && r.getGuest().equals(guest)) {
				myReservations.add(set.getValue());
			}
		}
		return myReservations;
	}
	
	public ArrayList<Reservation> readAllApprovedReservation() {
		ArrayList<Reservation> acceptedReservations = new ArrayList<>();
		for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
			Reservation r = set.getValue();
			if (r.getStatus().equals(ReservationStatus.ACCEPTED)) {
				if (r.getRoom().getStatus().equals(RoomStatus.AVAILABLE)) {
					acceptedReservations.add(r);
				}
			}
		}
		return acceptedReservations;
	}
	
	public ArrayList<Reservation> readCheckOutReservation() {
		ArrayList<Reservation> returnValue = new ArrayList<>();
		for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
			Reservation r = set.getValue();
			if (r.getStatus().equals(ReservationStatus.ACCEPTED)) {
				if (r.getRoom().getStatus().equals(RoomStatus.TAKEN)) {
					returnValue.add(r);
				}
			}
		}
		return returnValue;
	}
	
	public void updateReservation(int id, ReservationStatus status) {
		FormatManager formatManager = new FormatManager();
		Reservation reservationToUpdate = findReservationByID(id);
		if (reservationToUpdate != null) {
			this.allReservations.get(id).setStatus(status);
			this.allReservations.get(id).setDateConfirmed(formatManager.asDate(LocalDate.now()));
			System.out.printf("Reservation updated to: %s", status);
			System.out.println();
		}
		System.out.println();
	}
	
	public Integer[] findStatusAmount(LocalDate dateFrom, LocalDate dateTo) {
		FormatManager formatManager = new FormatManager();
		int accepted = 0;
		int declined = 0;
		int canceled = 0;
		int waiting = 0;
		for (Map.Entry<Integer, Reservation> set : this.getAllReservations().entrySet()) {
			if (set.getValue().getDateConfirmed() != null) {
				LocalDate d = formatManager.asLocalDate(set.getValue().getDateConfirmed());
				if ((dateFrom.isBefore(d) && dateTo.isAfter(d)) || dateFrom.isEqual(d) || dateTo.isEqual(d)) {
					if (set.getValue().getStatus().equals(ReservationStatus.ACCEPTED)) {
						accepted++;
					} else if (set.getValue().getStatus().equals(ReservationStatus.DECLINED)) {
						declined++;
					} else {
						accepted++;
						LocalDate d1 = formatManager.asLocalDate(set.getValue().getDateConfirmed());
						if ((dateFrom.isBefore(d1) && dateTo.isAfter(d1)) || dateFrom.isEqual(d1) || dateTo.isEqual(d1)) {
							canceled++;
						}
					}
				}
			} else if (set.getValue().getDateCanceled() != null) {
				LocalDate d1 = formatManager.asLocalDate(set.getValue().getDateConfirmed());
				if ((dateFrom.isBefore(d1) && dateTo.isAfter(d1)) || dateFrom.isEqual(d1) || dateTo.isEqual(d1)) {
					canceled++;
				}
			} else if (set.getValue().getDateConfirmed() == null && set.getValue().getDateCanceled() == null) {
				waiting++;
			}
		}
		Integer[] returnValue = {accepted, declined, canceled, waiting};
		return returnValue;
	}
	
	public int findReservationID(Guest guest, LocalDate checkIn, LocalDate checkOut) {
		FormatManager formatManager = new FormatManager();
		Date dateCheckIn = formatManager.asDate(checkIn);
		Date dateCheckOut = formatManager.asDate(checkOut);
		int id = -1;
		Reservation reservation;
		for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
			reservation = set.getValue();
			if (reservation.getGuest().equals(guest) && 
					reservation.getCheckInDate().equals(dateCheckIn) && 
					reservation.getCheckOutDate().equals(dateCheckOut)) {
				return set.getKey();
			}
		}
		return id;
	}
	
	public Reservation findReservationByID(int id) {
		for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
			if (set.getKey().equals(id)) {
				return set.getValue();
			}
		}
		System.out.println("Couldn't find a reservation with this ID!");
		return null;
	}
	
	public int findNextReservationID() {
		int id = 0;
		if (this.allReservations.size() == 0) {
			return 1000;
		} else {
			for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
				id = set.getKey() + 1;
			}
			return id;
		}
	}

	public String getReservationFileName() {
		return reservationFileName;
	}

	public void setReservationFileName(String reservationFileName) {
		this.reservationFileName = reservationFileName;
	}
	
	public boolean loadData(ManagerFactory managers) {
		FormatManager formatManager = new FormatManager();
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.reservationFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				Guest guest = null;
				if (token[1] != "") {
					String[] guestToken = token[1].split(",");
					guest = managers.getGuestManager().findGuestByName(guestToken[0], guestToken[1]);
				}
				int roomNumber;
				Room room = null;
				if (token[2] != "") {
					roomNumber = Integer.parseInt(token[2]);
					room = managers.getRoomManager().findRoomByID(roomNumber);
				}
				String[] serviceToken = token[6].split(",");
				ArrayList<AdditionalService> s = new ArrayList<>();
				if (serviceToken.length != 0) {
					for (int i = 0; i < serviceToken.length; i++) {
						s.add(managers.getServiceManager().findServiceByName(serviceToken[i]));
					}
				}
				RoomType type;
				if (token[3].equals("single bed")) {
					type = RoomType.SINGLE_BED;
				} else if (token[3].equals("double bed")) {
					type = RoomType.DOUBLE_BED;
				} else if (token[3].equals("king bed")) {
					type = RoomType.KING_BED;
				} else if (token[3].equals("triple bed")) {
					type = RoomType.TRIPLE_BED;
				} else {
					type = RoomType.QUAD_BED;
				}
				ReservationStatus status;
				if (token[7].equals("accepted")) {
					status = ReservationStatus.ACCEPTED;
				} else if (token[7].equals("declined")) {
					status = ReservationStatus.DECLINED;
				} else if (token[7].equals("canceled")) {
					status = ReservationStatus.CANCELED;
				} else {
					status = ReservationStatus.WAITING;
				}
				RoomCriteria criteria;
				ArrayList<RoomCriteria> allCriteria = new ArrayList<>();
				if (token.length > 10) {
					String[] tokenCriteria = token[10].split(",");
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
				}
				Reservation reservation = new Reservation(id, guest, type, formatManager.asDate(token[4]), 
						formatManager.asDate(token[5]), status, 
						Double.parseDouble(token[8]), Boolean.parseBoolean(token[9]));
				if (room != null) {
					reservation.setRoom(room);
				}
				if (s != null) {
					reservation.setBenefits(s);
				}
				Date dateConfirmed = null;
				if (token.length > 11) {
					if (!(token[11].equals(""))) {
						dateConfirmed = formatManager.asDate(token[11]);
					}
				}
				Date dateCanceled = null;
				if (token.length > 12) {
					if (!(token[12].equals(""))) {
						dateCanceled = formatManager.asDate(token[12]);
					}
				}
				reservation.setCriteria(allCriteria);
				reservation.setDateConfirmed(dateConfirmed);
				reservation.setDateCanceled(dateCanceled);
				LocalDate today = LocalDate.now();
				if (LocalDate.parse(token[4], DateTimeFormatter.ofPattern("dd.MM.yyyy.")).isBefore(today) && reservation.isActive()) {
					reservation.setStatus(ReservationStatus.CANCELED);
					reservation.setDateCanceled(formatManager.asDate(today));					
					reservation.setActive(false);
				}
				this.allReservations.put(id, reservation);
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
			pw = new PrintWriter(new FileWriter(this.reservationFileName, false));
			for (Map.Entry<Integer, Reservation> set : this.allReservations.entrySet()) {
				pw.println(set.getValue().toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}
