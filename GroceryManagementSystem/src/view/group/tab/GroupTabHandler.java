package view.group.tab;

import java.io.IOException;

import controller.AdminController;
import controller.GroupController;
import entity.FoodEntity;
import entity.GroupEntity;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import view.group.ChangeInforGroupHandler;
import view.group.FoodNeedBuyHandler;

public class GroupTabHandler {

    @FXML
    private Button btnThayDoiThongTinNhom;

    @FXML
    private Button btnThemThucPhamTrongNhom;

    @FXML
    private ComboBox<String> cbChonNhom;

	@FXML
	private TableColumn<FoodEntity, String> colDonVi;

	@FXML
	private TableColumn<FoodEntity, String> colLoaiThucPham;

	@FXML
	private TableColumn<FoodEntity, Integer> colSoLuongInGroup;

	@FXML
	private TableColumn<UserEntity, String> colTenThanhVien;

    @FXML
	private TableColumn<FoodEntity, String> colTenThucPham;

	@FXML
	private TableColumn<UserEntity, String> colVaiTro;

    @FXML
    private TableView<FoodEntity> tblDanhSachThucPham;

    @FXML
    private TableView<UserEntity> tblThanhVienNhom;
    
	private int groupIdCurrent = 0;
    
	private GroupController groupController;

    @FXML
    void addFoodNeedBuyGroup(ActionEvent event) {
		Stage addFoodIntoGroup = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/CRUDGroupShoppingList.fxml"));
		Parent formAddFood = null;
		try {
			formAddFood = loader.load();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		FoodNeedBuyHandler controller = loader.getController();
		controller.setGroupId(groupIdCurrent);
		controller.setChangeOrAdd(1);
		
		Scene scene = new Scene(formAddFood);
		addFoodIntoGroup.setScene(scene);
		addFoodIntoGroup.showAndWait();
		
		loadDataGroupComboBox();

    }
    
	@FXML
	public void changeInfoTeam() {

		String groupName = cbChonNhom.getSelectionModel().getSelectedItem();

		groupIdCurrent = groupController.getGroupIdByNameGroup(groupName);

		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/ChangeInforGroup.fxml"));
		Parent formChangeInforGroup = null;

		try {
			formChangeInforGroup = loader.load();

		} catch (Exception e) {

		}

		GroupEntity groupSelected = groupController.getGroupOfUserByGroupId(groupIdCurrent);

		ChangeInforGroupHandler controller = loader.getController();

		controller.setGroupData(groupSelected);
		controller.setGroupId(groupIdCurrent);

		Scene scene = new Scene(formChangeInforGroup);
		stage.setScene(scene);
		stage.showAndWait();

		loadGroupComboBox();
		loadDataGroupComboBox();
	}
	
	@FXML
	public void createTeam(ActionEvent event) {

		Stage createTeamStage = new Stage();
		Parent formCreateTeam = null;
		try {
			formCreateTeam = FXMLLoader.load(getClass().getResource("/fxml/GroupInfo.fxml"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		Scene scene = new Scene(formCreateTeam);
		createTeamStage.setScene(scene);
		createTeamStage.showAndWait();
		
		loadGroupComboBox();
		loadDataGroupComboBox();
	}
    
	@FXML
	public void selectGroupComboBox(ActionEvent event) {

		loadDataGroupComboBox();
		
		tblDanhSachThucPham.setRowFactory(tv -> {
			TableRow<FoodEntity> row = new TableRow<FoodEntity>() {};
			row.setOnMouseClicked(me -> {
				if(me.getClickCount() == 2 && !row.isEmpty()) {
					FoodEntity food = row.getItem();
					editFoodInGroup(food);
				}
			});
			
			return row;
		});
	}
	
	void editFoodInGroup(FoodEntity food) {
		Stage ChangeFoodInGroup = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/CRUDGroupShoppingList.fxml"));
		Parent formChangeFood = null;
		try {
			formChangeFood = loader.load();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		FoodNeedBuyHandler controller = loader.getController();
		controller.setFoodData(food);
		controller.setGroupId(groupIdCurrent);
		controller.setChangeOrAdd(0);

		Scene scene = new Scene(formChangeFood);
		ChangeFoodInGroup.setScene(scene);
		ChangeFoodInGroup.showAndWait();
		
		loadDataGroupComboBox();
	}
	
	@FXML
	private void initialize() {
		groupController = new GroupController();
		loadGroupComboBox();
	}
    
	public void loadDataGroupComboBox() {
		
		String groupSelected = cbChonNhom.getSelectionModel().getSelectedItem();

		UserEntity userLogin = UserSingleton.getInstance();

		groupIdCurrent = groupController.getGroupIdByNameGroup(groupSelected);

		GroupEntity group = groupController.getGroupOfUserByGroupId(groupIdCurrent);

		if (group.getLeaderId() == userLogin.getUser_id()) {
			btnThayDoiThongTinNhom.setDisable(false);
			btnThemThucPhamTrongNhom.setDisable(false);
		} else {
			btnThayDoiThongTinNhom.setDisable(true);
			btnThemThucPhamTrongNhom.setDisable(true);
		}

		ObservableList<FoodEntity> foodList = groupController.getFoodInGroup(group.getGroupName());

		colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
		colVaiTro.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("role"));
		tblThanhVienNhom.setItems(group.getMemberList());

		colTenThucPham.setCellValueFactory(new PropertyValueFactory<FoodEntity, String>("raw_food_name"));
		colLoaiThucPham.setCellValueFactory(new PropertyValueFactory<FoodEntity, String>("food_typeString"));
		colSoLuongInGroup.setCellValueFactory(new PropertyValueFactory<FoodEntity, Integer>("number"));
		colDonVi.setCellValueFactory(new PropertyValueFactory<FoodEntity, String>("unit"));
		tblDanhSachThucPham.setItems(foodList);
	}
	    
	public void loadGroupComboBox() {
		ObservableList<String> li = groupController.getGroupNameOfUser();
		try {
			cbChonNhom.setValue(li.get(0));
			cbChonNhom.setItems(li);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
