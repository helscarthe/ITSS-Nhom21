package view.user.tab;

import java.io.IOException;

import controller.DishController;
import entity.DishEntity;
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
    private DishController controllerFavFood;
	
	ObservableList<DishEntity> favDishList = FXCollections.observableArrayList();
	ObservableList<DishEntity> favDishFilterList = FXCollections.observableArrayList();
    void loadFavDish() {
    	favDishList = controllerFavFood.loadFavDishList();
		filterFavDish(txtTimKiemCongThucMonAnYeuThi.getText());
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
		if(btlTenMonAnYeuThich.getSelectionModel() == null) return;
		int dishId = btlTenMonAnYeuThich.getSelectionModel().getSelectedItem().getDish_id();
		controllerFavFood.removeFavDish(dishId);
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
    	controllerFavFood = new DishController();
    	loadFavDish();		
    	favDishTable.setCellValueFactory(
    		new PropertyValueFactory<DishEntity, String>("dish_name")
    	);
    	btlTenMonAnYeuThich.setItems(favDishFilterList);
    }

}
