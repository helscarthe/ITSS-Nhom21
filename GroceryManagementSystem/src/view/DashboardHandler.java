package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import controller.GroupController;
import javafx.collections.transformation.FilteredList;

import entity.DishEntity;
import entity.FoodEntity;
import entity.GroupEntity;
import entity.MealPlanFood;
import entity.RawFoodEntity;
import entity.UserEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.SqliteConnection;
import view.admin.DishFormHandler;
import view.admin.FoodFormHandler;
import view.admin.UserFormHandler;
import view.admin.FoodFormHandler;
import view.admin.UserFormHandler;
import view.group.ChangeInforGroupHandler;
import view.group.FoodNeedBuyHandler;

public class DashboardHandler extends BaseHandler implements Initializable{


    @FXML
    private TextArea btlConThucMonAnYeuThich;

    @FXML
    private TableView<?> btlTuLanhList;

    @FXML
    private Button btnDangXuat;

    @FXML
    private Button btnTaoNhom;

    @FXML
    private Button btnThayDoiThongTinNhom;

    @FXML
    private Button btnCapNhatCongThuc;

    @FXML
    private Button btnThemDoCanMua;

    @FXML
    private Button btnThemMonDinhNau;

    @FXML
    private Button btnThemNguoiDung;

    @FXML
    private Button btnThemThucPhamTrongNhom;

    @FXML
    private Button btnThemThucPhamTrongTuLanh;

    @FXML
    private Button btnThongTincaNhan;

    @FXML
    private Button btnTimKiemCongThucMonAn;

    @FXML
    private Button btnTimKiemMonAnYeuThich;

    @FXML
    private Button btnTimKiemMonDinhNau;

    @FXML
    private Button btnTimKiemTaiKhoanNguoiDung;
    

    @FXML
    private Button btn_xoamonanyeuthich;

    @FXML
    private ComboBox<String> cbChonNhom;

    @FXML
    private ComboBox<String> cbLoaiThucPham;

    @FXML
    private ComboBox<String> cbLoaiThucPhamTrongTuLanh;

    @FXML
    private Tab taDoAnSapHetHan;

    @FXML
    private Tab tabCongThucMonAnYeuThich;

    @FXML
    private Tab tabNhom;

    @FXML
    private Tab tabQuanLyDanhSachCanMua;

    @FXML
    private Tab tabQuanLyDuLieuThucPham;

    @FXML
    private Tab tabQuanLyMonDinhNau;

    @FXML
    private Tab tabQuanLyTaiKhoanNguoiDung;

    @FXML
    private Tab tabTuLanh;

    @FXML
    private TableColumn<UserEntity, String> colIsAdmin;

    @FXML
    private TableColumn<UserEntity, String> colPasswordHash;

    @FXML
    private TableColumn<UserEntity, String> colUserID;

    @FXML
    private TableColumn<UserEntity, String> colUsername;

    @FXML
    private TableColumn<DishEntity, String> colDishID;

    @FXML
    private TableColumn<DishEntity, String> colDishName;

    @FXML
    private TableColumn<DishEntity, String> colRecipe;

    @FXML
    private TableColumn<RawFoodEntity, String> colFoodID;

    @FXML
    private TableColumn<RawFoodEntity, String> colFoodName;

    @FXML
    private TableColumn<RawFoodEntity, String> colFoodType;

    @FXML
    private TableColumn<RawFoodEntity, String> colUnit;

    @FXML
	private TableColumn<FoodEntity, String> colTenThucPham;

	@FXML
	private TableColumn<FoodEntity, String> colLoaiThucPham;

	@FXML
	private TableColumn<FoodEntity, Integer> colSoLuongInGroup;

	@FXML
	private TableColumn<FoodEntity, String> colDonVi;
    
    @FXML
    private TableColumn<UserEntity, String> tbDanhSachThucPham;

    @FXML
    private TableView<FoodEntity> tblDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachcanMua;

    @FXML
    private TableView<RawFoodEntity> tblDulieuThucPham;

    @FXML
    private TableView<UserEntity> tblQuanLytaiKhoanNguoiDung;

    @FXML
    private TableView<DishEntity> tblQuanLyCongThucMonAn;

    @FXML
    private TableView<UserEntity> tblThanhVienNhom;

	@FXML
	private TableColumn<UserEntity, String> colTenThanhVien;

	@FXML
	private TableColumn<UserEntity, String> colVaiTro;
    
    @FXML
    private TableView<?> tdDoAnSapHetHan;

    @FXML
    private Tab txtQuanLyCongThucMonAn;

    @FXML
    private TextField txtTimKiemCongThucMonAn;

    @FXML
    private TextField txtTimKiemCongThucMonAnYeuThi;

    @FXML
    private Button txtTimKiemDoCanMua;

    @FXML
    private Button btnXoaMon;
    
