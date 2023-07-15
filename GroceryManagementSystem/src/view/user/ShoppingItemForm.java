package view.user;

import java.util.Optional;

import controller.ShoppingListController;
import entity.RawFoodEntity;
import entity.ShoppingItemEntity;
import entity.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import view.BaseFormHandler;

public class ShoppingItemForm extends BaseFormHandler{

    @FXML
    private Button btnSelectFood;

    @FXML
    private TextField txtDonVi;

    @FXML
    private ComboBox<String> txtLoaiThucPham;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTenThucPham;
    
    private ShoppingListController controller;
    
    private RawFoodEntity food;

    @FXML
    void delete(ActionEvent event) {
    	controller.deleteItem();
    	this.exit(event);
    }

    @SuppressWarnings("unchecked")
	@FXML
    void selectFoodNeedAdd(ActionEvent event) {
		
		ChoiceDialog<String> addFood = new ChoiceDialog<>();
		//TextInputDialog addFood = new TextInputDialog();
		
		addFood.getItems().addAll(controller.getAvailableFoods());
		
		ComboBox<String> options = (ComboBox<String>) addFood.getDialogPane().lookup(".combo-box");
		
		options.setEditable(true);

		addFood.setHeaderText("Nhập vào thực phẩm cần thêm!");
		Optional<String> result = addFood.showAndWait();
		
		if (!result.isPresent()) {
			return;
		}

		String nameFood = result.get();

		food = controller.getFoodModelByName(nameFood);

		if (food == null) {
			infoAlert("Thực phẩm chưa có trong cơ sở dữ liệu.\n Xin hãy điền thông tin vào để thêm mới!");
			txtTenThucPham.setText(nameFood);
			txtTenThucPham.setEditable(true);
			txtDonVi.setEditable(true);
			return;
		}

		txtTenThucPham.setText(food.getRaw_food_name());
		txtLoaiThucPham.getSelectionModel().select(food.getFood_typeString());
		txtLoaiThucPham.setDisable(true);
		txtDonVi.setText(food.getUnit());
    }

    @FXML
    void submit(ActionEvent event) {
		if (txtTenThucPham.getText().isEmpty()) {
			errorAlert("Hãy chọn thực phẩm cần thêm!");
			return;
		}
		
		if (txtSoLuong.getText().isEmpty()) {
			errorAlert("Vui lòng nhập vào số lượng!");
			return;
		}
		
		if (txtTenThucPham.isEditable()) {
			
			if (txtLoaiThucPham.getValue().isEmpty()) {
				errorAlert("Vui lòng chọn loại thực phẩm!");
				return;
			}
			
			if (txtDonVi.getText().isEmpty()) {
				errorAlert("Vui lòng nhập vào đơn vị!");
				return;
			}
			adminController.updateFood(txtTenThucPham.getText(),
					RawFoodEntity.getFood_typeStringToID(txtLoaiThucPham.getValue()),
					txtDonVi.getText());
			food = controller.getFoodModelByName(txtTenThucPham.getText());
		}
		
		boolean result = controller.updateItem(UserSingleton.getInstance().getUser_id(), food.getRaw_food_id(),
				Integer.parseInt(txtSoLuong.getText()));

		if (!result) {
			errorAlert("Thực phẩm đã có sẵn!\nĐể sửa đổi, vui lòng chuột đúp vào thực phẩm.");
		}
		
		this.exit(event);

    }
    
    @FXML
    void initialize() {
    	controller = new ShoppingListController();
		for (String type : RawFoodEntity.food_type_enum) {
			txtLoaiThucPham.getItems().add(type);
		}
    }

	public void editMode(ShoppingItemEntity item) {
		controller.setItem(item);
		txtTenThucPham.setText(item.getRaw_food_name());
		txtLoaiThucPham.getSelectionModel().select(item.getFood_type());
		txtDonVi.setText(item.getUnit());
		txtSoLuong.setText("" + item.getNumber());
		btnSelectFood.setDisable(true);
		txtLoaiThucPham.setDisable(true);
	}

}
