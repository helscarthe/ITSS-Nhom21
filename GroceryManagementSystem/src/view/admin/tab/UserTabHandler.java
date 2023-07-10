package view.admin.tab;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.SqliteConnection;
import view.admin.UserFormHandler;

public class UserTabHandler {

    @FXML
    private TableColumn<UserEntity, String> colIsAdmin;

    @FXML
    private TableColumn<UserEntity, String> colPasswordHash;

    @FXML
    private TableColumn<UserEntity, String> colUserID;

    @FXML
    private TableColumn<UserEntity, String> colUsername;

    @FXML
    private TableView<UserEntity> tblQuanLytaiKhoanNguoiDung;

    @FXML
    private TextField txtTimKiemTaiKhoanNguoiDung;
    
    private ObservableList<UserEntity> dataUsers;

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
    	refreshData();
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
    	refreshData();
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
    private void initialize() {
    	loadData();
        
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
    
    public void refreshData() {
    	loadData();
    	tblQuanLytaiKhoanNguoiDung.setItems(dataUsers);
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
