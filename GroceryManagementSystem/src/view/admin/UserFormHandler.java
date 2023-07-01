package view.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserFormHandler {

    @FXML
    private CheckBox cbIsAdmin;

    @FXML
    private TextField tfPassword;

    @FXML
    private TextField tfUsername;

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
    }

}
