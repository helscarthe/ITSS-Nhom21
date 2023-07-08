package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import entity.DishEntity;
import entity.RawFoodEntity;
import entity.UserEntity;
import service.SqliteConnection;

public class AdminController {
	
	private UserEntity user;
	
	private RawFoodEntity food;
	
	private DishEntity dish;

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setFood(RawFoodEntity food) {
		this.food = food;
	}

	public void setDish(DishEntity dish) {
		this.dish = dish;
	}

	public void updateUser(String username, String password, int isAdmin) {

    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = null;
    	Statement sttm = null;
		
		if (user == null) {
	    	query = "insert into users (username, password_hash, is_admin) "
	    			+ "values ('" + username + "', '" + password + "', '" + isAdmin + "');";
		} else {
			query = "update users "
					+ "set username = '" + username + "', "
					+ "password_hash = '" + password + "'," 
					+ "is_admin = '" + isAdmin + "' "
					+ "where user_id = '" + user.getUser_id() + "';";
		}
		
    	// execute query
		try {
			sttm = conn.createStatement();
			sttm.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateFood(String raw_food_name, int food_type, String unit) {

    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = null;
    	Statement sttm = null;
		
		if (food == null) {
	    	query = "insert into raw_foods (raw_food_name, food_type, unit) "
	    			+ "values ('" + raw_food_name + "', '" + food_type + "', '" + unit + "');";
		} else {
			query = "update raw_foods "
					+ "set raw_food_name = '" + raw_food_name + "', "
					+ "food_type = '" + food_type + "'," 
					+ "unit = '" + unit + "' "
					+ "where raw_food_id = '" + food.getRaw_food_id() + "';";
		}
		
    	// execute query
		try {
			sttm = conn.createStatement();
			sttm.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void updateDish(String dish_name, String recipe) {

    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = null;
    	Statement sttm = null;
		
		if (dish == null) {
	    	query = "insert into dishes (dish_name, recipe) "
	    			+ "values ('" + dish_name + "', '" + recipe + "');";
		} else {
			query = "update dishes "
					+ "set dish_name = '" + dish_name + "', "
					+ "recipe = '" + recipe + "'" 
					+ "where dish_id = '" + dish.getDish_id() + "';";
		}
		
    	// execute query
		try {
			sttm = conn.createStatement();
			sttm.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
