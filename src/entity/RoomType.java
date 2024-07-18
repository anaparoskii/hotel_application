package entity;

public enum RoomType {
	SINGLE_BED("1"), DOUBLE_BED("1+1"), KING_BED("2"), TRIPLE_BED("2+1"), QUAD_BED("2+1+1");
	
	final String code;
	RoomType(String s) {
		this.code = s;
	}
	
	public String showCode() {
		return code;
	}
	
	private String[] description = {"single bed", "double bed", "king bed", "triple bed", "quad bed"};
	
	@Override
	public String toString() {
		return description[this.ordinal()];
	}

}
