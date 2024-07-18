package controller;

import java.util.HashMap;
import java.util.Map;

import entity.Maid;
import entity.Reservation;
import entity.Room;
import entity.RoomStatus;
import manager.EmployeeManager;
import manager.ManagerFactory;

public class CheckOutControl {
	
	private ManagerFactory managers;
	
	public CheckOutControl(ManagerFactory managers) {
		this.managers = managers;
	}
	
	public void checkOutProcess(Reservation reservation) {
		reservation.getRoom().setStatus(RoomStatus.PREPARATION);
		System.out.printf("Checked out: %s %s", reservation.getGuest().getFirstName(), reservation.getGuest().getLastName());
		System.out.println();
		System.out.printf("Room status: %s", reservation.getRoom().getStatus());
		System.out.println();
		asignRoomToMaid(reservation.getRoom());
	}
	
	public void asignRoomToMaid(Room room) {
		EmployeeManager employeeManager = managers.getEmployeeManager();
		HashMap<Integer, Maid> allMaids = employeeManager.getAllMaids();
		int maidIndex = -1;
		int numberOfRooms = -1;
		for (Map.Entry<Integer, Maid> setup : allMaids.entrySet()) {
			maidIndex = setup.getKey();
			numberOfRooms = setup.getValue().getRoomsToClean().size();
			break;
		}
		for (Map.Entry<Integer, Maid> set : allMaids.entrySet()) {
			if (set.getValue().getRoomsToClean().size() < numberOfRooms && set.getKey() != 0) {
				maidIndex = set.getKey();
				numberOfRooms = set.getValue().getRoomsToClean().size();
			}
		}
		Maid chosenMaid = allMaids.get(maidIndex);
		chosenMaid.getRoomsToClean().add(room);
		employeeManager.getAllEmployees().put(maidIndex, chosenMaid);
		System.out.printf("Maid %s %s got room %d", chosenMaid.getFirstName(), chosenMaid.getLastName(), room.getRoomNumber());
		System.out.println();
	}

	public ManagerFactory getManagers() {
		return managers;
	}

	public void setManagers(ManagerFactory managers) {
		this.managers = managers;
	}
	

}
