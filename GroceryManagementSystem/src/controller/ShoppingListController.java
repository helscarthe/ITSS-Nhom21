package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.RawFoodEntity;
import entity.ShoppingItemEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.SqliteConnection;

public class ShoppingListController extends RawFoodController {
	
	ShoppingItemEntity item;

	public void setItem(ShoppingItemEntity item) {
		this.item = item;
	}
	
	public boolean updateItem(int user_id, int raw_food_id, int number) {

    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = null;
    	Statement sttm = null;
    	int res = 0;
		
		if (item == null) {
	    	query = "insert into shopping_list (user_id, food_id, number) "
	    			+ "values ('" + user_id + "', '" + raw_food_id + "', '" + number + "');";
		} else {
			query = "update shopping_list "
					+ "set number = '" + number + "'"
					+ "where food_id = '" + item.getRaw_food_id() + "'"
							+ "and user_id = '" + user_id + "';";
		}
		
    	// execute query
		try {
			sttm = conn.createStatement();
			res = sttm.executeUpdate(query);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		if (res == 0) {
			return false;
		}

		return true;
	}
	
	public void deleteItem() {
		// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	String query = "delete from shopping_list "
    			+ "where user_id = '" + UserSingleton.getInstance().getUser_id()
    			+ "' and food_id = '" + item.getRaw_food_id()+ "'";
    	Statement sttm = null;
		
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
	
	public ObservableList<ShoppingItemEntity> loadShoppingList(int user_id) {
    	
    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	Statement sttm = null;
    	ObservableList<ShoppingItemEntity> dataFood = null;
		
		// execute query
		try {
	    	String query;
			sttm = conn.createStatement();
			ResultSet rs;
	    	query = "select * from shopping_list where user_id = " + user_id;
			
			// dataFood is a class attribute
			dataFood = FXCollections.observableArrayList();
			rs = sttm.executeQuery(query);
			
			while(rs.next()) {
				RawFoodEntity rawFood = getFoodEntity(rs.getInt("food_id"));
				ShoppingItemEntity item = new ShoppingItemEntity(rawFood.getRaw_food_id(),
						rawFood.getRaw_food_name(),rawFood.getFood_type(),rawFood.getUnit(),
						rs.getInt("number"));
				dataFood.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataFood;
	}
	
	public RawFoodEntity getFoodEntity(int raw_food_id) {
    	
    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	Statement sttm = null;
		RawFoodEntity dish = null;
		
		// execute query
		try {
	    	String query;
			sttm = conn.createStatement();
			ResultSet rs;
	    	query = "select * from raw_foods where raw_food_id = " + raw_food_id;
			
			rs = sttm.executeQuery(query);
			
			while(rs.next()) {
				dish = new RawFoodEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"),
						rs.getInt("food_type"),rs.getString("unit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dish;
		
	}
}
