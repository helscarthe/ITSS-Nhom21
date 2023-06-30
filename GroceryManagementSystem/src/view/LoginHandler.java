package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.SqliteConnection;

public class LoginHandler {

    @FXML
    private Button btnDangNhap;

    @FXML
    private TextField txtTenDangNhap;

    @FXML
    private Hyperlink hyperlinkQuenMatKhau;

    @FXML
    private TextField txtMatKhau;

    private Connection conn = SqliteConnection.Connector();
    @FXML
    void submit(ActionEvent event) throws SQLException {
    	String pass = txtMatKhau.getText().toString();
    	String user = txtTenDangNhap.getText();
    	
    	String query = "select * from users where username=" + "'" + user + "'" + "and password_hash=" + "'" + pass + "'";
    	Statement sttm = null;

		try {
			sttm = conn.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
    	
    	ResultSet rs = sttm.executeQuery(query);
    	
    	if (rs.getObject("user_id") == null || rs.wasNull()) {
			Alert a = new Alert(AlertType.WARNING, "Tài khoản hoặc mật khẩu không chính xác!", ButtonType.OK);
			a.setHeaderText(null);
			a.showAndWait();
			return;
		}
		
//    	if(!pass.equals("2042002") || !user.equals("minh")) {
//    		Alert a = new Alert(AlertType.WARNING, "Đồ ngu", ButtonType.OK);
//    		a.setHeaderText(null);
//    		a.showAndWait();
//    		return;
//    	}
    	
    	
    	Parent dashboard = null;
		try {
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	dashboard = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
			stage.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		Stage stage = new Stage();
		Scene scene = new Scene(dashboard);
		
		
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		
		scene.setUserData(new UserEntity(
			     Integer.parseInt(rs.getString("user_id")),
			     rs.getString("username"),
			     rs.getString("password_hash"),
			     Boolean.parseBoolean(rs.getString("is_admin"))
			     ));
		
		stage.show();
    }

}

