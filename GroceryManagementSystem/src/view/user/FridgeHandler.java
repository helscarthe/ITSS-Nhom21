package view.user;

import java.time.LocalDate;
import java.util.Optional;

import controller.AdminController;
import controller.FridgeFoodController;
import entity.FridgeEntity;
import entity.RawFoodEntity;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.BaseFormHandler;

public class FridgeHandler extends BaseFormHandler {
	@FXML
    private Button btnChon;

    @FXML
    private Button btnXacNhan;

    @FXML
    private Button btnXoa;

    @FXML
    private DatePicker dpNgayHetHan;

    @FXML
    private TextField txtDonVi;

    @FXML
    private ComboBox<String> txtLoaiThucPham;

    @FXML
    private TextField txtSoLuong;

    @FXML
    private TextField txtTenThucPham;
    
    private RawFoodEntity food;

	private int type;

	private FridgeFoodController controller;

	private UserEntity userLogin;
	
	public void close(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage formAddFood = (Stage) node.getScene().getWindow();

		formAddFood.close();
	}

	@FXML
	void delete(ActionEvent event) {
		
		boolean deleteOk = controller.deleteFood(((FridgeEntity)food).getFridge_food_id());

		if (deleteOk == false) {
			errorAlert("Thực phẩm không có!");
			return;
		}

		infoAlert("Xóa thực phẩm thành công!");
		close(event);
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
			txtLoaiThucPham.getItems().addAll(RawFoodEntity.food_type_enum);
			txtDonVi.setEditable(true);
			return;
		}

		txtTenThucPham.setText(food.getRaw_food_name());
		txtLoaiThucPham.getItems().add(food.getFood_typeString());
		txtLoaiThucPham.getSelectionModel().selectFirst();
		txtDonVi.setText(food.getUnit());
	}

	@FXML
	void submit(ActionEvent event) {
		if (type == 1) {
			if (txtTenThucPham.getText().isEmpty()) {
				errorAlert("Hãy chọn thực phẩm cần thêm!");
				return;
			}

			if (dpNgayHetHan.getValue() == null) {
				errorAlert("Vui lòng nhập vào ngày hết hạn!");
				return;
			}
			
			if (txtSoLuong.getText().isEmpty()) {
				errorAlert("Vui lòng nhập vào số lượng!");
				return;
			}
			
			if (txtTenThucPham.isEditable()) {
				adminController.updateFood(txtTenThucPham.getText(),
						RawFoodEntity.getFood_typeStringToID(txtLoaiThucPham.getValue()),
						txtDonVi.getText());
				food = controller.getFoodModelByName(txtTenThucPham.getText());
			}

			controller.insertFoodIntoFridge(userLogin.getUser_id(), 
										   food.getRaw_food_id(),
										   Integer.parseInt(txtSoLuong.getText()), 
										   dpNgayHetHan.getValue().toString());
			
		} else {
			controller.updateFood(food.getRaw_food_id(), userLogin.getUser_id(),
					Integer.parseInt(txtSoLuong.getText()));
		}

		close(event);
	}

	public void setFoodData(FridgeEntity foodCurrent) {
		this.food = foodCurrent;
		txtTenThucPham.setText(food.getRaw_food_name());
		txtLoaiThucPham.setPromptText(food.getFood_typeString());
		dpNgayHetHan.setValue(LocalDate.parse(((FridgeEntity)food).getExpiry_date()));
		txtDonVi.setText(food.getUnit());
		txtSoLuong.setText(String.valueOf(((FridgeEntity)food).getNumber()));
	}

	public void setChangeOrAdd(int type) {
		this.type = type;

		if (type == 1) {
			btnXoa.setDisable(true);
		} else {
			dpNgayHetHan.setDisable(true);
			btnChon.setDisable(true);
		}
	}

	@FXML
	private void initialize() {
		adminController = new AdminController();
		controller = new FridgeFoodController();

		userLogin = UserSingleton.getInstance();
		txtLoaiThucPham.getEditor().setEditable(false);
		
	}
}
