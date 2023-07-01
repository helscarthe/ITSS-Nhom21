package view.admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.AdminController;
import entity.RawFoodEntity;
import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import view.BaseFormHandler;

public class FoodFormHandler extends BaseFormHandler implements Initializable{

    @FXML
    private ComboBox<String> cbFoodType;

    @FXML
    private TextField tfFoodName;

    @FXML
    private TextField tfUnit;

    @FXML
    void submit(ActionEvent event) {
    	String raw_food_name = tfFoodName.getText();
    	int food_type = -1;
    	if (cbFoodType.getValue() == null) {
			errorAlert("Hãy chọn loại thực phẩm!");
			return;
    	}
		for (int i = 0; i < RawFoodEntity.food_type_enum.length; i++) {
			if (cbFoodType.getValue().equals(RawFoodEntity.food_type_enum[i])) {
				food_type = i;
			}
		}
		if (food_type == -1) {
			errorAlert("Invalid food type somehow? Report to devs! code:food_type_invalid");
			return;
		};
    	String unit = tfUnit.getText();
    	adminController.updateFood(raw_food_name, food_type, unit);
    	this.exit(event);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminController = new AdminController();
		for (String type : RawFoodEntity.food_type_enum) {
			cbFoodType.getItems().add(type);
		}
	}
	
	public void editMode(RawFoodEntity food) {
		adminController.setFood(food);
		tfFoodName.setText(food.getRaw_food_name());
		cbFoodType.getSelectionModel().select(food.getFood_type());
		tfUnit.setText(food.getUnit());
	}

}