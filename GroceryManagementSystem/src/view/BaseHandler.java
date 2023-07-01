package view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class BaseHandler {
    
    protected void errorAlert(String err) {
		Alert a = new Alert(AlertType.WARNING, err, ButtonType.OK);
		a.setHeaderText(null);
		a.showAndWait();
    }

}
