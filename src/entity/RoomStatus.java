package entity;

public enum RoomStatus {
	TAKEN, PREPARATION, AVAILABLE;
	
	private String[] status = {"taken", "preparation", "available"};
	
	@Override
	public String toString() {
		return status[this.ordinal()];
	}

}
