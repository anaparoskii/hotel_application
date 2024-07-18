package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.AdditionalService;


public class ServiceManager {
	
	private HashMap<Integer, AdditionalService> allServices = new HashMap<>();
	private String serviceFileName;
	
	public ServiceManager() {
		this.allServices = new HashMap<>();
	}
	
	public ServiceManager(String serviceFileName) {
		this.serviceFileName = serviceFileName;
		this.allServices = new HashMap<>();
	}

	public HashMap<Integer, AdditionalService> getAllServices() {
		return allServices;
	}

	public void setAllServices(HashMap<Integer, AdditionalService> allServices) {
		this.allServices = allServices;
	}

	public void createService(int id, String type) {
		this.allServices.put(id, new AdditionalService(id, type));
	}
	
	public ArrayList<AdditionalService> readAllServices() {
		System.out.println("*** SERVICES ***");
		ArrayList<AdditionalService> allServices = new ArrayList<>();
		for (Map.Entry<Integer, AdditionalService> set : this.allServices.entrySet()) {
			allServices.add(set.getValue());
		}
		return allServices;
	}
	
	public void updateService(int id, String newName) {
		AdditionalService serviceToUpdate = findServiceByID(id);
		if (serviceToUpdate != null) {
			this.allServices.get(id).setType(newName);
		}
		System.out.println();
	}
	
	public void deleteService(int id) {
		AdditionalService serviceToDelete = findServiceByID(id);
		if (serviceToDelete != null) {
			System.out.printf("Removed additional service: %-10s", serviceToDelete.getType());
			System.out.println();
			this.allServices.remove(id);
		}
		System.out.println();
	}
	
	public AdditionalService findServiceByName(String name) {
		for (Map.Entry<Integer, AdditionalService> set : this.allServices.entrySet()) {
			AdditionalService service = set.getValue();
			if (service.getType().equals(name)) {
				return service;
			}
		}
		System.out.println("Couldn't find this service!");
		return null;
	}
	
	public AdditionalService findServiceByID(int id) {
		for (Map.Entry<Integer, AdditionalService> set : this.allServices.entrySet()) {
			if (set.getKey() == id) {
				return this.allServices.get(id);
			}
		}
		System.out.println("Couldn't find a service with this ID!");
		return null;
	}
	
	public int findServiceIDByName(String name) {
		for (Map.Entry<Integer, AdditionalService> set : this.allServices.entrySet()) {
			AdditionalService service = set.getValue();
			if (service.getType().equals(name)) {
				return service.getId();
			}
		}
		System.out.println("Couldn't find this service!");
		return -1;
	}
	
	public int findNextServiceID() {
		int id = 0;
		if (this.allServices.size() == 0) {
			return 1111;
		} else {
			for (Map.Entry<Integer, AdditionalService> set : this.allServices.entrySet()) {
				id = set.getKey() + 1;
			}
			return id;
		}
	}

	public String getServiceFileName() {
		return serviceFileName;
	}

	public void setServiceFileName(String serviceFileName) {
		this.serviceFileName = serviceFileName;
	}
	
	public boolean loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.serviceFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				AdditionalService service = new AdditionalService(id, token[1]);
				this.allServices.put(id, service);
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
			pw = new PrintWriter(new FileWriter(this.serviceFileName, false));
			for (Map.Entry<Integer, AdditionalService> set : this.allServices.entrySet()) {
				pw.println(set.getValue().toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
}
