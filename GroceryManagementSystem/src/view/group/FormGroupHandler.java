package view.group;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormGroupHandler {

	@FXML
	private Button btnThemThanhVien;

	@FXML
	private Button btnXacNhan;

	@FXML
	private Button btnXoaThanhVien;

	@FXML
	private TableColumn<?, ?> colIdThanhVien;

	@FXML
	private TableColumn<?, ?> colTenThanhVien;

	@FXML
	private TableView<?> tblThanhVienNhom;

	@FXML
	private TextField txtTenNhom;

	@FXML
	private TextField txtTruongNhom;

	public void addMemberToGroup() {

		Stage stage = new Stage();
		Parent formAddMember = null;
		try {
			formAddMember = FXMLLoader.load(getClass().getResource("/fxml/AddMemberToGroup.fxml"));

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(formAddMember);
		stage.setScene(scene);
		stage.show();
	}

}
