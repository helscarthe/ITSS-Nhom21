package entity;

public class DishEntity {
	
	private int dish_id;
	private String dish_name;
	private String recipe;
	
	public DishEntity(int dish_id, String dish_name, String recipe) {
		this.dish_id = dish_id;
		this.dish_name = dish_name;
		this.recipe = recipe;
	}
	
	public int getDish_id() {
		return dish_id;
	}
	
	public String getDish_name() {
		return dish_name;
	}
	
	public String getRecipe() {
		return recipe;
	}
	
}
