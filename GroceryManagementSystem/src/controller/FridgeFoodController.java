package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import entity.FridgeEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.SqliteConnection;

public class FridgeFoodController {

	public boolean deleteFood(int foodId, int userId) {

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			sttm.executeUpdate("delete from fridge_food where user_id=" + String.valueOf(userId)
					+ " and food_id=" + String.valueOf(foodId) + ";");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public ObservableList<FridgeEntity> getFoodListByUserId(int userId) {

		ObservableList<FridgeEntity> foodList = FXCollections.observableArrayList();

		String foodListQuery = "select fp.fridge_food_id, fp.food_id, fp.number, fp.expiry_date, rf.raw_food_name, rf.food_type, rf.unit from fridge_food as fp, raw_foods as rf where fp.food_id = rf.raw_food_id and fp.user_id="
				+ String.valueOf(userId) + ";";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(foodListQuery);

			while (rs.next()) {
				if (!rs.getString("expiry_date").equals(LocalDate.now().toString())) {
					foodList.add(new FridgeEntity(rs.getInt("fridge_food_id"), rs.getString("raw_food_name"),
							rs.getInt("food_id"), rs.getInt("number"), rs.getString("expiry_date"), rs.getInt("food_type"),
							rs.getString("unit")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foodList;
	}

	public ObservableList<FridgeEntity> getFoodListByType(String typeFood) {

		ObservableList<FridgeEntity> foodList = FXCollections.observableArrayList();

		int foodTypeInt;

		if (typeFood.equalsIgnoreCase("Rau")) {
			foodTypeInt = 1;
		} else if (typeFood.equalsIgnoreCase("Thịt")) {
			foodTypeInt = 2;
		} else {
			foodTypeInt = 3;
		}

		String query = "select fp.fridge_food_id, fp.food_id, fp.number, fp.expiry_date, rf.raw_food_name, rf.food_type, rf.unit from fridge_food as fp, raw_food as rf where fp.food_id = rf.raw_food_id and fp.food_type="
				+ foodTypeInt + ";";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			while (rs.next()) {
				foodList.add(new FridgeEntity(rs.getInt("fridge_food_id"), rs.getString("raw_food_name"),
						rs.getInt("food_id"), rs.getInt("number"), rs.getString("expiry_date"), rs.getInt("food_type"),
						rs.getString("unit")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foodList;

	}

	public FridgeEntity getFoodByTypeAndName(String typeFood, String nameFood) {

		int foodTypeInt;

		if (typeFood.equalsIgnoreCase("Rau")) {
			foodTypeInt = 1;
		} else if (typeFood.equalsIgnoreCase("Thịt")) {
			foodTypeInt = 2;
		} else {
			foodTypeInt = 3;
		}

		String query = "select fp.fridge_food_id, fp.food_id, fp.number, fp.expiry_date, rf.raw_food_name, rf.food_type, rf.unit from fridge_food as fp, raw_food as rf where fp.food_id = rf.raw_food_id and fp.food_type="
				+ foodTypeInt + "and rf.raw_food_name='" + nameFood + "';";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return new FridgeEntity(rs.getInt("fridge_food_id"), rs.getString("raw_food_name"),
						rs.getInt("food_id"), rs.getInt("number"), rs.getString("expiry_date"), rs.getInt("food_type"),
						rs.getString("unit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}
	
	public FridgeEntity getFoodModelByName(String nameFood) {


		String query = "select * from raw_foods where raw_food_name= '" + nameFood + "' collate nocase;";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return new FridgeEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"), rs.getInt("food_type"),
						rs.getString("unit"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}

	public int getFoodIdByName(String nameFood) {

		String query = "select raw_food_id from raw_foods where raw_food_name= '" + nameFood + "' collate nocase;";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return rs.getInt("raw_food_id");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 0;
	}
	
	public boolean insertFoodIntoFridge(String nameFood, int userId, int number, String expiry_date) {

		int foodId = getFoodIdByName(nameFood);
		
		if (foodId != 0) {
			String insertIntoFridge = "insert into fridge_food (user_id, food_id, number, expiry_date) values (?, ?, ?, ?);";

			try (Connection conn = SqliteConnection.Connector();
					PreparedStatement psttm = conn.prepareStatement(insertIntoFridge);) {
				psttm.setInt(1, userId);
				psttm.setInt(2, foodId);
				psttm.setInt(3, number);
				psttm.setString(4, expiry_date);
				

				if (psttm.execute()) {
					return true;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		} 
		
		return false;

	}
	
	public void updateFood(int foodId, int userId, int soLuong) {

		String query = "update fridge_food set number = ? where food_id = ? and user_id = ?;";

		try (Connection conn = SqliteConnection.Connector(); PreparedStatement psttm = conn.prepareStatement(query);) {

			psttm.setInt(1, soLuong);
			psttm.setInt(2, foodId);
			psttm.setInt(3, userId);

			psttm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
