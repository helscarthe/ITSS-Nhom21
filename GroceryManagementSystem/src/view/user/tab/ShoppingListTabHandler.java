package view.user.tab;

import java.io.IOException;
import java.util.function.Predicate;

import controller.ShoppingListController;
import entity.ShoppingItemEntity;
import entity.UserSingleton;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.user.ShoppingItemForm;

public class ShoppingListTabHandler {

    @FXML
    private TableColumn<ShoppingItemEntity, Integer> colFoodAmount;

    @FXML
    private TableColumn<ShoppingItemEntity, String> colFoodName;

    @FXML
    private TableColumn<ShoppingItemEntity, String> colFoodType;

    @FXML
    private TableColumn<ShoppingItemEntity, String> colFoodUnit;

    @FXML
    private TableView<ShoppingItemEntity> tblDanhSachcanMua;

    @FXML
    private TextField txtTimKiemDocanMua;
    
    private ObservableList<ShoppingItemEntity> dataFood;
    
    private ShoppingListController controller;

    @FXML
    void addFoodToShoppingList(ActionEvent event) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddFood = null;
    	try {
    		formAddFood = FXMLLoader.load(getClass().getResource("/fxml/CRUDShoppingList.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddFood);
    	stage.setScene(scene);
    	stage.showAndWait();
    	refreshData();
    }

    private void editFood(ShoppingItemEntity rowData) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddFood = null;
    	FXMLLoader loader = null;
    	try {
    		loader = new FXMLLoader(getClass().getResource("/fxml/CRUDShoppingList.fxml"));
    		formAddFood = (Parent)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddFood);
    	ShoppingItemForm handler = loader.getController();
    	handler.editMode(rowData);
    	stage.setScene(scene);
    	stage.showAndWait();
    	refreshData();
	}

	@FXML
    void searchFoodInShoppingList(ActionEvent event) {
    	// get filter value
    	String filterKey = txtTimKiemDocanMua.getText();
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<ShoppingItemEntity> containsKey = i -> i.getRaw_food_name().toLowerCase().contains(filterKey.toLowerCase());
    	
    	// create filtered list
    	FilteredList<ShoppingItemEntity> filteredDishList = dataFood.filtered(containsKey);
    	
    	// load list to table
    	tblDanhSachcanMua.setItems(filteredDishList);

    }
    
    @FXML
    void initialize() {
    	controller = new ShoppingListController();
    	loadData();
    
    	// important, set correct column to correct attribute
    	colFoodName.setCellValueFactory(
				new PropertyValueFactory<ShoppingItemEntity, String>("raw_food_name")
		);;
		colFoodType.setCellValueFactory(
				new PropertyValueFactory<ShoppingItemEntity, String>("food_typeString")
		);;
		colFoodUnit.setCellValueFactory(
				new PropertyValueFactory<ShoppingItemEntity, String>("unit")
		);;
		colFoodAmount.setCellValueFactory(
				new PropertyValueFactory<ShoppingItemEntity, Integer>("number")
		);;
		
		// load values
		tblDanhSachcanMua.setItems(dataFood);
    	
    	// add cheeky editFood thing
		tblDanhSachcanMua.setRowFactory(tv -> {
    		TableRow<ShoppingItemEntity> row = new TableRow<>();
    		row.setOnMouseClicked(mouseEvent -> {
    			if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
    				ShoppingItemEntity rowData = row.getItem();
    				editFood(rowData);
    			}
    		});
    		return row;
    	});
    }
    
    public void refreshData() {
    	loadData();
    	tblDanhSachcanMua.setItems(dataFood);
    }

    private void loadData() {
    	dataFood = controller.loadShoppingList(UserSingleton.getInstance().getUser_id());
	}

}
