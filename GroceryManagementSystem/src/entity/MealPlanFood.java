package entity;

public class MealPlanFood {
	private int mealPlanId;
	private int userId;
	private String date;
	private int mealNumber;
	private String foodName;
	private int dishId;
	public MealPlanFood(int mealPlanId, int userId, String date, int mealNumber, int dishId, String foodName) {
		this.mealPlanId = mealPlanId;
		this.userId = userId;
		this.date = date;
		this.mealNumber = mealNumber;
		this.dishId = dishId;
		this.foodName = foodName;
	}
	public int getMealPlanId() {
		return mealPlanId;
	}
	public int getUserId() {
		return userId;
	}
	public String getDate() {
		return date;
	}
	public int getMealNumber() {
		return mealNumber;
	}
	public String getFoodName() {
		return foodName;
	}
	public int getDishId() {
		return dishId;
	}
}
