package view;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import entity.DishEntity;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SqliteConnection;
import view.admin.FoodFormHandler;
import view.admin.UserFormHandler;

public class DashboardHandler extends BaseHandler implements Initializable{


    @FXML
    private TextArea btlConThucMonAnYeuThich;

    @FXML
    private TableView<?> btlTenMonAnYeuThich;

    @FXML
    private TableView<?> btlTuLanhList;

    @FXML
    private Button btnDangXuat;

    @FXML
    private Button btnTaoNhom;

    @FXML
    private Button btnThayDoiThongTinNhom;

    @FXML
    private Button btnThemCongThuc;

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
    private Button btnXoaCongThuc;

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
    private TableColumn<UserEntity, String> tbDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachcanMua;

    @FXML
    private TableView<RawFoodEntity> tblDulieuThucPham;

    @FXML
    private TableView<?> tblQuanLyMonDinhNau;

    @FXML
    private TableView<UserEntity> tblQuanLytaiKhoanNguoiDung;

    @FXML
    private TableView<DishEntity> tblQuanLyCongThucMonAn;

    @FXML
    private TableView<?> tblThanhVienNhom;

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
    private TextField txtTimKiemDocanMua;

    @FXML
    private TextField txtTimKiemMonDinhNau;

    @FXML
    private TextField txtTimKiemTaiKhoanNguoiDung;

    @FXML
    private TextField txtTimKiemThucPhamTrongTuLanh;

    @FXML
    private TextField txtTimKiemTenThucPham;
    
    @FXML
    private TableColumn<?, ?> favDishTable;
    
	ObservableList<UserEntity> dataUsers;
    
	ObservableList<DishEntity> dataDishes;
    
	ObservableList<RawFoodEntity> dataFood;
    
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
    
    @FXML
    public void clickTabGroup(Event event){
    	Connection conn = SqliteConnection.Connector();
    	String query = "select group_name from groups";
    	
    	Statement sttm = null;
		ObservableList<String> li = null;
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			
			li = FXCollections.observableArrayList();
			
			while(rs.next()) {
				li.add(rs.getString("group_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	cbChonNhom.setValue(li.get(0));
    	cbChonNhom.setItems(li);
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
    	
    	Node node = (Node) event.getSource();
    	Stage dashboardStage = (Stage) node.getScene().getWindow();
    	
    	Scene scene = new Scene(formCreateTeam);
    	createTeamStage.setScene(scene);
    	
    	createTeamStage.setUserData(dashboardStage.getUserData());
    	
    	createTeamStage.show();
    }
    
    @FXML
    public void changeInfoTeam() {
    	Stage stage = new Stage();
    	Parent formCreateTeam = null;
    	try {
			formCreateTeam = FXMLLoader.load(getClass().getResource("/fxml/GroupInfo.fxml"));
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}
    	
    	Scene scene = new Scene(formCreateTeam);
    	stage.setScene(scene);
    	stage.show();
    }
    
//    @FXML
//    void getDishes(Event event) {
//    	//Tùng
//    	//Thực hiện lấy dữ liệu từ bảng dishes (cơ sở dữ liệu)
//    	//rồi đổ vào bảng id=btlQuanLyCongThucMonAn khi nhấn vào tab công thức nấu ăn
//    	//Giao diện của bảng đang sai hay sao ấy (đáng ra chỉ cần 3 cột thứ tự, tên món, công thức).
//    	//Thứ tự không nên lấy theo id vì lỡ xóa 1 món đi thì sẽ bị mất 1 id.
//    }

//    @FXML
//    void addDish(ActionEvent event) {
//    	//Tùng
//    	//Thực hiện mở giao diện của màn thêm công thức nấu ăn 
//    }
    
//    @FXML
//    void searchDish(ActionEvent event) {
//    	//Tùng
//    	//Thực hiện khi nhân vào nút tìm kiếm trong tab "công thức nấu ăn"
//    	//Lấy dữ liệu nhập vào của người dùng để tìm các món ăn đã lấy trước đó bằng hàm loadDishes
//    	//Đổ dữ liệu vào trong bảng hiển thị.
//    }
//	  Tuấn implement rồi nhá!
//	  Ý Tuấn là Tùng implement favdish với plan dish á =)))
    
    
    @FXML
    void loadFavDish(Event event) {
    	//Tùng
    	//Thực hiện lấy dữ liệu đổ vào bảng khi nhấn vào tab công thức món ăn yêu thích
    	//Đổ tên các món ăn yêu thích vào tablecolumn có id là favDishTable.
    	//TextArea "công thức" ban đầu để trống, Khi nhấn vào 1 hàng trong tablecolum trên
    	//thì sẽ hiển thị công thức của món ăn đó.
    	
    }
    
    @FXML
    void addFavDish(ActionEvent event) {
    	//Tùng
    	//Thực hiện khi nhấn vào nút thêm công thức nấu ăn yêu thích.
    	//Mở giao diện của màn thêm công thức nấu ăn yêu thích.
    }

    @FXML
    void deleteFavDish(ActionEvent event) {
    	//Tùng
    	//Xóa món ăn đang được chọn( khi nhấn vào 1 dòng trong côt favDishTable thì 
    	//dòng ý sẽ  bị bôi xanh). Viết logic lấy user_id và dish_id của dòng thông tin đó
    	//rồi xóa ra khỏi bảng fav_dish (cơ sở dữ liệu)
    }
    
    @FXML
    void searchFavDish(ActionEvent event) {
    	//Tùng
    	//tìm kiếm món ăn trong các món ăn yêu thích, đổ thông tin vào bảng. Tương tự searchDish
    }
    
   
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
				new PropertyValueFactory<UserEntity, String>("is_admin")
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
    void selectFoodDataTab(Event event) {
    
    	// important, set correct column to correct attribute
		colFoodID.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("raw_food_id")
		);;
		colFoodName.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("raw_food_name")
		);;
		colFoodType.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("food_typeString")
		);;
		colUnit.setCellValueFactory(
				new PropertyValueFactory<RawFoodEntity, String>("unit")
		);;
		
		cbLoaiThucPham.getItems().clear();
		
		for (String type : RawFoodEntity.food_type_enum) {
			cbLoaiThucPham.getItems().add(type);
		}
		
		// load values
    	tblDulieuThucPham.setItems(dataFood);
    	
    	// add cheeky editFood thing
    	tblDulieuThucPham.setRowFactory(tv -> {
    		TableRow<RawFoodEntity> row = new TableRow<>();
    		row.setOnMouseClicked(mouseEvent -> {
    			if (mouseEvent.getClickCount() == 2 && !row.isEmpty()) {
    				RawFoodEntity rowData = row.getItem();
    				editFood(rowData);
    			}
    		});
    		return row;
    	});
    }
    
