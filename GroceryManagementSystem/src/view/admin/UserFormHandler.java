package view.admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.AdminController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserFormHandler implements Initializable{

    @FXML
    private CheckBox cbIsAdmin;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;
    
    private AdminController adminController;

    @FXML
    void exit(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

    @FXML
    void submit(ActionEvent event) {
    	String username = tfUsername.getText();
    	String password_hash = tfPassword.getText();
    	int isAdmin = cbIsAdmin.isSelected() ? 1 : 0;
    	adminController.addUser(username, password_hash, isAdmin);
    	this.exit(event);
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adminController = new AdminController();
	}

}