    @FXML
    private TextField txtTimKiemDocanMua;

    @FXML
    private TextField txtTimKiemMonDinhNau;

    @FXML
    private TextField txtTimKiemTaiKhoanNguoiDung;

    @FXML
    private TextField txtTimKiemThucPhamTrongTuLanh;

    @FXML
    private TextField txtTimKiemTenThucPham;
    
    
    


    
	ObservableList<UserEntity> dataUsers;
    
	ObservableList<DishEntity> dataDishes;
    
	ObservableList<RawFoodEntity> dataFood;
    

    
	private int groupIdCurrent = 0;
	@FXML
    public void addFoodIntoFridge(ActionEvent event) {
    	Stage stage = new Stage();
    	
    	Parent formAddFoodIntoFridge = null;
    	try {
			formAddFoodIntoFridge = FXMLLoader.load(getClass().getResource("/fxml/CRUDFood.fxml"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
    	
    	Scene scene = new Scene(formAddFoodIntoFridge);
    	stage.setScene(scene);
    	stage.show();
    }

    // Tab Nhóm
    
	public void loadGroupComboBox() {
		ObservableList<String> li = GroupController.getGroupNameOfUser();
		try {
			cbChonNhom.setValue(li.get(0));
			cbChonNhom.setItems(li);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@FXML
	public void selectGroupTab(Event event) {
		
		loadGroupComboBox();
		
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
	public void changeInfoTeam() {

		String groupName = cbChonNhom.getSelectionModel().getSelectedItem();

		groupIdCurrent = GroupController.getGroupIdByNameGroup(groupName);

		Stage stage = new Stage();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/ChangeInforGroup.fxml"));
		Parent formChangeInforGroup = null;

		try {
			formChangeInforGroup = loader.load();

		} catch (Exception e) {

		}

		GroupEntity groupSelected = GroupController.getGroupOfUserByGroupId(groupIdCurrent);

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
	public void addFoodNeedBuyGroup(ActionEvent event) {
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
	
	/// End Tab Nhóm
    @FXML
    void selectAccountTab(Event event) {
    
    	// important, set correct column to correct attribute
		colUserID.setCellValueFactory(
				new PropertyValueFactory<UserEntity, String>("user_id")
		);;
		colUsername.setCellValueFactory(
				new PropertyValueFactory<UserEntity, String>("username")
		);;
		colPasswordHash.setCellValueFactory(
				new PropertyValueFactory<UserEntity, String>("password_hash")
		);;
		colIsAdmin.setCellValueFactory(
				new PropertyValueFactory<UserEntity, String>("admin")
		);;
		
		// load values
    	tblQuanLytaiKhoanNguoiDung.setItems(dataUsers);
    	
    	// add cheeky editUser thing
    	tblQuanLytaiKhoanNguoiDung.setRowFactory(tv -> {
    		TableRow<UserEntity> row = new TableRow<>();
    		row.setOnMouseClicked(mouseEvent -> {
    			if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
    				UserEntity rowData = row.getItem();
    				editUser(rowData);
    			}
    		});
    		return row;
    	});
    }

    @FXML
    void addUser(ActionEvent event) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddUser = null;
    	try {
    		formAddUser = FXMLLoader.load(getClass().getResource("/fxml/UserInfoChangeForm.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddUser);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadData();
    	tblQuanLytaiKhoanNguoiDung.setItems(dataUsers);
    }

    void editUser(UserEntity rowData) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formEditUser = null;
    	FXMLLoader loader = null;
    	try {
    		loader = new FXMLLoader(getClass().getResource("/fxml/UserInfoChangeForm.fxml"));
    		formEditUser = (Parent)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formEditUser);
    	UserFormHandler handler = loader.getController();
    	handler.editMode(rowData);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadData();
    	tblQuanLytaiKhoanNguoiDung.setItems(dataUsers);
    }

    @FXML
    void searchUser(ActionEvent event) {
    	// get filter value
    	String filterKey = txtTimKiemTaiKhoanNguoiDung.getText();
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<UserEntity> containsKey = i -> i.getUsername().contains(filterKey);
    	
    	// create filtered list
    	FilteredList<UserEntity> filteredUserList = dataUsers.filtered(containsKey);
    	
    	// load list to table
    	tblQuanLytaiKhoanNguoiDung.setItems(filteredUserList);
    }
    
    @FXML
    void selectDishesTab(Event event) {
    
    	// important, set correct column to correct attribute
		colDishID.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("dish_id")
		);;
		colDishName.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("dish_name")
		);;
		colRecipe.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("recipe")
		);;
		
		// load values
    	tblQuanLyCongThucMonAn.setItems(dataDishes);
    	
    	// add cheeky editDish thing
    	tblQuanLyCongThucMonAn.setRowFactory(tv -> {
    		TableRow<DishEntity> row = new TableRow<>();
    		row.setOnMouseClicked(mouseEvent -> {
    			if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
    				DishEntity rowData = row.getItem();
    				editDish(rowData);
    			}
    		});
    		return row;
    	});
    }

    @FXML
    void addDish(ActionEvent event) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddDish = null;
    	try {
    		formAddDish = FXMLLoader.load(getClass().getResource("/fxml/AddDishModel.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddDish);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadData();
    	tblQuanLyCongThucMonAn.setItems(dataDishes);
    }
    
    void editDish(DishEntity rowData) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddDish = null;
    	FXMLLoader loader = null;
    	try {
    		loader = new FXMLLoader(getClass().getResource("/fxml/AddDishModel.fxml"));
    		formAddDish = (Parent)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddDish);
    	DishFormHandler handler = loader.getController();
    	handler.editMode(rowData);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadData();
    	tblQuanLyCongThucMonAn.setItems(dataDishes);
    }

    @FXML
    void searchDish(ActionEvent event) {
    	// get filter value
    	String filterKey = txtTimKiemCongThucMonAn.getText();
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<DishEntity> containsKey = i -> i.getDish_name().contains(filterKey);
    	
    	// create filtered list
    	FilteredList<DishEntity> filteredDishList = dataDishes.filtered(containsKey);
    	
    	// load list to table
    	tblQuanLyCongThucMonAn.setItems(filteredDishList);

    }

	public void checkAdmin(boolean isAdmin) {
		if (isAdmin) {
			return;
		}
		
		tabQuanLyDanhSachCanMua.getTabPane().getTabs().remove(tabQuanLyDanhSachCanMua);
		tabQuanLyDuLieuThucPham.getTabPane().getTabs().remove(tabQuanLyDuLieuThucPham);
		tabQuanLyTaiKhoanNguoiDung.getTabPane().getTabs().remove(tabQuanLyTaiKhoanNguoiDung);
	}

    @FXML
    void logOut(ActionEvent event) {
    	// basic open new window (stage)
    	Parent login = null;
    	try {
        	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
		loadData();		
		checkAdmin(UserSingleton.getInstance().isAdmin());
	}
	
	private void loadData() {
    	
    	// create db connection, query, and statement
    	Connection conn = SqliteConnection.Connector();
    	Statement sttm = null;
		
		// execute query
		try {
	    	String query = "select * from users";
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			
			// dataUsers is a class attribute
			dataUsers = FXCollections.observableArrayList();
			
			while(rs.next()) {
				UserEntity user = new UserEntity(rs.getInt("user_id"), rs.getString("username"),
						rs.getString("password_hash"), rs.getBoolean("is_admin"));
				dataUsers.add(user);
			}
			
	    	query = "select * from dishes";
			
			// dataDishes is a class attribute
			dataDishes = FXCollections.observableArrayList();
			rs = sttm.executeQuery(query);
			
			while(rs.next()) {
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"),
						rs.getString("recipe"));
				dataDishes.add(dish);
			}
			
	    	query = "select * from raw_foods";
			
			// dataFood is a class attribute
			dataFood = FXCollections.observableArrayList();
			rs = sttm.executeQuery(query);
			
			while(rs.next()) {
				RawFoodEntity dish = new RawFoodEntity(rs.getInt("raw_food_id"), rs.getString("raw_food_name"),
						rs.getInt("food_type"),rs.getString("unit"));
				dataFood.add(dish);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadDataGroupComboBox() {
		
		String groupSelected = cbChonNhom.getSelectionModel().getSelectedItem();

		UserEntity userLogin = UserSingleton.getInstance();

		groupIdCurrent = GroupController.getGroupIdByNameGroup(groupSelected);

		GroupEntity group = GroupController.getGroupOfUserByGroupId(groupIdCurrent);

		if (group.getLeaderId() == userLogin.getUser_id()) {
			btnThayDoiThongTinNhom.setDisable(false);
			btnThemThucPhamTrongNhom.setDisable(false);
		} else {
			btnThayDoiThongTinNhom.setDisable(true);
			btnThemThucPhamTrongNhom.setDisable(true);
		}

		ObservableList<FoodEntity> foodList = GroupController.getFoodInGroup(group.getGroupName());

		colTenThanhVien.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("username"));
		colVaiTro.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("role"));
		tblThanhVienNhom.setItems(group.getMemberList());

		colTenThucPham.setCellValueFactory(new PropertyValueFactory<FoodEntity, String>("raw_food_name"));
		colLoaiThucPham.setCellValueFactory(new PropertyValueFactory<FoodEntity, String>("food_typeString"));
		colSoLuongInGroup.setCellValueFactory(new PropertyValueFactory<FoodEntity, Integer>("number"));
		colDonVi.setCellValueFactory(new PropertyValueFactory<FoodEntity, String>("unit"));
		tblDanhSachThucPham.setItems(foodList);
	}
}

