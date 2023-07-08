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
    private TableView<DishEntity> btlTenMonAnYeuThich;

    @FXML
    private TableView<?> btlTuLanhList;

    @FXML
    private Button btnCapNhatCongThuc;

    @FXML
    private Button btnThemDoCanMua;

    @FXML
    private Button btnThemMonDinhNau;

    @FXML
    private Button btnThemThucPhamTrongTuLanh;

    @FXML
    private Button btnThongTincaNhan;

    @FXML
    private Button btnTimKiemMonAnYeuThich;

    @FXML
    private Button btnTimKiemMonDinhNau;

    @FXML
    private Button btn_xoamonanyeuthich;

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
    private TableColumn<UserEntity, String> tbDanhSachThucPham;

    @FXML
    private TableView<?> tblDanhSachcanMua;

    @FXML
    private TableView<MealPlanFood> tblQuanLyMonDinhNau;
    
    @FXML
    private TableView<?> tdDoAnSapHetHan;

    @FXML
    private Tab txtQuanLyCongThucMonAn;

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
    private TextField txtTimKiemThucPhamTrongTuLanh;
    
    @FXML
    private TableColumn<DishEntity, String> favDishTable;
    
    @FXML
    private TableColumn<MealPlanFood, String> colMealPlanFood;
    
    @FXML
    private TableColumn<MealPlanFood, String> colMealDate;
    
	ObservableList<MealPlanFood> mealPlanList = FXCollections.observableArrayList();
	ObservableList<MealPlanFood> mealPlanFilterList = FXCollections.observableArrayList();
	
	ObservableList<DishEntity> favDishList = FXCollections.observableArrayList();
	ObservableList<DishEntity> favDishFilterList = FXCollections.observableArrayList();
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
	
	/// End Tab Nhóm
	
    void loadFavDish() {
    	Connection conn = SqliteConnection.Connector();
    	int userId = UserSingleton.getInstance().getUser_id();
    	String query = "select f.dish_id dish_id, d.dish_name dish_name, d.recipe recipe from fav_dish as f, dishes as d where f.dish_id = d.dish_id and f.user_id = "+userId+";";
    	
    	Statement sttm = null;
		ObservableList<String> li = null;
		try {
			sttm = conn.createStatement();
			ResultSet rs = sttm.executeQuery(query);
			favDishList.clear();
			ObservableList<DishEntity> list = FXCollections.observableArrayList();
			while(rs.next()) {
				DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"), rs.getString("recipe"));
				list.add(dish);
			}
			favDishList.addAll(list);
			filterFavDish(txtTimKiemCongThucMonAnYeuThi.getText());
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    void filterFavDish(String searchKey) {
    	favDishFilterList.clear();
    	ObservableList<DishEntity> matchs = FXCollections.observableArrayList();
    	for(DishEntity dish: favDishList) {
    		if(dish.getDish_name().contains(searchKey))
    		matchs.add(dish);
    	}
    	favDishFilterList.addAll(matchs);
    }
    @FXML
    void showRecipeFavDish(MouseEvent event) {
    	btlTenMonAnYeuThich.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if(event.getClickCount() == 2) {
            		btlConThucMonAnYeuThich.setText(btlTenMonAnYeuThich.getSelectionModel().getSelectedItem().getRecipe());
            	}
            }
        });
    }
    @FXML
    void deleteFavDish(ActionEvent event) {
		int userId = UserSingleton.getInstance().getUser_id();
		if(btlTenMonAnYeuThich.getSelectionModel() == null) return;
		int dishId = btlTenMonAnYeuThich.getSelectionModel().getSelectedItem().getDish_id();
		Connection conn = SqliteConnection.Connector();
		String query = "DELETE FROM fav_dish WHERE user_id="+userId+" and dish_id="+dishId+";";
		Statement sttm = null;
		 try {
				sttm = conn.createStatement();
				sttm.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error query database");
			}
	    	try {
	        	conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    loadFavDish();
    }
    @FXML
    void updateFavDish(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formUpdateFavDish = null;
    	try {
    		formUpdateFavDish = FXMLLoader.load(getClass().getResource("/fxml/AddFavouriteDishForm.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formUpdateFavDish);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadFavDish();
    }
    
    @FXML
    void searchFavDish(KeyEvent event) {
    	filterFavDish(((TextField)(event.getSource())).getText());
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
		loadMealPlan();
		loadFavDish();
		checkAdmin(UserSingleton.getInstance().isAdmin());
		colMealPlanFood.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, String>("foodName")
		);
		colMealDate.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, String>("date")
		);
    	
		// load values
		tblQuanLyMonDinhNau.setItems(mealPlanFilterList);
		
		favDishTable.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("dish_name")
		);
		btlTenMonAnYeuThich.setItems(favDishFilterList);
		
	}
	
	@FXML
    void loadMealPlan() {
    	int userId = UserSingleton.getInstance().getUser_id();
		System.out.println("2");
    	Connection conn = SqliteConnection.Connector();
    	String query = "select m.meal_plan_id as meal_plan_id, m.user_id as user_id, m.date as date, m.meal_number as meal_number, m.dish_id as dish_id, d.dish_name as dish_name from meal_plan m, dishes d where m.dish_id=d.dish_id and user_id = " + userId + ";";
    	Statement sttm = null;
		try {
			sttm = conn.createStatement();
			mealPlanList.clear();
			ResultSet rs = sttm.executeQuery(query);
			while(rs.next()) {
				MealPlanFood mealFood = new MealPlanFood(rs.getInt("meal_plan_id"), rs.getInt("user_id"), rs.getString("date"), rs.getInt("meal_number"), rs.getInt("dish_id"), rs.getString("dish_name"));
				mealPlanList.add(mealFood);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error query database");
		}
		filter(txtTimKiemMonDinhNau.getText());
    	try {
        	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    @FXML
    void addMealPlan(ActionEvent event) {
    	Stage stage = new Stage();
    	Parent formAddDish = null;
    	try {
    		formAddDish = FXMLLoader.load(getClass().getResource("/fxml/AddMealPlanForm.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    	Scene scene = new Scene(formAddDish);
    	stage.setScene(scene);
    	stage.showAndWait();
    	loadMealPlan();
    }
    @FXML
    void searchMealPlan(KeyEvent event) {
    	filter(((TextField)(event.getSource())).getText());
    }
    void filter(String searchKey) {
    	mealPlanFilterList.clear();
    	ObservableList<MealPlanFood> matchs = FXCollections.observableArrayList();
    	for(MealPlanFood food: mealPlanList) {
    		if(food.getFoodName().contains(searchKey))
    		matchs.add(food);
    	}
    	mealPlanFilterList.addAll(matchs);
    }
    @FXML
    void deleteMealPlan(ActionEvent event) {
        Connection conn = SqliteConnection.Connector();
        if(tblQuanLyMonDinhNau.getSelectionModel() == null) return;
        String query = "DELETE FROM meal_plan WHERE meal_plan_id="+tblQuanLyMonDinhNau.getSelectionModel().getSelectedItem().getMealPlanId()+";";
        Statement sttm = null;
        try {
            	sttm = conn.createStatement();
            	sttm.executeUpdate(query);
           } catch (SQLException e) {
            	e.printStackTrace();
            	System.out.println("Error query database");
           }
        try {
          	conn.close();
        } catch (Exception e) {
            e.printStackTrace();
   		}
        loadMealPlan();
    }
}

