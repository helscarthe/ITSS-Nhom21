package entity;

public class ShoppingItemEntity extends RawFoodEntity {

	private int number;
	
	public ShoppingItemEntity(int raw_food_id, String raw_food_name, int food_type, String unit, int number) {
		super(raw_food_id, raw_food_name, food_type, unit);
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

}
