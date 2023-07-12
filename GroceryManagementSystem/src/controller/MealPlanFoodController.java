package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.MealPlanFood;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.SqliteConnection;

public class MealPlanFoodController {
	public ObservableList<MealPlanFood> loadMealList(){
		ObservableList<MealPlanFood> mealPlanList = FXCollections.observableArrayList();
    	int userId = UserSingleton.getInstance().getUser_id();
    	Connection conn = SqliteConnection.Connector();
    	String query = "select m.meal_plan_id as meal_plan_id, m.user_id as user_id, m.date as date, m.meal_number as meal_number, m.dish_id as dish_id, d.dish_name as dish_name from meal_plan m, dishes d where m.dish_id=d.dish_id and user_id = " + userId + ";";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			while(rs.next()) {
				MealPlanFood mealFood = new MealPlanFood(rs.getInt("meal_plan_id"), rs.getInt("user_id"), rs.getString("date"), rs.getInt("meal_number"), rs.getInt("dish_id"), rs.getString("dish_name"));
				mealPlanList.add(mealFood);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error query database");
		}
		
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return mealPlanList;
	}
	public void deleteMeal(int mealId) {
		Connection conn = SqliteConnection.Connector();
        String query = "DELETE FROM meal_plan WHERE meal_plan_id="+mealId+";";
        Statement sttm = null;
        try {
            	sttm = conn.createStatement();
            	sttm.executeUpdate(query);
           } catch (SQLException e) {
            	e.printStackTrace();
            	System.out.println("Error query database");
           }
        try {
          	conn.close();
        } catch (Exception e) {
            e.printStackTrace();
   		}
	}
}
