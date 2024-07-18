package entity;

public class AdditionalService {
	private int id;
	private String type;
	
	public AdditionalService() {}
	
	public AdditionalService(int id, String type) {
		this.id = id;
		this.type = type;
	}
	
	@Override
	public String toString() {
		String s = String.format("Service type: %-10s", this.getType());
		return s;
	}
	
	public String toFileString() {
		return String.valueOf(this.id) + ";" + this.type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
