package view.admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.AdminController;
import entity.DishEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import view.BaseFormHandler;

public class DishFormHandler extends BaseFormHandler implements Initializable{

    @FXML
    private TextField tfDishName;

    @FXML
    private TextArea tfRecipe;

    @FXML
    void submit(ActionEvent event) {
    	String dishname = tfDishName.getText();
    	String recipe = tfRecipe.getText();
    	adminController.updateDish(dishname, recipe);
    	this.exit(event);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminController = new AdminController();
	}
	
	public void editMode(DishEntity dish) {
		adminController.setDish(dish);
		tfDishName.setText(dish.getDish_name());
		tfRecipe.setText(dish.getRecipe());
	}

}
