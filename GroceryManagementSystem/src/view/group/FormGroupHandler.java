package view.group;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
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

	private Connection conn = SqliteConnection.Connector();
	private ObservableList<UserEntity> data = FXCollections.observableArrayList();

	@FXML
	public void addMemberToGroup() throws SQLException {

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

		while (rs.next()) {
			data.add(new UserEntity(Integer.parseInt(rs.getString("user_id")), rs.getString("username")));
		}

		colIdThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, Integer>("user_id"));
		colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
		tblThanhVienNhom.setItems(data);

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
	public void submitFormGroup() {
		
	}

}
