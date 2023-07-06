package controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import entity.RawFoodEntity;
import entity.UserEntity;
import service.SqliteConnection;

public class AdminController {
	
	public UserEntity user;
	
	public RawFoodEntity food;

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public void setFood(RawFoodEntity food) {
		this.food = food;
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

}
