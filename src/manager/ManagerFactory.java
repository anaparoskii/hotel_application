package manager;

import utils.AppSettings;

public class ManagerFactory {
	private AppSettings appSettings;
	private EmployeeManager employeeManager;
	private GuestManager guestManager;
	private PricingManager pricingManager;
	private ReservationManager reservationManager;
	private RoomManager roomManager;
	private ServiceManager serviceManager;
	private MaidManager maidManager;
	
	public ManagerFactory() {
		this.employeeManager = new EmployeeManager();
		this.guestManager = new GuestManager();
		this.pricingManager = new PricingManager();
		this.reservationManager = new ReservationManager();
		this.roomManager = new RoomManager();
		this.serviceManager = new ServiceManager();
	}
	
	public ManagerFactory(AppSettings appSettings) {
		this.appSettings = appSettings;
		this.employeeManager = new EmployeeManager(this.appSettings.getEmployeesFilename());
		this.guestManager = new GuestManager(this.appSettings.getGuestFileName());
		this.pricingManager = new PricingManager(this.appSettings.getPricingFileName());
		this.reservationManager = new ReservationManager(this.appSettings.getReservationFileName());
		this.roomManager = new RoomManager(this.appSettings.getRoomFileName());
		this.serviceManager = new ServiceManager(this.appSettings.getServiceFileName());
		this.maidManager = new MaidManager(this.appSettings.getMaidFileName());
	}
	
	public void loadData() {
		this.employeeManager.loadData();
		this.guestManager.loadData();
		this.serviceManager.loadData();
		this.roomManager.loadData();
		this.reservationManager.loadData(this);
		this.pricingManager.loadData(this);
		this.maidManager.loadData(this);
	}
	
	public void saveData() {
		this.employeeManager.saveData();
		this.guestManager.saveData();
		this.serviceManager.saveData();
		this.roomManager.saveData();
		this.reservationManager.saveData();
		this.pricingManager.saveData();
		this.maidManager.saveData(this);
	}
	
	public EmployeeManager getEmployeeManager() {
		return employeeManager;
	}
	public void setEmployeeManager(EmployeeManager employeeManager) {
		this.employeeManager = employeeManager;
	}
	public GuestManager getGuestManager() {
		return guestManager;
	}
	public void setGuestManager(GuestManager guestManager) {
		this.guestManager = guestManager;
	}
	public PricingManager getPricingManager() {
		return pricingManager;
	}
	public void setPricingManager(PricingManager pricingManager) {
		this.pricingManager = pricingManager;
	}
	public ReservationManager getReservationManager() {
		return reservationManager;
	}
	public void setReservationManager(ReservationManager reservationManager) {
		this.reservationManager = reservationManager;
	}
	public RoomManager getRoomManager() {
		return roomManager;
	}
	public void setRoomManager(RoomManager roomManager) {
		this.roomManager = roomManager;
	}
	public ServiceManager getServiceManager() {
		return serviceManager;
	}
	public void setServiceManager(ServiceManager serviceManager) {
		this.serviceManager = serviceManager;
	}

	public AppSettings getAppSettings() {
		return appSettings;
	}

	public void setAppSettings(AppSettings appSettings) {
		this.appSettings = appSettings;
	}

	public MaidManager getMaidManager() {
		return maidManager;
	}

	public void setMaidManager(MaidManager maidManager) {
		this.maidManager = maidManager;
	}
	
	

}
