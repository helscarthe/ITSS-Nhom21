package view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private TableColumn<?, ?> tbDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachcanMua;

    @FXML
    private TableView<?> tblDulieuThucPham;

    @FXML
    private TableView<?> tblQuanLyMonDinhNau;

    @FXML
    private TableView<?> tblQuanLytaiKhoanNguoiDung;

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
    public void clickTabGroup (ActionEvent event) throws SQLException {
    	System.out.println(1);
    	Connection connection = SqliteConnection.Connector();
    	String query = "select group_name from groups";
    	
    	Statement sttm = null;
    	
    	try {
        	sttm = connection.createStatement();

		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	    ResultSet rs = sttm.executeQuery(query);
    	
    	ObservableList<String> li = FXCollections.observableArrayList();
    	
    	while(rs.next()) {
    		li.add(rs.getString("group_name"));
    	}
    	cbChonNhom.setValue(li.get(0));
    	cbChonNhom.setItems(li);
    	
    }
    
    @FXML
    public void createTeam(ActionEvent event) {
    	
    	Stage stage = new Stage();
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
    	stage.setScene(scene);
    	
    	stage.setUserData(dashboardStage.getUserData());
    	
    	stage.show();
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
