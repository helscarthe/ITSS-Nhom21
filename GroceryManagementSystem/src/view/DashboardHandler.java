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
    private TableView<MealPlanFood> tblQuanLyMonDinhNau;

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
    
    @FXML
    private TableColumn<DishEntity, String> favDishTable;
    
    
    @FXML
    private TableColumn<MealPlanFood, Integer> colMealIndex;
    
    @FXML
    private TableColumn<MealPlanFood, String> colMealPlanFood;
    
    @FXML
    private TableColumn<MealPlanFood, Integer> colMealType;
    
    @FXML
    private TableColumn<MealPlanFood, String> colMealDate;
    
	ObservableList<UserEntity> dataUsers;
    
	ObservableList<DishEntity> dataDishes;
    
	ObservableList<RawFoodEntity> dataFood;
    
	ObservableList<MealPlanFood> mealPlanList = FXCollections.observableArrayList();
	ObservableList<MealPlanFood> mealPlanFilterList = FXCollections.observableArrayList();
	
	ObservableList<DishEntity> favDishList = FXCollections.observableArrayList();
	ObservableList<DishEntity> favDishFilterList = FXCollections.observableArrayList();
    
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
    	FilteredList<RawFoodEntity> filteredFoodList = dataFood.filtered(filter);
    	
    	// load list to table
    	tblDulieuThucPham.setItems(filteredFoodList);
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
		loadMealPlan();
		loadFavDish();
		checkAdmin(UserSingleton.getInstance().isAdmin());
		colMealIndex.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, Integer>("mealPlanId")
		);
		colMealPlanFood.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, String>("foodName")
		);
		colMealDate.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, String>("date")
		);
		colMealType.setCellValueFactory(
				new PropertyValueFactory<MealPlanFood, Integer>("mealNumber")
		);
    	
		// load values
		tblQuanLyMonDinhNau.setItems(mealPlanFilterList);
		
		favDishTable.setCellValueFactory(
				new PropertyValueFactory<DishEntity, String>("dish_name")
		);
		btlTenMonAnYeuThich.setItems(favDishFilterList);
		
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

