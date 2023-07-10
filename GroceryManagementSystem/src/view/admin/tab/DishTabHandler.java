package view.admin.tab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

import entity.DishEntity;
import javafx.collections.FXCollections;
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
import service.SqliteConnection;
import view.admin.DishFormHandler;

public class DishTabHandler {

    @FXML
    private TableColumn<DishEntity, String> colDishID;

    @FXML
    private TableColumn<DishEntity, String> colDishName;

    @FXML
    private TableColumn<DishEntity, String> colRecipe;

    @FXML
    private TableView<DishEntity> tblQuanLyCongThucMonAn;

    @FXML
    private TextField txtTimKiemCongThucMonAn;
    
    private ObservableList<DishEntity> dataDishes;

    @FXML
    void addDish(ActionEvent event) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddDish = null;
    	try {
    		formAddDish = FXMLLoader.load(getClass().getResource("/fxml/AddDishModel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddDish);
    	stage.setScene(scene);
    	stage.showAndWait();
    	refreshData();
    }
    
    void editDish(DishEntity rowData) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddDish = null;
    	FXMLLoader loader = null;
    	try {
    		loader = new FXMLLoader(getClass().getResource("/fxml/AddDishModel.fxml"));
    		formAddDish = (Parent)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddDish);
    	DishFormHandler handler = loader.getController();
    	handler.editMode(rowData);
    	stage.setScene(scene);
    	stage.showAndWait();
    	refreshData();
    }

    @FXML
    void searchDish(ActionEvent event) {
    	// get filter value
    	String filterKey = txtTimKiemCongThucMonAn.getText();
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<DishEntity> containsKey = i -> i.getDish_name().toLowerCase().contains(filterKey.toLowerCase());
    	
    	// create filtered list
    	FilteredList<DishEntity> filteredDishList = dataDishes.filtered(containsKey);
    	
    	// load list to table
    	tblQuanLyCongThucMonAn.setItems(filteredDishList);

    }
    
    @FXML
    private void initialize() {
    	loadData();
    	
    	// important, set correct column to correct attribute
		colDishID.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("dish_id")
		);;
		colDishName.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("dish_name")
		);;
		colRecipe.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("recipe")
		);;
		
		// load values
    	tblQuanLyCongThucMonAn.setItems(dataDishes);
    	
    	// add cheeky editDish thing
    	tblQuanLyCongThucMonAn.setRowFactory(tv -> {
    		TableRow<DishEntity> row = new TableRow<>();
    		row.setOnMouseClicked(mouseEvent -> {
    			if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
    				DishEntity rowData = row.getItem();
    				editDish(rowData);
    			}
    		});
    		return row;
    	});
    }
    
    public void refreshData() {
    	loadData();
    	tblQuanLyCongThucMonAn.setItems(dataDishes);
    }

	private void loadData() {
    	
    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	Statement sttm = null;
		
		// execute query
		try {
	    	String query;
			sttm = conn.createStatement();
			ResultSet rs;
			
	    	query = "select * from dishes";
			
			// dataDishes is a class attribute
			dataDishes = FXCollections.observableArrayList();
			rs = sttm.executeQuery(query);
			
			while(rs.next()) {
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"),
						rs.getString("recipe"));
				dataDishes.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
