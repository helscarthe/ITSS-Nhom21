package entity;

public class FridgeEntity {

	private int fridge_food_id;
	private String raw_food_name;
	private int food_id;
	private int number;
	private String expiry_date;
	private int food_type;
	private String unit;
	
	public static final String[] food_type_enum = {"Rau", "Thịt", "Hoa quả"};

	
	public FridgeEntity() {
	}

	public FridgeEntity(int fridge_food_id,String raw_food_name, int food_id, int number, String expiry_date, int food_type,
			String unit) {
		super();
		this.raw_food_name = raw_food_name;
		this.fridge_food_id = fridge_food_id;
		this.food_id = food_id;
		this.number = number;
		this.expiry_date = expiry_date;
		this.food_type = food_type;
		this.unit = unit;
	}
	
	
	

	public FridgeEntity(int food_id, String raw_food_name, int food_type, String unit) {
		super();
		this.raw_food_name = raw_food_name;
		this.food_id = food_id;
		this.food_type = food_type;
		this.unit = unit;
	}

	public String getRaw_food_name() {
		return raw_food_name;
	}

	public void setRaw_food_name(String raw_food_name) {
		this.raw_food_name = raw_food_name;
	}

	public int getFridge_food_id() {
		return fridge_food_id;
	}


	public void setFridge_food_id(int fridge_food_id) {
		this.fridge_food_id = fridge_food_id;
	}

	public int getFood_id() {
		return food_id;
	}


	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public String getExpiry_date() {
		return expiry_date;
	}


	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}


	public int getFood_type() {
		return food_type;
	}


	public void setFood_type(int food_type) {
		this.food_type = food_type;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public static String[] getFoodTypeEnum() {
		return food_type_enum;
	}


	public String getFood_typeString() {
		return food_type_enum[food_type];
	}
}
