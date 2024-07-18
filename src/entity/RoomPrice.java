package entity;


public class RoomPrice {
	private RoomType room;
	private double price;
	
	public RoomPrice() {}
	
	public RoomPrice(RoomType room, double price) {
		this.room = room;
		this.price = price;
	}
	
	@Override
	public String toString() {
		String s = String.format("Room type: %-15s Price for 1 night: %.2f", this.room, this.price);
		return s;
	}
	
	public RoomType getRoom() {
		return room;
	}
	public void setRoom(RoomType room) {
		this.room = room;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
