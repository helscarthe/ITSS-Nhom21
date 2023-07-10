package view.user.tab;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class PersonalFavoriteTabHandle {

    @FXML
    private TextField txtTimKiemCongThucMonAnYeuThi;

    @FXML
    private TableView<DishEntity> btlTenMonAnYeuThich;

    @FXML
    private TextArea btlConThucMonAnYeuThich;

    @FXML
    private Button btnCapNhatCongThuc;

    @FXML
    private TableColumn<DishEntity, String> favDishTable;

    @FXML
    private Button btnTimKiemMonAnYeuThich;

    @FXML
    private Button btn_xoamonanyeuthich;
	
	ObservableList<DishEntity> favDishList = FXCollections.observableArrayList();
	ObservableList<DishEntity> favDishFilterList = FXCollections.observableArrayList();
    void loadFavDish() {
    	Connection conn = SqliteConnection.Connector();
    	int userId = UserSingleton.getInstance().getUser_id();
    	String query = "select f.dish_id dish_id, d.dish_name dish_name, d.recipe recipe from fav_dish as f, dishes as d where f.dish_id = d.dish_id and f.user_id = "+userId+";";
    	
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			favDishList.clear();
			ObservableList<DishEntity> list = FXCollections.observableArrayList();
			while(rs.next()) {
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"), rs.getString("recipe"));
				list.add(dish);
			}
			favDishList.addAll(list);
			filterFavDish(txtTimKiemCongThucMonAnYeuThi.getText());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    void filterFavDish(String searchKey) {
    	favDishFilterList.clear();
    	ObservableList<DishEntity> matchs = FXCollections.observableArrayList();
    	for(DishEntity dish: favDishList) {
    		if(dish.getDish_name().toLowerCase().contains(searchKey.toLowerCase()))
    		matchs.add(dish);
    	}
    	favDishFilterList.addAll(matchs);
    }
    @FXML
    void showRecipeFavDish(MouseEvent event) {
    	btlTenMonAnYeuThich.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getClickCount() == 2) {
            		btlConThucMonAnYeuThich.setText(btlTenMonAnYeuThich.getSelectionModel().getSelectedItem().getRecipe());
            	}
            }
        });
    }
    @FXML
    void deleteFavDish(ActionEvent event) {
		int userId = UserSingleton.getInstance().getUser_id();
		if(btlTenMonAnYeuThich.getSelectionModel() == null) return;
		int dishId = btlTenMonAnYeuThich.getSelectionModel().getSelectedItem().getDish_id();
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
	    loadFavDish();
    }
    @FXML
    void updateFavDish(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formUpdateFavDish = null;
    	try {
    		formUpdateFavDish = FXMLLoader.load(getClass().getResource("/fxml/AddFavouriteDishForm.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formUpdateFavDish);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadFavDish();
    }
    
    @FXML
    void searchFavDish(KeyEvent event) {
    	filterFavDish(((TextField)(event.getSource())).getText());
    }
    
    @FXML
    void initialize() {
    	loadFavDish();		
    	favDishTable.setCellValueFactory(
    		new PropertyValueFactory<DishEntity, String>("dish_name")
    	);
    	btlTenMonAnYeuThich.setItems(favDishFilterList);
    }

}
