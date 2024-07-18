package utils;

public class AppSettings {
	
	private String employeeFileName;
	private String guestFileName;
	private String serviceFileName;
	private String roomFileName;
	private String reservationFileName;
	private String pricingFileName;
	private String maidFileName;
	
	public AppSettings(String employeesFilename, String guestFileName, String serviceFileName, 
			String roomFileName, String reservationFileName, String pricingFileName, String maidFileName) {
		this.employeeFileName = employeesFilename;
		this.guestFileName = guestFileName;
		this.serviceFileName = serviceFileName;
		this.roomFileName = roomFileName;
		this.reservationFileName = reservationFileName;
		this.pricingFileName = pricingFileName;
		this.maidFileName = maidFileName;
	}

	public String getEmployeesFilename() {
		return employeeFileName;
	}

	public void setEmployeesFilename(String employeesFilename) {
		this.employeeFileName = employeesFilename;
	}

	public String getGuestFileName() {
		return guestFileName;
	}

	public void setGuestFileName(String guestFileName) {
		this.guestFileName = guestFileName;
	}

	public String getServiceFileName() {
		return serviceFileName;
	}

	public void setServiceFileName(String serviceFileName) {
		this.serviceFileName = serviceFileName;
	}

	public String getRoomFileName() {
		return roomFileName;
	}

	public void setRoomFileName(String roomFileName) {
		this.roomFileName = roomFileName;
	}

	public String getReservationFileName() {
		return reservationFileName;
	}

	public void setReservationFileName(String reservationFileName) {
		this.reservationFileName = reservationFileName;
	}

	public String getPricingFileName() {
		return pricingFileName;
	}

	public void setPricingFileName(String pricingFileName) {
		this.pricingFileName = pricingFileName;
	}

	public String getMaidFileName() {
		return maidFileName;
	}

	public void setMaidFileName(String maidFileName) {
		this.maidFileName = maidFileName;
	}

}
