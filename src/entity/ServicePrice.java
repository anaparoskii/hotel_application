package entity;

public class ServicePrice {
	private AdditionalService service;
	private double price;
	
	public ServicePrice() {}
	
	public ServicePrice(AdditionalService service, double price) {
		this.service = service;
		this.price = price;
	}
	
	@Override
	public String toString() {
		String s = String.format("%-15s Daily price: %.2f", this.service, this.price);
		return s;
	}
	
	public AdditionalService getService() {
		return service;
	}
	public void setService(AdditionalService service) {
		this.service = service;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
