package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import controller.FridgeFoodController;
import entity.FridgeEntity;
import entity.RawFoodEntity;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.admin.tab.DishTabHandler;
import view.admin.tab.FoodTabHandler;
import view.admin.tab.UserTabHandler;
import view.fridge.FridgeHandler;

public class DashboardHandler extends BaseHandler implements Initializable {

	@FXML
	private Button btnThemThucPhamTrongTulanh;

	@FXML
	private Button btnTimKiemTuLanh;

	@FXML
	private ComboBox<String> cbLoaiThucPhamTrongTuLanh;

	@FXML
	private TableColumn<FridgeEntity, String> colDonViTuLanh;

	@FXML
	private TableColumn<FridgeEntity, String> colLoaiThucPhamTuLanh;

	@FXML
	private TableColumn<FridgeEntity, String> colNgayHetHanTuLanh;

	@FXML
	private TableColumn<FridgeEntity, Integer> colSoLuongTuLanh;

	@FXML
	private TableColumn<FridgeEntity, String> colTenThucPhamTuLanh;

	@FXML
	private TableView<?> tblDanhSachcanMua;

	@FXML
	private TableView<FridgeEntity> tblThucPhamTrongTuLanh;

	@FXML
	private Tab tblTuLanh;

	@FXML
	private TableView<?> tdDoAnSapHetHan;

	@FXML
	private Tab adminTab;

	@FXML
	private TextField txtTimKiemDocanMua;

	@FXML
	private TextField txtTimKiemThucPhamTrongTuLanh;

	private FridgeFoodController controller;
	
	@FXML
	private DishTabHandler adminDishController;
	
	@FXML
	private FoodTabHandler adminFoodController;
	
	@FXML
	private UserTabHandler adminUserController;

	private ObservableList<FridgeEntity> data = FXCollections.observableArrayList();
	
	void loadDataFridge() {
		UserEntity userLogin = UserSingleton.getInstance();
		int userId = userLogin.getUser_id();

		data = controller.getFoodListByUserId(userId);

		colTenThucPhamTuLanh.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("raw_food_name"));
		colLoaiThucPhamTuLanh.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("food_typeString"));
		colNgayHetHanTuLanh.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("expiry_date"));
		colSoLuongTuLanh.setCellValueFactory(new PropertyValueFactory<FridgeEntity, Integer>("number"));
		colDonViTuLanh.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("unit"));

		tblThucPhamTrongTuLanh.setItems(data);
	}

	@FXML
	void searchFoodFridge(ActionEvent event) {
		// get filter value
    	int food_type = -1;
    	if (cbLoaiThucPhamTrongTuLanh.getValue() != null) {
    		for (int i = 0; i < FridgeEntity.food_type_enum.length; i++) {
    			if (cbLoaiThucPhamTrongTuLanh.getValue().equals(RawFoodEntity.food_type_enum[i])) {
    				food_type = i;
    			}
    		}
    	}
    	String filterKey = txtTimKiemThucPhamTrongTuLanh.getText();
    	int food_type_final = food_type; // jank workaround please ignore
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<FridgeEntity> containsKey = i -> i.getRaw_food_name().contains(filterKey);
    	Predicate<FridgeEntity> ofType = i -> i.getFood_type() == food_type_final;
    	Predicate<FridgeEntity> filter = containsKey.and(ofType);
		if (food_type == -1) {
			filter = containsKey;
		};
    	
    	// create filtered list
    	FilteredList<FridgeEntity> filteredFoodList = data.filtered(filter);
    	
    	// load list to table
    	tblThucPhamTrongTuLanh.setItems(filteredFoodList);
	}

	@FXML
	void selectTuLanhTab(Event event) {

		ObservableList<String> typeFood = FXCollections.observableArrayList();
		typeFood.add("Rau");
		typeFood.add("Thịt");
		typeFood.add("Hoa quả");

		loadDataFridge();

		cbLoaiThucPhamTrongTuLanh.setItems(typeFood);

		tblThucPhamTrongTuLanh.setRowFactory(tv -> {
			TableRow<FridgeEntity> row = new TableRow<FridgeEntity>() {
			};
			row.setOnMouseClicked(me -> {
				if (me.getClickCount() == 2 && !row.isEmpty()) {
					FridgeEntity food = row.getItem();
					editFoodInFridge(food);
				}
			});

			return row;
		});
	}

	@FXML
	public void addFoodIntoFridge(ActionEvent event) {
		Stage addFoodIntoFridge = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/CRUDFridgeFood.fxml"));
		Parent formAddFood = null;
		try {
			formAddFood = loader.load();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		FridgeHandler controller = loader.getController();
		controller.setChangeOrAdd(1);

		Scene scene = new Scene(formAddFood);
		addFoodIntoFridge.setScene(scene);
		addFoodIntoFridge.showAndWait();

		loadDataFridge();
	}

	void editFoodInFridge(FridgeEntity food) {
		Stage ChangeFoodInGroup = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/CRUDFridgeFood.fxml"));
		Parent formChangeFood = null;
		try {
			formChangeFood = loader.load();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		FridgeHandler controller = loader.getController();
		controller.setFoodData(food);
		controller.setChangeOrAdd(0);

		Scene scene = new Scene(formChangeFood);
		ChangeFoodInGroup.setScene(scene);
		ChangeFoodInGroup.showAndWait();

		loadDataFridge();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		controller = new FridgeFoodController();
		checkAdmin(UserSingleton.getInstance().isAdmin());
	}
}
