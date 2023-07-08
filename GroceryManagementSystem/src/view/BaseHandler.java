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
    
    protected void infoAlert(String info) {
		Alert a = new Alert(AlertType.INFORMATION, info, ButtonType.OK);
		a.setHeaderText(null);
		a.showAndWait();
    }

}
