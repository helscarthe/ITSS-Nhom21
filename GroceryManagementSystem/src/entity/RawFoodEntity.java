package entity;

public class RawFoodEntity {
	private int raw_food_id;
	private String raw_food_name;
	private int food_type;
	private String unit;
	
	public static final String[] food_type_enum = {"Rau", "Đạm", "Hoa quả", "Tinh bột", "Gia vị"};
	
	public RawFoodEntity(int raw_food_id, String raw_food_name, int food_type, String unit) {
		this.raw_food_id = raw_food_id;
		this.raw_food_name = raw_food_name;
		this.unit = unit;
		this.food_type = food_type;
	}

	public int getRaw_food_id() {
		return raw_food_id;
	}

	public String getRaw_food_name() {
		return raw_food_name;
	}

	public int getFood_type() {
		return food_type;
	}

	public String getUnit() {
		return unit;
	}
	
	public String getFood_typeString() {
		return food_type_enum[food_type];
	}
	
	public static int getFood_typeStringToID(String type) {
		for (int i = 0; i < food_type_enum.length; i++) {
			if (type.equals(food_type_enum[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public int food_type_stringToIndex(String food_typeStr) {
		for (int i = 0; i < food_type_enum.length; i++) {
			if (food_typeStr.equals(food_type_enum[i])) {
				return i;
			}
		}
		return -1;
	}
}
