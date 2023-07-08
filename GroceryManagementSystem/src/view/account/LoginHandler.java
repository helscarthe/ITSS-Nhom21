package view.account;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import controller.AccountController;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.SqliteConnection;
import view.BaseHandler;

public class LoginHandler extends BaseHandler{

    @FXML
    private Button btnDangNhap;

    @FXML
    private TextField txtTenDangNhap;

    @FXML
    private Hyperlink hyperlinkQuenMatKhau;

    @FXML
    private TextField txtMatKhau;

    private Connection conn = SqliteConnection.Connector();

    private AccountController accountController;

    @FXML
    void submit(ActionEvent event) throws SQLException {
    	String pass = txtMatKhau.getText();
    	String username = txtTenDangNhap.getText();
    	
    	if (username.isEmpty()) {
    		errorAlert("Chưa nhập tên đăng nhập!");
			return;
    	}
    	
    	if (pass.isEmpty()) {
    		errorAlert("Chưa nhập mật khẩu!");
			return;
    	}
    	
    	UserEntity user = accountController.logIn(username, pass);
    	
    	if (user == null) {
    		errorAlert("Tài khoản hoặc mật khẩu không chính xác!");
			return;
		}
    	
		UserSingleton.setInstance(user);
    	
    	Parent dashboard = null;
		try {
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	dashboard = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
			stage.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		Stage stage = new Stage();
		Scene scene = new Scene(dashboard);

		stage.setScene(scene);

		stage.show();
		conn.close();
    }

    @FXML
    void forgotPassword(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formForgot = null;
    	try {
    		formForgot = FXMLLoader.load(getClass().getResource("/fxml/ForgotPassword.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formForgot);
    	stage.setScene(scene);
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	stage.show();
    }

    @FXML
    void signUp(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formSignup = null;
    	try {
    		formSignup = FXMLLoader.load(getClass().getResource("/fxml/Signup.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formSignup);
    	stage.setScene(scene);
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	stage.show();

    }

    @FXML
    private void initialize() {
    	accountController = new AccountController();
    }

}

