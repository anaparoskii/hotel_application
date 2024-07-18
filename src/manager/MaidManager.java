package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import entity.Maid;

public class MaidManager {
	private String maidFileName;
	
	public MaidManager(String maidFileName) {
		this.maidFileName = maidFileName;
	}
	
	public boolean loadData(ManagerFactory managers) {
		FormatManager formatManager = new FormatManager();
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.maidFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				ArrayList<Date> allDates = new ArrayList<>();
				String[] dates = token[1].split(",");
				for (int i = 0; i < dates.length; i++) {
					allDates.add(formatManager.asDate(dates[i]));
				}
				Maid m = (Maid) managers.getEmployeeManager().getAllEmployees().get(id);
				m.setCleanedRooms(allDates);
				managers.getEmployeeManager().getAllEmployees().replace(id, m);
			}
			br.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean saveData(ManagerFactory managers) {
		FormatManager formatManager = new FormatManager();
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileWriter(this.maidFileName, false));
			for (Map.Entry<Integer, Maid> set : managers.getEmployeeManager().getAllMaids().entrySet()) {
				String d = "";
				if (set.getValue().getCleanedRooms() != null) {
					for (int i = 0; i < set.getValue().getCleanedRooms().size(); i++) {
						d += formatManager.dateToString(set.getValue().getCleanedRooms().get(i)) + ",";
					}
					pw.println(set.getValue().getId() + ";" + d);
				}
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	public String getMaidFileName() {
		return maidFileName;
	}

	public void setMaidFileName(String maidFileName) {
		this.maidFileName = maidFileName;
	}
}
