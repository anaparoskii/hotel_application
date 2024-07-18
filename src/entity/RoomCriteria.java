package entity;

public enum RoomCriteria {
	AIR_CONDITION, BALCONY, TV, SMOKING;
	
	private String[] description = {"air condition", "balcony", "tv", "smoking"};
	
	@Override
	public String toString() {
		return description[this.ordinal()];
	}

}
