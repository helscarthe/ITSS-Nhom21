package view;

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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginHandler {

    @FXML
    private Button btnDangNhap;

    @FXML
    private TextField txtTenDangNhap;

    @FXML
    private Hyperlink hyperlinkQuenMatKhau;

    @FXML
    private TextField txtMatKhau;

    @FXML
    void submit(ActionEvent event) {
    	String pass = txtMatKhau.getText().toString();
    	String user = txtTenDangNhap.getText();
    	
    	if(!pass.equals("admin") || !user.equals("admin")) {
    		Alert a = new Alert(AlertType.WARNING, "co cai concac", ButtonType.OK);
    		a.setHeaderText(null);
    		a.showAndWait();
    		return;
    	}
    	
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Parent login = null;
		try {
			login = FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml"));
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

