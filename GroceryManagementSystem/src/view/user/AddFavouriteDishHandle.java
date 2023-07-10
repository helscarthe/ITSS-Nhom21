package view.user;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.DishEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.SqliteConnection;
import view.BaseHandler;
public class AddFavouriteDishHandle extends BaseHandler{
	  @FXML
	    private TextField tf_search;

	    @FXML
	    private TableView<DishEntity> tb_match;

	    @FXML
	    private Button btn_submit;

	    @FXML
	    private TextArea tf_recipe;

	    @FXML
	    private TableColumn<DishEntity, String> cl_match_name;
    ObservableList<DishEntity> dishList = FXCollections.observableArrayList();
    ObservableList<DishEntity> dishMatchedList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
    	loadData();
    	cl_match_name.setCellValueFactory(
    			new PropertyValueFactory<DishEntity, String>("dish_name")
    	);
    	tb_match.setItems(dishMatchedList);
    	
    	
    }
    
    void loadData() {
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
			dishMatchedList.addAll(dishList);
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
    
    @FXML
    void search(KeyEvent event) {
    	String input = ((TextField)(event.getSource())).getText();
    	ObservableList<DishEntity> matched = FXCollections.observableArrayList();
    	dishMatchedList.clear();
    	for(DishEntity dish: dishList) {
    		if(dish.getDish_name().contains(input)) {
	    		matched.add(dish);
    		}
    	}
    	dishMatchedList.addAll(matched);
    }
    @FXML
    void showRecipe(MouseEvent event) {
    	tb_match.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	tf_recipe.setText(tb_match.getSelectionModel().getSelectedItem().getRecipe());
            }
        });
    }
    @FXML
    void submit(ActionEvent event) {
    	int userId = UserSingleton.getInstance().getUser_id();
    	if(tb_match.getSelectionModel() == null) {
    		errorAlert("Vui lòng chọn món");
    	};
		int dishId = tb_match.getSelectionModel().getSelectedItem().getDish_id();
    	String values = "("+dishId+","+userId+")";
    	Connection conn = SqliteConnection.Connector();
    	String query = "insert into fav_dish(dish_id, user_id) values"+values+";";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			sttm.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Looi");
			errorAlert("Món đã có trong danh sách yêu thích");
			return;
		}
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
}
