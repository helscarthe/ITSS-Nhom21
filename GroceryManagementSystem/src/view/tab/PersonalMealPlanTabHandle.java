package view.tab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.MealPlanFood;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.SqliteConnection;

public class PersonalMealPlanTabHandle {
    
    @FXML
    private TableColumn<MealPlanFood, String> colMealDate;

    @FXML
    private TableColumn<MealPlanFood, String> colMealPlanFood;

    @FXML
    private Button btnXoaMon;

    @FXML
    private Button btnTimKiemMonDinhNau;

    @FXML
    private TextField txtTimKiemMonDinhNau;

    @FXML
    private Button btnThemMonDinhNau;

    @FXML
    private TableView<MealPlanFood> tblQuanLyMonDinhNau;

	ObservableList<MealPlanFood> mealPlanList = FXCollections.observableArrayList();
	ObservableList<MealPlanFood> mealPlanFilterList = FXCollections.observableArrayList();
	
    @FXML
    void loadMealPlan() {
    	int userId = UserSingleton.getInstance().getUser_id();
    	Connection conn = SqliteConnection.Connector();
    	String query = "select m.meal_plan_id as meal_plan_id, m.user_id as user_id, m.date as date, m.meal_number as meal_number, m.dish_id as dish_id, d.dish_name as dish_name from meal_plan m, dishes d where m.dish_id=d.dish_id and user_id = " + userId + ";";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			mealPlanList.clear();
			ResultSet rs = sttm.executeQuery(query);
			while(rs.next()) {
				MealPlanFood mealFood = new MealPlanFood(rs.getInt("meal_plan_id"), rs.getInt("user_id"), rs.getString("date"), rs.getInt("meal_number"), rs.getInt("dish_id"), rs.getString("dish_name"));
				mealPlanList.add(mealFood);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error query database");
		}
		filter(txtTimKiemMonDinhNau.getText());
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void addMealPlan(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formAddDish = null;
    	try {
    		formAddDish = FXMLLoader.load(getClass().getResource("/fxml/AddMealPlanForm.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddDish);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadMealPlan();
    }
    @FXML
    void searchMealPlan(KeyEvent event) {
    	filter(((TextField)(event.getSource())).getText());
    }
    void filter(String searchKey) {
    	mealPlanFilterList.clear();
    	ObservableList<MealPlanFood> matchs = FXCollections.observableArrayList();
    	for(MealPlanFood food: mealPlanList) {
    		if(food.getFoodName().contains(searchKey))
    		matchs.add(food);
    	}
    	mealPlanFilterList.addAll(matchs);
    }
    @FXML
    void deleteMealPlan(ActionEvent event) {
        Connection conn = SqliteConnection.Connector();
        if(tblQuanLyMonDinhNau.getSelectionModel() == null) return;
        String query = "DELETE FROM meal_plan WHERE meal_plan_id="+tblQuanLyMonDinhNau.getSelectionModel().getSelectedItem().getMealPlanId()+";";
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
        loadMealPlan();
    }
    
    @FXML
    void initialize() {
		loadMealPlan();	
		colMealPlanFood.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, String>("foodName")
		);
		colMealDate.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, String>("date")
		);
		tblQuanLyMonDinhNau.setItems(mealPlanFilterList);
    }
}
