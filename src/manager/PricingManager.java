package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import entity.AdditionalService;
import entity.Pricing;
import entity.RoomPrice;
import entity.RoomType;
import entity.ServicePrice;

public class PricingManager {
	
	private HashMap<Integer, Pricing> seasonPricing = new HashMap<>();
	private String pricingFileName;
	
	public PricingManager() {
		this.seasonPricing = new HashMap<>();
	}
	
	public PricingManager(String pricingFileName) {
		this.seasonPricing = new HashMap<>();
		this.pricingFileName = pricingFileName;
	}
	
	public HashMap<Integer, Pricing> getSeasonPricing() {
		return seasonPricing;
	}

	public void setSeasonPricing(HashMap<Integer, Pricing> seasonPricing) {
		this.seasonPricing = seasonPricing;
	}

	public void createPricing(LocalDate startDate, LocalDate endDate, HashMap<Integer, AdditionalService> allServices) {
		int id = findNextPricingID();
		Pricing seasonPricing = new Pricing(id, startDate, endDate);
		ArrayList<RoomPrice> roomPrice = new ArrayList<>();
		for (RoomType type : RoomType.values()) {
			RoomPrice room = new RoomPrice();
			room.setRoom(type);
			room.setPrice(0);
			roomPrice.add(room);
		}
		seasonPricing.setRoomPrice(roomPrice);
		ArrayList<ServicePrice> servicePrice = new ArrayList<>();
		if (allServices != null) {
			for (Map.Entry<Integer, AdditionalService> set : allServices.entrySet()) {
				AdditionalService additionalService = set.getValue();
				ServicePrice service = new ServicePrice();
				service.setService(additionalService);
				service.setPrice(0);
				servicePrice.add(service);
			}
		}
		seasonPricing.setServicePrice(servicePrice);
		this.seasonPricing.put(id, seasonPricing);
		System.out.println("Season prices created!");
		System.out.println();
	}
	
	public void addNewServicePricing(AdditionalService newService, double price, Pricing pricing) {
		ServicePrice newServicePrice = new ServicePrice();
		newServicePrice.setService(newService);
		newServicePrice.setPrice(price);
		ArrayList<ServicePrice> servicePrice = pricing.getServicePrice();
		servicePrice.add(newServicePrice);
		pricing.setServicePrice(servicePrice);
		System.out.printf("Added service price: %-15s - %.2f", newService.getType(), price);
		System.out.println();
	}
	
	public void updateServicePricing(String wantedService, double newPrice, Pricing pricing) {
		ArrayList<ServicePrice> servicePrice = pricing.getServicePrice();
		for (int i = 0; i < servicePrice.size(); i++) {
			ServicePrice pricedService = servicePrice.get(i);
			AdditionalService service = pricedService.getService();
			if (service.getType().equals(wantedService)) {
				servicePrice.get(i).setPrice(newPrice);
				System.out.printf("Changed price: %s - %.2f", wantedService, newPrice);
				System.out.println();
				break;
			} else {
				System.out.println("Wanted service not found!");
			}
		}
		System.out.println();
	}
	
	public void updateRoomPricing(RoomType wantedRoom, double newPrice, Pricing pricing) {
		ArrayList<RoomPrice> roomPrice = pricing.getRoomPrice();
		for (int i = 0; i < roomPrice.size(); i++) {
			RoomPrice pricedRoom = roomPrice.get(i);
			RoomType room = pricedRoom.getRoom();
			if (room.equals(wantedRoom)) {
				roomPrice.get(i).setPrice(newPrice);
				System.out.printf("Changed price: %s - %.2f", wantedRoom, newPrice);
				System.out.println();
				break;
			} else {
				System.out.println("Wanted room not found!");
			}
		}
		System.out.println();
	}
	
	public void readPricing(Pricing pricing) {
		FormatManager formatManager = new FormatManager();
		String formattedStartDate = formatManager.dateToString(pricing.getStartDate());
		String formattedEndDate = formatManager.dateToString(pricing.getEndDate());
		System.out.printf("DATE: %s to %s", formattedStartDate, formattedEndDate);
		System.out.println();
		System.out.println();
		System.out.println("*** ROOM PRICES ***");
		ArrayList<RoomPrice> roomPrices = pricing.getRoomPrice();
		for (int i = 0; i < roomPrices.size(); i++) {
			System.out.println(roomPrices.get(i).toString());
		}
		System.out.println();
		System.out.println("*** SERVICE PRICES ***");
		ArrayList<ServicePrice> servicePrices = pricing.getServicePrice();
		for (int i = 0; i < servicePrices.size(); i++) {
			System.out.println(servicePrices.get(i).toString());
		}
		System.out.println();
	}
	
