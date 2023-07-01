package view.admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.AdminController;
import entity.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import view.BaseFormHandler;

public class UserFormHandler extends BaseFormHandler implements Initializable{

    @FXML
    private CheckBox cbIsAdmin;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

    @FXML
    void submit(ActionEvent event) {
    	String username = tfUsername.getText();
    	String password_hash = tfPassword.getText();
    	int isAdmin = cbIsAdmin.isSelected() ? 1 : 0;
    	adminController.updateUser(username, password_hash, isAdmin);
    	this.exit(event);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminController = new AdminController();
	}
	
	public void editMode(UserEntity user) {
		adminController.setUser(user);
		tfUsername.setText(user.getUsername());
		tfPassword.setText(user.getPassword_hash());
		if (user.isIs_admin()) {
			cbIsAdmin.setSelected(true);
		}
	}

}
