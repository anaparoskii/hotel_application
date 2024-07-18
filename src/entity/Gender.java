package entity;

public enum Gender {
	MALE, FEMALE;
	
	private String[] description = {"male", "female"};
	
	@Override
	public String toString() {
		return description[this.ordinal()];
	}
}
