package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.DishEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.SqliteConnection;

public class DishController {
	public ObservableList<DishEntity> loadFavDishList(){
		ObservableList<DishEntity> favDishList = FXCollections.observableArrayList();
		Connection conn = SqliteConnection.Connector();
    	int userId = UserSingleton.getInstance().getUser_id();
    	String query = "select f.dish_id dish_id, d.dish_name dish_name, d.recipe recipe from fav_dish as f, dishes as d where f.dish_id = d.dish_id and f.user_id = "+userId+";";
    	
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			ObservableList<DishEntity> list = FXCollections.observableArrayList();
			while(rs.next()) {
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"), rs.getString("recipe"));
				list.add(dish);
			}
			favDishList.addAll(list);

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return favDishList;
	}
	public ObservableList<DishEntity> loadDishList(){
		ObservableList<DishEntity> dishList = FXCollections.observableArrayList();
		Connection conn = SqliteConnection.Connector();
    	String query = "select dish_id, dish_name, recipe from dishes;";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			while(rs.next()) {
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"), rs.getString("recipe"));
				dishList.add(dish);
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
    	return dishList;
	}
	public boolean addFavDish(int dishId) {
    	int userId = UserSingleton.getInstance().getUser_id();
		String values = "("+dishId+","+userId+")";
    	Connection conn = SqliteConnection.Connector();
    	String query = "insert into fav_dish(dish_id, user_id) values"+values+";";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			sttm.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Looi");
			return false;
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			return false;
		}
    	return true;
	}
	public void removeFavDish(int dishId) {
		int userId = UserSingleton.getInstance().getUser_id();
		Connection conn = SqliteConnection.Connector();
		String query = "DELETE FROM fav_dish WHERE user_id="+userId+" and dish_id="+dishId+";";
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
