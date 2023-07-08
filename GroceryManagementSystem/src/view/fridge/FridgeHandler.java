package view.fridge;

import java.time.LocalDate;

import controller.FridgeFoodController;
import entity.FridgeEntity;
import entity.RawFoodEntity;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FridgeHandler {
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
    private TextField txtLoaiThucPham;

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
		
		int userId = userLogin.getUser_id();

		boolean deleteOk = controller.deleteFood(food.getRaw_food_id(), userId);

		if (deleteOk == false) {
			Alert a = new Alert(AlertType.WARNING, "Thực phẩm không có!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}

		Alert a = new Alert(AlertType.INFORMATION, "Xóa thực phẩm thành công!", ButtonType.OK);
		a.setHeaderText(null);
		a.showAndWait();
		close(event);
	}

	@FXML
	void selectFoodNeedAdd(ActionEvent event) {
		TextInputDialog addFood = new TextInputDialog();

		addFood.setHeaderText("Nhập vào thực phẩm cần thêm!");
		addFood.showAndWait();

		String nameFood = addFood.getEditor().getText();

		food = controller.getFoodModelByName(nameFood);

		if (food == null) {
			Alert a = new Alert(AlertType.WARNING, "Thực phẩm không có!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}

		txtTenThucPham.setText(food.getRaw_food_name());
		txtLoaiThucPham.setText(food.getFood_typeString());
		txtDonVi.setText(food.getUnit());
	}

	@FXML
	void submit(ActionEvent event) {
		if (type == 1) {
			if (txtTenThucPham.getText().isEmpty()) {
				Alert a = new Alert(AlertType.WARNING, "Hãy chọn thực phẩm cần thêm!", ButtonType.OK);
				a.setHeaderText(null);
				a.showAndWait();
				return;
			}

			if (dpNgayHetHan.getValue() == null) {
				Alert a = new Alert(AlertType.WARNING, "Vui lòng nhập vào ngày hết hạn!", ButtonType.OK);
				a.setHeaderText(null);
				a.showAndWait();
				return;
			}
			
			if (txtSoLuong.getText().isEmpty()) {
				Alert a = new Alert(AlertType.WARNING, "Vui lòng nhập vào số lượng!", ButtonType.OK);
				a.setHeaderText(null);
				a.showAndWait();
				return;
			}

			Boolean insertOk = controller.insertFoodIntoFridge(userLogin.getUser_id(), 
															   food.getRaw_food_id(),
															   Integer.parseInt(txtSoLuong.getText()), 
															   dpNgayHetHan.getValue().toString());

			if (insertOk == false) {
				Alert a = new Alert(AlertType.WARNING, "Thực phẩm đã có!", ButtonType.OK);
				a.setHeaderText(null);
				a.showAndWait();

				txtTenThucPham.setText("");
				txtLoaiThucPham.setText("");
				txtDonVi.setText("");

				return;
			}
			
		} else {
			controller.updateFood(food.getRaw_food_id(), userLogin.getUser_id(),
					Integer.parseInt(txtSoLuong.getText()));
		}

		close(event);
	}

	public void setFoodData(FridgeEntity foodCurrent) {
		this.food = foodCurrent;
		txtTenThucPham.setText(food.getRaw_food_name());
		txtLoaiThucPham.setText(food.getFood_typeString());
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
		}
	}

	@FXML
	private void initialize() {
		controller = new FridgeFoodController();

		userLogin = UserSingleton.getInstance();
		
	}
}
