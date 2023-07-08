package view.group;

import controller.GroupController;
import entity.FoodEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FoodNeedBuyHandler {

	@FXML
	private Button btnChon;

	@FXML
	private Button btnXacNhan;

	@FXML
	private Button btnXoa;

	@FXML
	private TextField txtDonVi;

	@FXML
	private TextField txtLoaiThucPham;

	@FXML
	private TextField txtSoLuong;

	@FXML
	private TextField txtTenThucPham;

	private FoodEntity food;

	private int groupIdCurrent;
	
	private int type;

	public void close(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage formAddFood = (Stage) node.getScene().getWindow();
		
		formAddFood.close();
	}
	
	@FXML
	void delete(ActionEvent event) {
		boolean deleteOk = GroupController.deleteFoodInGroup(food.getRaw_food_id(), groupIdCurrent);
		
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
	void selectFoodNeedBuy(ActionEvent event) {
		TextInputDialog addFood = new TextInputDialog();

		addFood.setHeaderText("Nhập vào thực phẩm cần tìm !");
		addFood.showAndWait();

		String nameFood = addFood.getEditor().getText();

		food = GroupController.getFoodByName(nameFood);

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
			
			if (txtSoLuong.getText().isEmpty()) {
				Alert a = new Alert(AlertType.WARNING, "Vui lòng nhập vào số lượng!", ButtonType.OK);
				a.setHeaderText(null);
				a.showAndWait();
				return;
			}

			Boolean insertOk = GroupController.insertFoodIntoGroup(food.getRaw_food_id(), groupIdCurrent,
					Integer.parseInt(txtSoLuong.getText()));
			
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
			GroupController.updateFoodInGroup(food.getRaw_food_id(), groupIdCurrent, Integer.parseInt(txtSoLuong.getText()));
		} 
		
		
		close(event);
	}

	
	public void setGroupId(int groupId) {
		this.groupIdCurrent = groupId;
	}
	
	public void setFoodData(FoodEntity foodCurrent) {
		this.food = foodCurrent;
		txtTenThucPham.setText(food.getRaw_food_name());
		txtLoaiThucPham.setText(food.getFood_typeString());
		txtDonVi.setText(food.getUnit());
		txtSoLuong.setText(String.valueOf(food.getNumber()));
	}
	
	public void setChangeOrAdd(int type) {
		this.type = type;
		
		if (type == 1) {
			btnXoa.setDisable(true);
		} 
	}

}
