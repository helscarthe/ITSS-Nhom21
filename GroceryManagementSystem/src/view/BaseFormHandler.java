package view;

import controller.AdminController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

public class BaseFormHandler extends BaseHandler{
    
    protected AdminController adminController;

    @FXML
	protected void exit(ActionEvent event) {
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	stage.close();
    }

}
