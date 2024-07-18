package manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FormatManager {
	public FormatManager() {}
	
	public Date asDate(LocalDate localDate) {
	    return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
	
	public String dateToString(LocalDate date) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy.");	
		return date.format(format);
	}
	
	public String dateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy.");  
		String s = dateFormat.format(date);
		return s;
	}
	
	public Date asDate(String string) {
		LocalDate localDate = LocalDate.parse(string, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		return asDate(localDate);
	}
	
	public LocalDate asLocalDate(Date date) {
		String dateString = dateToString(date);
		LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		return localDate;
	}
	
	public LocalDate asLocalDate(String date) {
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy."));
		return localDate;
	}

}
