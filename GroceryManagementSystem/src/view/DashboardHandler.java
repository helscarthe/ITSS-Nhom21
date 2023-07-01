package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Predicate;

import entity.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SqliteConnection;

public class DashboardHandler {


    @FXML
    private TextArea btlConThucMonAnYeuThich;

    @FXML
    private TableView<?> btlQuanLyCongThucMonAn;

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
    private Button btnThemThucPham;

    @FXML
    private Button btnThemThucPhamTrongNhom;

    @FXML
    private Button btnThemThucPhamTrongTuLanh;

    @FXML
    private Button btnThongTincaNhan;

    @FXML
    private Button btnTimKiem;

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
    private ComboBox<?> cbLoaiThucPham;

    @FXML
    private ComboBox<?> cbLoaiThucPhamTrongTuLanh;

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
    private TableColumn<UserEntity, String> tbDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachcanMua;

    @FXML
    private TableView<?> tblDulieuThucPham;

    @FXML
    private TableView<?> tblQuanLyMonDinhNau;

    @FXML
    private TableView<UserEntity> tblQuanLytaiKhoanNguoiDung;

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
    private TableColumn<?, ?> favDishTable;
    
	ObservableList<UserEntity> dataUsers;
    
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
    	Connection conn = SqliteConnection.Connector();
    	String query = "select * from users";
    	
    	Statement sttm = null;
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
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			
			dataUsers = FXCollections.observableArrayList();
			
			while(rs.next()) {
				UserEntity user = new UserEntity(rs.getInt("user_id"), rs.getString("username"),
						rs.getString("password_hash"), rs.getBoolean("is_admin"));
				dataUsers.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	tblQuanLytaiKhoanNguoiDung.setItems(dataUsers);
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

    }

    @FXML
    void addUser(ActionEvent event) {
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
    	stage.show();

    }

    @FXML
    void searchUser(ActionEvent event) {
    	String filterKey = txtTimKiemTaiKhoanNguoiDung.getText();
    	
    	Predicate<UserEntity> containsKey = i -> i.getUsername().contains(filterKey);
    	
    	FilteredList<UserEntity> filteredUserList = dataUsers.filtered(containsKey);
    	
    	tblQuanLytaiKhoanNguoiDung.setItems(filteredUserList);
    }

    @FXML
    void selectFoodDataTab(Event event) {

    }
    
    @FXML
    void addFood(ActionEvent event) {

    }

    @FXML
    void searchFood(ActionEvent event) {

    }
    
    @FXML
    void selectDishesTab(Event event) {
    	
    }

    @FXML
    void addDish(ActionEvent event) {

    }

    @FXML
    void searchDish(ActionEvent event) {

    }
    

    
}
