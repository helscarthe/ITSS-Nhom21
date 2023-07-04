package entity;

public class MealPlanFood {
	public int mealPlanId;
	public int userId;
	public String date;
	public int mealNumber;
	public String foodName;
	public int dishId;
	public MealPlanFood(int mealPlanId, int userId, String date, int mealNumber, int dishId, String foodName) {
		super();
		this.mealPlanId = mealPlanId;
		this.userId = userId;
		this.date = date;
		this.mealNumber = mealNumber;
		this.dishId = dishId;
		this.foodName = foodName;
	}
}
