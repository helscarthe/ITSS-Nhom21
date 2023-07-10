package view;

import java.io.IOException;

import entity.UserSingleton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.admin.tab.DishTabHandler;
import view.admin.tab.FoodTabHandler;
import view.admin.tab.UserTabHandler;
import view.user.tab.ExpiringTabHandler;

public class DashboardHandler extends BaseHandler{

	@FXML
	private Button btnThemThucPhamTrongTulanh;

	@FXML
	private Button btnTimKiemTuLanh;

	@FXML
	private TableView<?> tblDanhSachcanMua;

	@FXML
	private Tab tblTuLanh;

	@FXML
	private Tab adminTab;

	@FXML
	private TextField txtTimKiemDocanMua;
	
	@FXML
	private ExpiringTabHandler expiringFoodController;
	
	@FXML
	private DishTabHandler adminDishController;
	
	@FXML
	private FoodTabHandler adminFoodController;
	
	@FXML
	private UserTabHandler adminUserController;

	@FXML
	void loadExpiringFoodTab(Event event) {
		expiringFoodController.refreshData();
	}

	@FXML
	void loadAdminFoodTab(Event event) {
		adminFoodController.refreshData();
	}

	@FXML
	void loadAdminDishTab(Event event) {
		adminDishController.refreshData();
	}

	@FXML
	void loadAdminUserTab(Event event) {
		adminUserController.refreshData();
	}

	public void checkAdmin(boolean isAdmin) {
		if (isAdmin) {
			return;
		}

		adminTab.getTabPane().getTabs().remove(adminTab);
	}

	@FXML
	void logOut(ActionEvent event) {
		// basic open new window (stage)
		Parent login = null;
		try {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
			login = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Stage stage = new Stage();
		Scene scene = new Scene(login);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initialize() {
		checkAdmin(UserSingleton.getInstance().isAdmin());
	}
}
