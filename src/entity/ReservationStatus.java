package entity;

public enum ReservationStatus {
	WAITING, ACCEPTED, CANCELED, DECLINED;
	
	private String[] status = {"waiting", "accepted", "canceled", "declined"};
	
	@Override
	public String toString() {
		return status[this.ordinal()];
	}
}
