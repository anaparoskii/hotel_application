package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import entity.Maid;
import entity.Room;
import entity.RoomStatus;
import manager.EmployeeManager;
import manager.FormatManager;
import manager.ManagerFactory;

public class MaidControl {
	
	private ManagerFactory managers;
	
	public MaidControl(ManagerFactory managers) {
		this.managers = managers;
	}
	
	public void cleanRoom(String firstName, String lastName, Room room) {
		FormatManager formatManager = new FormatManager();
		EmployeeManager employeeManager = managers.getEmployeeManager();
		int maidId = employeeManager.findEmployeeIDByName(firstName, lastName);
		Maid maid = (Maid) employeeManager.findEmployeeByID(maidId);
		for (Room rooms : maid.getRoomsToClean()) {
			if (rooms.equals(room)) {
				maid.getRoomsToClean().remove(rooms);
				break;
			}
		}
		Date d = formatManager.asDate(LocalDate.now());
		ArrayList<Date> allDates = maid.getCleanedRooms();
		if (allDates == null) {
			allDates = new ArrayList<>();
		}
		allDates.add(d);
		maid.setCleanedRooms(allDates);
		employeeManager.getAllEmployees().replace(maidId, maid);
		room.setStatus(RoomStatus.AVAILABLE);
		System.out.printf("Maid %s %s cleaned room %d", maid.getFirstName(), maid.getLastName(), room.getRoomNumber());
		System.out.println();
		System.out.printf("Room status: %s", room.getStatus());
		System.out.println();
	}
	
	public int getChartData(LocalDate dateFrom, LocalDate dateTo, Maid maid) {
		FormatManager formatManager = new FormatManager();
		ArrayList<Date> datesOfCleaning = maid.getCleanedRooms();
		int numberOfCleanedRooms = 0;
		if (datesOfCleaning != null) {
			for (Date date : datesOfCleaning) {
				LocalDate d = formatManager.asLocalDate(date);
				if (d.isAfter(dateFrom) && d.isBefore(dateTo)) {
					numberOfCleanedRooms++;
				}
			}
		}
		return numberOfCleanedRooms;
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}

}
