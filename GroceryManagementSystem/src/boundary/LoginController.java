package boundary;

import java.io.IOException;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
    private TextField password;

    @FXML
    private Button submit;

    @FXML
    private TextField username;

    @FXML
    void submit(ActionEvent event) {
    	String pass = password.getText().toString();
    	String user = username.getText();
    	
    	if(!pass.equals("admin") || !user.equals("admin")) {
    		Alert a = new Alert(AlertType.WARNING, "nunu baby", ButtonType.OK);
    		a.setHeaderText(null);
    		a.showAndWait();
    		return;
    	}
    	
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Parent login = null;
		try {
			login = FXMLLoader.load(getClass().getResource("/boundary/dashboard.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		Scene scene = new Scene(login);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
    }

}