	public void deletePricing(int id) {
		Pricing pricingToDelete = findPricingByID(id);
		if (pricingToDelete != null) {
			for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
				if (set.getKey() == id) {
					this.seasonPricing.get(id).setActive(false);
					System.out.println("Pricing successfully deactivated!");
					System.out.println();
				}
			}
		}
	}
	
	public Pricing findPricingByID(int id) {
		for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
			if (set.getKey() == id) {
				return set.getValue();
			}
		}
		return null;
	}
	
	public int findPricingIDByDate(LocalDate startDate, LocalDate endDate) {
		for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
			if (set.getValue().getStartDate().equals(startDate) && set.getValue().getEndDate().equals(endDate)) {
				return set.getKey();
			}
		}
		return -1;
	}
	
	public int findPricingIDforDate(LocalDate date) {
		int id = -1;
		for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
			System.out.println(set.getKey());
			if (set.getValue().getStartDate().isBefore(date) && set.getValue().getEndDate().isAfter(date) || 
					set.getValue().getStartDate().isEqual(date) || set.getValue().getEndDate().isEqual(date)) {
				id = set.getKey();
			}
		}
		System.out.println("cenovnik id");
		System.out.println(id);
		return id;
	}
	
	
	// seasonal pricing is active, special prices are not considered "active"
	public Pricing findActivePricing() {
		for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
			if (set.getValue().isActive()) {
				return set.getValue();
			}
		}
		return null;
	}
	
	public int findNextPricingID() {
		int id = 0;
		if (this.seasonPricing.size() == 0) {
			return 1000;
		} else {
			for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
				id = set.getKey() + 1;
			}
		}
		return id;
	}

	public String getPricingFileName() {
		return pricingFileName;
	}

	public void setPricingFileName(String pricingFileName) {
		this.pricingFileName = pricingFileName;
	}
	
	public boolean loadData(ManagerFactory managers) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(this.pricingFileName));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] token = line.split(";");
				int id = Integer.parseInt(token[0]);
				LocalDate start = LocalDate.parse(token[1], DateTimeFormatter.ofPattern("dd.MM.yyyy."));
				LocalDate end = LocalDate.parse(token[2], DateTimeFormatter.ofPattern("dd.MM.yyyy."));
				String[] rp = token[3].split(",");
				ArrayList<RoomPrice> roomPrice = new ArrayList<>();
				for (int i = 0; i < rp.length; i++) {
					String[] value = rp[i].split("-");
					RoomType type;
					if (value[0].equals("single bed")) {
						type = RoomType.SINGLE_BED;
					} else if (value[0].equals("double bed")) {
						type = RoomType.DOUBLE_BED;
					} else if (value[0].equals("king bed")) {
						type = RoomType.KING_BED;
					} else if (value[0].equals("triple bed")) {
						type = RoomType.TRIPLE_BED;
					} else {
						type = RoomType.QUAD_BED;
					}
					roomPrice.add(new RoomPrice(type, Double.parseDouble(value[1])));
				}
				String[] sp = token[4].split(",");
				ArrayList<ServicePrice> servicePrice = new ArrayList<>();
				for (int i = 0; i < sp.length; i++) {
					String[] value = sp[i].split("-");
					AdditionalService s = managers.getServiceManager().findServiceByName(value[0]);
					servicePrice.add(new ServicePrice(s, Double.parseDouble(value[1])));
				}
				Pricing pricing = new Pricing(id, start, end, roomPrice, servicePrice);
				this.seasonPricing.put(id, pricing);
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
			pw = new PrintWriter(new FileWriter(this.pricingFileName, false));
			for (Map.Entry<Integer, Pricing> set : this.seasonPricing.entrySet()) {
				pw.println(set.getValue().toFileString());
			}
			pw.close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}

}