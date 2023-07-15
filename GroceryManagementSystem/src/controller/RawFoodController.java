package controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import entity.RawFoodEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import service.SqliteConnection;

public class RawFoodController {
	
	public RawFoodEntity getFoodModelByName(String nameFood) {


		String query = "select * from raw_foods where raw_food_name= '" + nameFood + "' collate nocase;";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);

			if (rs.next()) {
				return new RawFoodEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"), rs.getInt("food_type"),
						rs.getString("unit"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return null;

	}
	
	public ObservableList<String> getAvailableFoods() {
		ObservableList<String> res = FXCollections.observableArrayList();
		
		String query = "select raw_food_name from raw_foods;";

		try (Connection conn = SqliteConnection.Connector(); Statement sttm = conn.createStatement();) {

			ResultSet rs = sttm.executeQuery(query);
			
			while (rs.next()) {
				String food_name = rs.getString("raw_food_name");
				res.add(food_name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return res;
	}

}
