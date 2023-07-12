package view.user;
import controller.DishController;
import entity.DishEntity;
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
    private DishController controllerFavFood;
    @FXML
    public void initialize() {
    	controllerFavFood = new DishController();
    	loadData();
    	cl_match_name.setCellValueFactory(
    			new PropertyValueFactory<DishEntity, String>("dish_name")
    	);
    	tb_match.setItems(dishMatchedList);
    	
    	
    }
    
    void loadData() {
    	dishList = controllerFavFood.loadDishList();
		dishMatchedList.addAll(dishList);
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
    	if(tb_match.getSelectionModel() == null) {
    		errorAlert("Vui lòng chọn món");
    	};
		int dishId = tb_match.getSelectionModel().getSelectedItem().getDish_id();
    	boolean status = controllerFavFood.addFavDish(dishId);
    	if(status == false) {
			errorAlert("Món đã có trong danh sách yêu thích");
    	}
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }
}
