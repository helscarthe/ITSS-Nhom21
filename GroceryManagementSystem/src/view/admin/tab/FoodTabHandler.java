package view.admin.tab;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

import entity.RawFoodEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SqliteConnection;
import view.BaseHandler;
import view.admin.FoodFormHandler;

public class FoodTabHandler extends BaseHandler{

    @FXML
    private ComboBox<String> cbLoaiThucPham;

    @FXML
    private TableColumn<RawFoodEntity, String> colFoodID;

    @FXML
    private TableColumn<RawFoodEntity, String> colFoodName;

    @FXML
    private TableColumn<RawFoodEntity, String> colFoodType;

    @FXML
    private TableColumn<RawFoodEntity, String> colUnit;

    @FXML
    private TableView<RawFoodEntity> tblDulieuThucPham;

    @FXML
    private TextField txtTimKiemTenThucPham;
    
    private ObservableList<RawFoodEntity> dataFood;
    
    @FXML
    void addFood(ActionEvent event) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddFood = null;
    	try {
    		formAddFood = FXMLLoader.load(getClass().getResource("/fxml/CRUDFoodModelInfo.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddFood);
    	stage.setScene(scene);
    	stage.showAndWait();
    	refreshData();
    }
    
    void editFood(RawFoodEntity rowData) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddFood = null;
    	FXMLLoader loader = null;
    	try {
    		loader = new FXMLLoader(getClass().getResource("/fxml/CRUDFoodModelInfo.fxml"));
    		formAddFood = (Parent)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddFood);
    	FoodFormHandler handler = loader.getController();
    	handler.editMode(rowData);
    	stage.setScene(scene);
    	stage.showAndWait();
    	refreshData();
    }

	@FXML
    void searchFood(ActionEvent event) {
    	// get filter value
    	int food_type = -1;
    	if (cbLoaiThucPham.getValue() != null) {
    		for (int i = 0; i < RawFoodEntity.food_type_enum.length; i++) {
    			if (cbLoaiThucPham.getValue().equals(RawFoodEntity.food_type_enum[i])) {
    				food_type = i;
    			}
    		}
    	}
    	String filterKey = txtTimKiemTenThucPham.getText();
    	int food_type_final = food_type; // jank workaround please ignore
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<RawFoodEntity> containsKey = i -> i.getRaw_food_name().contains(filterKey);
    	Predicate<RawFoodEntity> ofType = i -> i.getFood_type() == food_type_final;
    	Predicate<RawFoodEntity> filter = containsKey.and(ofType);
		if (food_type == -1) {
			filter = containsKey;
		};
    	
    	// create filtered list
    	FilteredList<RawFoodEntity> filteredFoodList = dataFood.filtered(filter);
    	
    	// load list to table
    	tblDulieuThucPham.setItems(filteredFoodList);
    }

    @FXML
	public void initialize() {
    	loadData();
    
    	// important, set correct column to correct attribute
		colFoodID.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("raw_food_id")
		);;
		colFoodName.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("raw_food_name")
		);;
		colFoodType.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("food_typeString")
		);;
		colUnit.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("unit")
		);;
		
		cbLoaiThucPham.getItems().clear();
		
		for (String type : RawFoodEntity.food_type_enum) {
			cbLoaiThucPham.getItems().add(type);
		}
		
		// load values
    	tblDulieuThucPham.setItems(dataFood);
    	
    	// add cheeky editFood thing
    	tblDulieuThucPham.setRowFactory(tv -> {
    		TableRow<RawFoodEntity> row = new TableRow<>();
    		row.setOnMouseClicked(mouseEvent -> {
    			if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
    				RawFoodEntity rowData = row.getItem();
    				editFood(rowData);
    			}
    		});
    		return row;
    	});
	}
    
    public void refreshData() {
    	loadData();
    	tblDulieuThucPham.setItems(dataFood);
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
	    	query = "select * from raw_foods";
			
			// dataFood is a class attribute
			dataFood = FXCollections.observableArrayList();
			rs = sttm.executeQuery(query);
			
			while(rs.next()) {
				RawFoodEntity dish = new RawFoodEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"),
						rs.getInt("food_type"),rs.getString("unit"));
				dataFood.add(dish);
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