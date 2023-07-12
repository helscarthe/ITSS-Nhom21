package view.user.tab;

import java.io.IOException;

import controller.MealPlanFoodController;
import entity.MealPlanFood;
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
    private MealPlanFoodController controller;
	ObservableList<MealPlanFood> mealPlanList = FXCollections.observableArrayList();
	ObservableList<MealPlanFood> mealPlanFilterList = FXCollections.observableArrayList();
	
    @FXML
    void loadMealPlan() {
    	mealPlanList = controller.loadMealList();
    	filter(txtTimKiemMonDinhNau.getText());
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
    		if(food.getFoodName().toLowerCase().contains(searchKey.toLowerCase()))
    		matchs.add(food);
    	}
    	mealPlanFilterList.addAll(matchs);
    }
    @FXML
    void deleteMealPlan(ActionEvent event) {
        if(tblQuanLyMonDinhNau.getSelectionModel() == null) return;
        controller.deleteMeal(tblQuanLyMonDinhNau.getSelectionModel().getSelectedItem().getMealPlanId());
        loadMealPlan();
    }
    
    @FXML
    void initialize() {
    	controller = new MealPlanFoodController();
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
