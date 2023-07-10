package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FridgeEntity extends RawFoodEntity {

	private int fridge_food_id;
	private int number;
	private String expiry_date;

	public FridgeEntity(int fridge_food_id,String raw_food_name, int food_id, int number, String expiry_date, int food_type,
			String unit) {
		super(food_id, raw_food_name, food_type, unit);
		this.fridge_food_id = fridge_food_id;
		this.number = number;
		this.expiry_date = expiry_date;
	}

	public int getFridge_food_id() {
		return fridge_food_id;
	}

	public int getNumber() {
		return number;
	}

	public String getExpiry_date() {
		return expiry_date;
	}

	public long getRemainingDays() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate expDate = LocalDate.parse(expiry_date, formatter);
		
		long res = now.until(expDate, ChronoUnit.DAYS);
		
		return res;
	}

	public String getRemainingDaysString() {
		String res = getRemainingDays() + " ngày";
		
		return res;
	}
}
