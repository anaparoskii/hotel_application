package entity;

public enum EmployeeTitle {
	ADMINISTRATOR, RECEPTIONIST, MAID;
	
	private String[] description = {"administator", "receptionist", "maid"};
	
	@Override
	public String toString() {
		return description[this.ordinal()];
	}
}
