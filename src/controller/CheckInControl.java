package controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import entity.AdditionalService;
import entity.Pricing;
import entity.Reservation;
import entity.RoomStatus;
import entity.ServicePrice;
import manager.FormatManager;
import manager.ManagerFactory;
import manager.PricingManager;

public class CheckInControl {
	private ManagerFactory managers;
	
	public CheckInControl(ManagerFactory managers) {
		this.setManagers(managers);
	}
	
	public void checkInProcess(int id, ArrayList<AdditionalService> addServices) {
		System.out.println(id);
		Reservation reservation = managers.getReservationManager().findReservationByID(id);
		reservation.getRoom().setStatus(RoomStatus.TAKEN);
		if (addServices != null) {
			additionalServices(reservation, addServices);
			double price = reservation.getPrice();
			price += fixPrice(reservation, addServices);
			reservation.setPrice(price);
		}
		reservation.setActive(false);
		System.out.printf("Checked in: %s %s", reservation.getGuest().getFirstName(), reservation.getGuest().getLastName());
		System.out.println();
		System.out.printf("Reservation price: %.2f", reservation.getPrice());
		System.out.println();
		System.out.printf("Room status: %s", reservation.getRoom().getStatus());
		System.out.println();
	}
	
	public void additionalServices(Reservation reservation, ArrayList<AdditionalService> addServices) {
		for (AdditionalService s : addServices) {
			reservation.getBenefits().add(s);
		}
	}
	
	public double fixPrice(Reservation reservation, ArrayList<AdditionalService> addServices) {
		double price = 0;
		FormatManager formatManager = new FormatManager();
		PricingManager pricingManager = managers.getPricingManager();
		String checkInString = formatManager.dateToString(reservation.getCheckInDate());
		LocalDate checkIn = LocalDate.parse(checkInString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		String checkOutString = formatManager.dateToString(reservation.getCheckOutDate());
		LocalDate checkOut = LocalDate.parse(checkOutString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		do {
			Pricing pricing = pricingManager.findPricingByID(pricingManager.findPricingIDforDate(checkIn));
			ArrayList<ServicePrice> serviceCost = pricing.getServicePrice();
			for (ServicePrice p : serviceCost) {
				for (AdditionalService s : addServices) {
					if (s.equals(p.getService())) {
						price += p.getPrice();
					}
				}
			}
			checkIn = checkIn.plusDays(1);
		} while (checkIn.isBefore(checkOut));
		return price;
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}
}
