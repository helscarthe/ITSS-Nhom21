package view.account;

import java.io.IOException;

import controller.AccountController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.BaseHandler;

public class SignupHandler extends BaseHandler {

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfPasswordRepeat;

    @FXML
    private TextField tfUsername;

    private AccountController accountController;

    @FXML
    void submit(ActionEvent event) {
    	String pass = tfPassword.getText();
    	String passRep = tfPasswordRepeat.getText();
    	String username = tfUsername.getText();
    	
    	if (username.isEmpty()) {
    		errorAlert("Chưa nhập tên đăng nhập!");
			return;
    	}
    	
    	if (pass.isEmpty()) {
    		errorAlert("Chưa nhập mật khẩu!");
			return;
    	}
    	
    	if (passRep.isEmpty()) {
    		errorAlert("Chưa nhập mật khẩu lần 2!");
			return;
    	}
    	
    	if (accountController.signUp(username, pass) == -1) {
    		errorAlert("Tên người dùng đã tồn tại!");
    		return;
    	}
    	
    	if (!pass.equals(passRep)) {
    		errorAlert("Mật khẩu nhập lại chưa khớp!");
			return;
    	}
    	
    	infoAlert("Tạo tài khoản thành công!\nChuyển hướng đến cửa sổ đăng nhập...");
    	
    	logIn(event);
    }

    @FXML
    void logIn(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formLogin = null;
    	try {
    		formLogin = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formLogin);
    	stage.setScene(scene);
    	((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    	stage.show();
    }

    @FXML
    private void initialize() {
    	accountController = new AccountController();
    }

}