    @FXML
    void addFood(ActionEvent event) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddFood = null;
    	try {
    		formAddFood = FXMLLoader.load(getClass().getResource("/fxml/CRUDFoodModelInfo.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddFood);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadData();
    	tblDulieuThucPham.setItems(dataFood);
    }
    
    void editFood(RawFoodEntity rowData) {
    	// basic open new window (stage)
    	Stage stage = new Stage();
    	Parent formAddFood = null;
    	FXMLLoader loader = null;
    	try {
    		loader = new FXMLLoader(getClass().getResource("/fxml/CRUDFoodModelInfo.fxml"));
    		formAddFood = (Parent)loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddFood);
    	FoodFormHandler handler = loader.getController();
    	handler.editMode(rowData);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadData();
    	tblDulieuThucPham.setItems(dataFood);
    }

    @FXML
    void searchFood(ActionEvent event) {
    	// get filter value
    	int food_type = -1;
    	if (cbLoaiThucPham.getValue() != null) {
    		for (int i = 0; i < RawFoodEntity.food_type_enum.length; i++) {
    			if (cbLoaiThucPham.getValue().equals(RawFoodEntity.food_type_enum[i])) {
    				food_type = i;
    			}
    		}
    		if (food_type == -1) {
    			errorAlert("Invalid food type somehow? Report to devs! code:food_type_invalid");
    			return;
    		};
    	}
    	String filterKey = txtTimKiemTenThucPham.getText();
    	int food_type_final = food_type; // jank workaround please ignore
    	
    	// create predicate that says "Need this value"
    	// i is the object chosen
    	// Here, i is UserEntity
    	Predicate<RawFoodEntity> containsKey = i -> i.getRaw_food_name().contains(filterKey);
    	Predicate<RawFoodEntity> ofType = i -> i.getFood_type() == food_type_final;
    	Predicate<RawFoodEntity> filter = containsKey.and(ofType);
    	
    	// create filtered list
    	FilteredList<RawFoodEntity> filteredUserList = dataFood.filtered(filter);
    	
    	// load list to table
    	tblDulieuThucPham.setItems(filteredUserList);
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
    }

    @FXML
    void addDish(ActionEvent event) {

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
    	FilteredList<DishEntity> filteredUserList = dataDishes.filtered(containsKey);
    	
    	// load list to table
    	tblQuanLyCongThucMonAn.setItems(filteredUserList);

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
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("username"),
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
    

    
}

