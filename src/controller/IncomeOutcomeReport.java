package controller;

import java.time.LocalDate;
import java.util.ArrayList;

import entity.Employee;
import entity.Reservation;
import entity.ReservationStatus;
import entity.RoomType;
import manager.FormatManager;
import manager.ManagerFactory;

public class IncomeOutcomeReport {
	
	private ManagerFactory managers;
	
	public IncomeOutcomeReport(ManagerFactory managers) {
		this.managers = managers;
	}
	
	public int getIncomeData(LocalDate dateFrom, LocalDate dateTo, RoomType type) {
		FormatManager formatManager = new FormatManager();
		ArrayList<Reservation> allReservations = managers.getReservationManager().readAllReservations();
		int price = 0;
		for (Reservation r : allReservations) {
			if (!r.getStatus().equals(ReservationStatus.DECLINED) && 
					r.getRoomType().equals(type) &&
					r.getDateConfirmed() != null) {
				LocalDate date = formatManager.asLocalDate(r.getDateConfirmed());
				if (date.isAfter(dateFrom) && date.isBefore(dateTo)) {
					price += r.getPrice();
				}
			}
		}
		return price;
	}
	
	public int getOutcomeData(LocalDate dateFrom, LocalDate dateTo, Employee employee) {
		int price = 0;
		FormatManager formatManager = new FormatManager();
		LocalDate dateStarted = formatManager.asLocalDate(employee.getDateStartedWork());
		if (dateStarted.isAfter(dateFrom)) { 
			dateFrom = dateStarted;
		}
		if (!employee.isWork()) {
			LocalDate dateFinished = formatManager.asLocalDate(employee.getDateFinishedWork());
			if (dateFinished.isBefore(dateTo) && dateFinished.isAfter(dateFrom)) {
				dateTo = dateFinished;
			}
		}
		do { 
			price += employee.getSalary();
			dateFrom = dateFrom.plusMonths(1);
		} while (dateFrom.isBefore(dateTo));
		
		return price;
	}

  public ManagerFactory getManagers() {
    return managers;
  }

  public void setManagers(ManagerFactory managers) {
    this.managers = managers;
  }
	
}
