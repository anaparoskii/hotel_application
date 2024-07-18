package main;

import manager.ManagerFactory;
import utils.AppSettings;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		AppSettings appSettings = new AppSettings("./data/employees.csv", "./data/guests.csv", "./data/services.csv",
				"./data/rooms.csv", "./data/reservations.csv", "./data/pricings.csv", "./data/maids.csv");
		ManagerFactory controllers = new ManagerFactory(appSettings);
		controllers.loadData();
		
		MainFrame main = new MainFrame(controllers);
		main.toString();
		
	}

}
