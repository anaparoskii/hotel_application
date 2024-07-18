package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.AdditionalService;
import entity.Guest;
import entity.Reservation;
import entity.ReservationStatus;
import manager.FormatManager;
import manager.ManagerFactory;
import manager.ReservationManager;

public class ReservationControl {
	
	private ManagerFactory managers;
	
	public ReservationControl(ManagerFactory managers) {
		this.managers = managers;
	}
	
	public String checkReservation(Reservation reservation) {
		ReservationManager reservationManager = managers.getReservationManager();
		FormatManager formatManager = new FormatManager();
		String checkInString = formatManager.dateToString(reservation.getCheckInDate());
		LocalDate checkIn = LocalDate.parse(checkInString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		String checkOutString = formatManager.dateToString(reservation.getCheckOutDate());
		LocalDate checkOut = LocalDate.parse(checkOutString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		if (reservationManager.isAvailable(checkIn, checkOut, 
				reservation.getRoomType(), managers.getRoomManager().getAllRooms(), reservation)) {
			managers.getReservationManager().updateReservation(reservation.getId(), ReservationStatus.ACCEPTED);
			return "<html>Reservation status changed to<br>ACCEPTED</html>";
		} else {
			managers.getReservationManager().updateReservation(reservation.getId(), ReservationStatus.DECLINED);
			reservation.setActive(false);
			reservation.setPrice(0);
			return "<html>Reservation status changed to<br>DECLINED</html>";
		}
	}
	
	public String cancelReservation(Reservation reservation) {
		FormatManager formatManager = new FormatManager();
		if (reservation.getStatus().equals(ReservationStatus.ACCEPTED)) {
			for (int i = 0; i < reservation.getRoom().getCheckInDate().size(); i++) {
				if (formatManager.asLocalDate(reservation.getCheckInDate())
						.isEqual(formatManager.asLocalDate(reservation.getRoom().getCheckInDate().get(i)))) {
					if (formatManager.asLocalDate(reservation.getCheckOutDate())
							.isEqual(formatManager.asLocalDate(reservation.getRoom().getCheckOutDate().get(i)))) {
						reservation.getRoom().getCheckInDate().remove(i);
						break;
					}
				}
			}
		} else {
			reservation.setPrice(0);
		}
		managers.getReservationManager().updateReservation(reservation.getId(), ReservationStatus.CANCELED);
		reservation.setDateCanceled(formatManager.asDate(LocalDate.now()));
		reservation.setActive(false);
		return "<html>Reservation status changed to<br>CANCELLED</html>";
	}
	
	public ArrayList<Reservation> getReservation() {
		return managers.getReservationManager().readAllReservations();
	}
	
	public ArrayList<Reservation> getGuestReservation(Guest guest) {
		return managers.getReservationManager().readAllGuestReservations(guest);
	}
	
	public ArrayList<Reservation> getAcceptedReservations() {
		return managers.getReservationManager().readAllApprovedReservation();
	}
	
	public ArrayList<Reservation> getCheckOutReservations() {
		return managers.getReservationManager().readCheckOutReservation();
	}
	
	public ArrayList<AdditionalService> getServices() {
		ArrayList<AdditionalService> returnValue = new ArrayList<>();
		HashMap<Integer, AdditionalService> allServices = managers.getServiceManager().getAllServices();
		for (Map.Entry<Integer, AdditionalService> set : allServices.entrySet()) {
			returnValue.add(set.getValue());
		}
		return returnValue;
	}
	
	public ArrayList<Reservation> sortAscending(ArrayList<Reservation> allReservations) {
		for (int i = 0; i < allReservations.size(); i++) {
			for (int j = 0; j < allReservations.size(); j++) {
				if (allReservations.get(i).getPrice() < allReservations.get(j).getPrice()) {
					Reservation first = allReservations.get(i);
					Reservation second = allReservations.get(j);
					allReservations.set(i, second);
					allReservations.set(j, first);
				}
			}
		}
		return allReservations;
	}
	
	public ArrayList<Reservation> sortDescending(ArrayList<Reservation> allReservations) {
		for (int i = 0; i < allReservations.size(); i++) {
			for (int j = 0; j < allReservations.size(); j++) {
				if (allReservations.get(i).getPrice() > allReservations.get(j).getPrice()) {
					Reservation first = allReservations.get(i);
					Reservation second = allReservations.get(j);
					allReservations.set(i, second);
					allReservations.set(j, first);
				}
			}
		}
		return allReservations;
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}

}
