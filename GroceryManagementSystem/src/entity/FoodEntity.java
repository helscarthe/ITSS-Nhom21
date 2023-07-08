package entity;

public class FoodEntity {
	private int raw_food_id;
	private String raw_food_name;
	private int food_type;
	private int number;
	private String unit;
	
	public static final String[] food_type_enum = {"Rau", "Thịt", "Hoa quả"};

	public FoodEntity(int raw_food_id, String raw_food_name, int food_type, int number, String unit) {
		super();
		this.raw_food_id = raw_food_id;
		this.raw_food_name = raw_food_name;
		this.food_type = food_type;
		this.number = number;
		this.unit = unit;
	}

	public FoodEntity(int raw_food_id, String raw_food_name, int food_type, String unit) {
		super();
		this.raw_food_id = raw_food_id;
		this.raw_food_name = raw_food_name;
		this.food_type = food_type;
		this.unit = unit;
	}
	
	public int getRaw_food_id() {
		return raw_food_id;
	}

	public void setRaw_food_id(int raw_food_id) {
		this.raw_food_id = raw_food_id;
	}

	public String getRaw_food_name() {
		return raw_food_name;
	}

	public void setRaw_food_name(String raw_food_name) {
		this.raw_food_name = raw_food_name;
	}

	public int getFood_type() {
		return food_type;
	}

	public void setFood_type(int food_type) {
		this.food_type = food_type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getFood_typeString() {
		return food_type_enum[food_type];
	}
}
