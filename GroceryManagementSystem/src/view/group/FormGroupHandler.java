package view.group;

import java.sql.SQLException;
import controller.GroupController;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FormGroupHandler {

	@FXML
	private Button btnThemThanhVien;

	@FXML
	private Button btnXacNhan;

	@FXML
	private Button btnXoaThanhVien;

	@FXML
	private TableColumn<UserEntity, Integer> colIdThanhVien;

	@FXML
	private TableColumn<UserEntity, String> colTenThanhVien;

	@FXML
	private TableView<UserEntity> tblThanhVienNhom;

	@FXML
	private TextField txtTenNhom;

	private GroupController groupController;


	@FXML
	public void addMemberToGroup(ActionEvent event) throws SQLException {
		
		ObservableList<UserEntity> data = FXCollections.observableArrayList();

		TextInputDialog findMember = new TextInputDialog();

		findMember.setHeaderText("Nhập vào tên người dùng");
		findMember.showAndWait();

		String nameMember = findMember.getEditor().getText();

		UserEntity user = groupController.getUserByUsername(nameMember);
		
		if (user == null) {
			Alert a = new Alert(AlertType.WARNING, "Người dùng không tồn tại!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}

		UserEntity userLogin = UserSingleton.getInstance();
		
		
		if (contains(tblThanhVienNhom, user) || contains(userLogin, user)) {
			Alert a = new Alert(AlertType.WARNING, "Thành viên đã có trong nhóm!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}
		
		data.add(user);
		colIdThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, Integer>("user_id"));
		colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
		tblThanhVienNhom.setItems(data);

	}

	@FXML
	public void deleteMember() throws SQLException {
		UserEntity user = tblThanhVienNhom.getSelectionModel().getSelectedItem();
		int userLogin = UserSingleton.getInstance().getUser_id();
		
		if (user == null) {
			Alert a = new Alert(AlertType.WARNING, "Vui lòng chọn thành viên muốn xóa!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			
			return;
		}
		
		if (user.getUser_id() == userLogin) {
			Alert a = new Alert(AlertType.CONFIRMATION, "Đây là trưởng nhóm bạn không thể xóa!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			
			return;
		}
		
		tblThanhVienNhom.getItems().remove(user);
	}

	

	@FXML
	public void submitFormGroup(ActionEvent event) throws SQLException {
		
		UserEntity userLogin = UserSingleton.getInstance();
		
    	int leaderId = userLogin.getUser_id();
    	
		String nameGroup = txtTenNhom.getText();
		
		if (nameGroup.isEmpty()) {
			Alert a = new Alert(AlertType.WARNING, "Vui lòng điền tên nhóm!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}
		
		int createOk = groupController.createGroupMember(nameGroup, tblThanhVienNhom.getItems(), leaderId);
     	
		if (createOk == 0) {
			Alert a = new Alert(AlertType.WARNING, "Tên nhóm đã tồn tại!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}
		
     	Node node = (Node) event.getSource();
		Stage formGroup = (Stage) node.getScene().getWindow();
		
		formGroup.close();
	}
	
	public static boolean contains(TableView<UserEntity> table, UserEntity obj){
        for(UserEntity item: table.getItems())
            if (item.getUsername().equals(obj.getUsername()))
                return true;

        return false;
    }
	
	public static boolean contains(UserEntity userLogin, UserEntity obj){
        if (userLogin.getUsername().equals( obj.getUsername() ) )
                return true;

        return false;
    }
	
	@FXML
	private void initialize() {
		groupController = new GroupController();
	}

}