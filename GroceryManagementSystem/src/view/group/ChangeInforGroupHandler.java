package view.group;

import controller.GroupController;
import entity.GroupEntity;
import entity.UserEntity;
import entity.UserSingleton;
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

public class ChangeInforGroupHandler {

	@FXML
	private Button btnThemThanhVien;

	@FXML
	private Button btnXacNhan;

	@FXML
	private Button btnXoaThanhVien;

	@FXML
	private Button btnXoaNhom;
	
	@FXML
	private TableColumn<UserEntity, Integer> colIdThanhVien;

	@FXML
	private TableColumn<UserEntity, String> colTenThanhVien;

	@FXML
	private TableView<UserEntity> tblThanhVienNhom;

	@FXML
	private TextField txtTenNhom;


	private int groupIdCurrent;

	@FXML
	public void addMemberToGroup(ActionEvent event) {

		ObservableList<UserEntity> userList = tblThanhVienNhom.getItems();

		UserEntity userLogin = UserSingleton.getInstance();
		
		TextInputDialog findMember = new TextInputDialog();

		findMember.setHeaderText("Nhập vào tên người dùng");
		findMember.showAndWait();

		String nameMember = findMember.getEditor().getText();

		UserEntity user = GroupController.getUserByUsername(nameMember);
		
		
		if (user == null) {
			Alert a = new Alert(AlertType.WARNING, "Người dùng không tồn tại!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}
		
		if (contains(tblThanhVienNhom, user) || contains(userLogin, user)) {
			Alert a = new Alert(AlertType.WARNING, "Thành viên đã có trong nhóm!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}
		
		

		boolean addOk = GroupController.addGroupMember(groupIdCurrent, user.getUser_id());
	
		if (addOk == true) {
			Alert a = new Alert(AlertType.WARNING, "Thêm người dùng thành công!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			
			userList.add(user);
			colIdThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, Integer>("user_id"));
			colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
			tblThanhVienNhom.setItems(userList);
		}
		
	}

	@FXML
	public void deleteMember() {
		int userLogin = UserSingleton.getInstance().getUser_id();

		UserEntity user = tblThanhVienNhom.getSelectionModel().getSelectedItem();
		
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
		
		GroupController.deleteGroupMember(user.getUser_id(), groupIdCurrent);
		
		tblThanhVienNhom.getItems().remove(user);
	}

	

	@FXML
	public void submitFormGroup(ActionEvent event) {
		
     	close(event);
	}
	
	@FXML
	public void deleteGroup(ActionEvent event) {
		
		Alert delConfirm = new Alert(AlertType.CONFIRMATION, "Bạn có thực sự muốn xóa nhóm không?", ButtonType.YES, ButtonType.NO);
		delConfirm.setHeaderText(null);
		delConfirm.showAndWait();
		
		if (delConfirm.getResult() == ButtonType.YES) {
			Boolean deleteOk = GroupController.deleteGroup(groupIdCurrent);
			
			if (deleteOk == true) {
				Alert a = new Alert(AlertType.WARNING, "Xóa nhóm thành công!", ButtonType.OK);
				a.setHeaderText(null);
				a.showAndWait();
			}
		}
		
		
		close(event);
	}
	
	public void close(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage formChangeInfor = (Stage) node.getScene().getWindow();
		
		formChangeInfor.close();
	}
	
	public void setGroupData(GroupEntity group) {
		txtTenNhom.setText(group.getGroupName());
		colIdThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, Integer>("user_id"));
		colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
		
		tblThanhVienNhom.setItems(group.getMemberList());
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
	
	public void setGroupId(int groupId) {
		this.groupIdCurrent = groupId;
	}

}