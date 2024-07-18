package controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import entity.Reservation;
import entity.ReservationStatus;
import entity.RoomType;
import manager.FormatManager;
import manager.ManagerFactory;

public class IncomeChartData {
	
	private ManagerFactory managers;
	
	public IncomeChartData(ManagerFactory managers) {
		this.setManagers(managers);
	}
	
	public double[] getXData(LocalDate today) {
		LocalDate start = today.minusMonths(12);
		
		double[] xData = new double[12];
		
		for (int i = 0; i < 12; i++) {
			xData[i] = start.getMonthValue();
			if ((today.getMonthValue() < start.getMonthValue()) && (today.getYear() > start.getYear())) {
				xData[i] = 0;
			}
			start = start.plusMonths(1);
		}
		
		return xData;
	}
	
	public double[] getYData(LocalDate today, RoomType type, double[] xData) {
		FormatManager formatManager = new FormatManager();
		double[] yData = new double[12];
		for (int i = 0; i < yData.length; i++) {
			yData[i] = 0;
		}
		HashMap<Integer, Reservation> allReservations = managers.getReservationManager().getAllReservations();
		for (Map.Entry<Integer, Reservation> set : allReservations.entrySet()) {
			if (!set.getValue().getStatus().equals(ReservationStatus.DECLINED) && 
					set.getValue().getRoomType().equals(type) && 
					set.getValue().getDateConfirmed() != null) {
				LocalDate date = formatManager.asLocalDate(set.getValue().getDateConfirmed());
				if (date.getYear() == today.getYear() && today.isAfter(date)) {
					for (int j = 0; j < 12; j++) {
						if (xData[j] == date.getMonthValue()) {
							yData[j] += set.getValue().getPrice();
						}
					}
				}
			}
		}
		return yData;
	}

  public ManagerFactory getManagers() {
    return managers;
  }

  public void setManagers(ManagerFactory managers) {
    this.managers = managers;
  }
	
}
