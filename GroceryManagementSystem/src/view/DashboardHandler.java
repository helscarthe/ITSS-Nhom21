package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import entity.UserSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DashboardHandler extends BaseHandler implements Initializable{

    @FXML
    private TableView<?> btlTuLanhList;

    @FXML
    private ComboBox<String> cbLoaiThucPhamTrongTuLanh;

    @FXML
    private Tab txtQuanLyCongThucMonAn;

    @FXML
    private Tab tabQuanLyDuLieuThucPham;

    @FXML
    private Tab tabQuanLyTaiKhoanNguoiDung;

    @FXML
    private TableView<?> tblDanhSachcanMua;
    
    @FXML
    private TableView<?> tdDoAnSapHetHan;
    
    @FXML
    private TextField txtTimKiemDocanMua;

    @FXML
    private TextField txtTimKiemMonDinhNau;

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

	public void checkAdmin(boolean isAdmin) {
		if (isAdmin) {
			return;
		}
		
		txtQuanLyCongThucMonAn.getTabPane().getTabs().remove(txtQuanLyCongThucMonAn);
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
		checkAdmin(UserSingleton.getInstance().isAdmin());
	}
}

