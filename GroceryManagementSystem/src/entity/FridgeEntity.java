package entity;

public class FridgeEntity extends RawFoodEntity {

	private int fridge_food_id;
	private int number;
	private String expiry_date;
	
	public static final String[] food_type_enum = {"Rau", "Thịt", "Hoa quả"};

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
}
