package view.group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.UserEntity;
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
import service.SqliteConnection;

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

	private ObservableList<UserEntity> data = FXCollections.observableArrayList();

	@FXML
	public void addMemberToGroup(ActionEvent event) throws SQLException {

		Connection conn = SqliteConnection.Connector();
		
		TextInputDialog findMember = new TextInputDialog();

		findMember.setHeaderText("Nhập vào tên người dùng");
		findMember.showAndWait();

		String nameMember = findMember.getEditor().getText();

//		System.out.println(nameMember);

		String query = "select user_id, username from users where username = " + "'" + nameMember + "'";

		Statement sttm = null;

		try {
			sttm = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		ResultSet rs = sttm.executeQuery(query);

			
		
		if (rs.getObject("user_id") == null || rs.wasNull()) {
			Alert a = new Alert(AlertType.WARNING, "Người dùng không tồn tại!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}

		Node node = (Node) event.getSource();
		Stage dashboardStage = (Stage) node.getScene().getWindow();
		UserEntity userLogin = (UserEntity) dashboardStage.getUserData();
		// TODO: SỬAAAAAA
//		while (rs.next()) {
//			UserEntity user = new UserEntity(Integer.parseInt(rs.getString("user_id")), rs.getString("username"));
//			
//			if (contains(tblThanhVienNhom, user) || contains(userLogin, user)) {
//				Alert a = new Alert(AlertType.WARNING, "Thành viên đã có trong nhóm!", ButtonType.OK);
//				a.setHeaderText(null);
//				a.showAndWait();
//				return;
//			}
//			data.add(user);
//		}

		colIdThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, Integer>("user_id"));
		colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
		tblThanhVienNhom.setItems(data);

		sttm.close();
	}

	@FXML
	public void deleteMember() throws SQLException {
		UserEntity user = tblThanhVienNhom.getSelectionModel().getSelectedItem();
			
		if (user == null) {
			Alert a = new Alert(AlertType.WARNING, "Vui lòng chọn thành viên muốn xóa!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			
			return;
		}
			
		Alert a = new Alert(AlertType.CONFIRMATION, "Bạn chắn chắc muốn xóa thành viên này?", ButtonType.YES);
		a.setHeaderText(null);
		a.showAndWait();
		
		tblThanhVienNhom.getItems().remove(user);
	}

	

	@FXML
	public void submitFormGroup(ActionEvent event) throws SQLException {
		Connection conn = SqliteConnection.Connector();
		
		Node node = (Node) event.getSource();
    	Stage dashboardStage = (Stage) node.getScene().getWindow();
    	UserEntity usernameLogin = (UserEntity) dashboardStage.getUserData();
    	int leaderId;
    	
    	try {
    		leaderId = usernameLogin.getUser_id();
    		
    		System.out.println("LeaderId: " + leaderId);
		} catch (NullPointerException e) {
			System.out.println("leaderId is Null!");
			return;
		}
    	
		String nameGroup = txtTenNhom.getText();
		String addGroupQuery = "insert into groups (leader_id, group_name) values (" +  String.valueOf(leaderId) + ", '" + nameGroup + "')";		
		
		Statement sttm;
		try {
			sttm = conn.createStatement();
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} 
		
		try {
			sttm.execute(addGroupQuery);

		} catch (Exception e) {
			e.printStackTrace();
			return;		
		}
		
    	String groupIdQuery = "select group_id from groups where group_name =" + "'" + nameGroup + "'";
		ResultSet grId = sttm.executeQuery(groupIdQuery);
    	int groupId = Integer.parseInt(grId.getString("group_id"));

    	sttm.execute("insert into group_member (group_id, user_id) values (" +  String.valueOf(groupId) + ", " + String.valueOf(leaderId) + ")");
    	
     	for (UserEntity user : tblThanhVienNhom.getItems()) {
     		System.out.println(1);
        	sttm.execute("insert into group_member (group_id, user_id) values (" +  String.valueOf(groupId) + ", " + String.valueOf(user.getUser_id()) + ")");
		}
     	
//     	conn.close();
     	
     	dashboardStage.close();
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

}
