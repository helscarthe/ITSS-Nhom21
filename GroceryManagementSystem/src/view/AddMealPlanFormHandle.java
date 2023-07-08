package view;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import entity.DishEntity;
import entity.UserSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import service.SqliteConnection;
public class AddMealPlanFormHandle extends BaseHandler {
	@FXML
	    private ChoiceBox<Integer> cb_minute;
	
	    @FXML
	    private ChoiceBox<Integer> cb_second;
	
	    @FXML
	    private ChoiceBox<Integer> cb_hour;
	
	    @FXML
	    private TableColumn<DishEntity, String> cl_meal_name;
	
	    @FXML
	    private Text form_title;
	
	    @FXML
	    private DatePicker dp_date_meal;
	
	    @FXML
	    private TableColumn<DishEntity, String> cl_search_name;
	
	    @FXML
	    private TextField tf_meal;
	
	    @FXML
	    private TableView<DishEntity> tb_choose_plan;
	    @FXML
	    private TableView<DishEntity> tb_meal_search;
	    ObservableList<DishEntity> dishList = FXCollections.observableArrayList();
	    ObservableList<DishEntity> dishMatchedList = FXCollections.observableArrayList();
	    ObservableList<DishEntity> dishChoosedList = FXCollections.observableArrayList();
	    ObservableList<Integer> hours = FXCollections.observableArrayList();
	    ObservableList<Integer> minutes = FXCollections.observableArrayList();
	    ObservableList<Integer> seconds = FXCollections.observableArrayList();
	    @FXML
	    public void initialize() {
	        loadData();
	        cl_search_name.setCellValueFactory(
					new PropertyValueFactory<DishEntity, String>("dish_name")
			);
	     // load values
	     	tb_meal_search.setItems(dishMatchedList);
	     	
	     	cl_meal_name.setCellValueFactory(
	     			new PropertyValueFactory<DishEntity, String>("dish_name")
	     	);
	     // load values
	     	dishChoosedList = FXCollections.observableArrayList();
	     	tb_choose_plan.setItems(dishChoosedList);
	     	for(int i = 0; i < 24; i++) {
	     		hours.add(i);
	     	}
	     	for(int i = 0; i< 60; i++) {
	     		minutes.add(i);
	     	}
	     	for(int i=0; i< 60; i++) {
	     		seconds.add(i);
	     	}
	     	cb_hour.setItems(hours);
	     	cb_minute.setItems(minutes);
	     	cb_second.setItems(seconds);
	     	cb_hour.setValue(8);
	     	cb_minute.setValue(0);
	     	cb_second.setValue(0);
	     	dp_date_meal.setValue(LocalDate.now());
	    }

		@FXML
	    void submit(ActionEvent event) {
	    	String date = dp_date_meal.getValue().toString();
	    	String hour = cb_hour.getValue().toString();
	    	String minute = cb_minute.getValue().toString();
	    	String second = cb_second.getValue().toString();
	    	int userId = UserSingleton.getInstance().getUser_id();
	    	String values = "";
	    	int size = dishChoosedList.size();
	    	if(size == 0) return;
	    	int count = 0;
	    	for(DishEntity dish: dishChoosedList) {
	    		count++;
	    		values += "("+userId+",\""+date+" "+ hour+":"+minute+":"+second+".000\","+1+","+dish.getDish_id()+")";
	    		if(count < size) {
	    			values +=",";
	    		}
	    	}
	    	System.out.println(values);
	    	Connection conn = SqliteConnection.Connector();
	    	String query = "insert into meal_plan(user_id, date, meal_number,dish_id) values"+values+";";
	    	Statement sttm = null;
			try {
				sttm = conn.createStatement();
				sttm.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Looi");
			}
	    	try {
	        	conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	stage.close();
	    }
	
	    @FXML
	    void search(KeyEvent event) {
	    	String input = ((TextField)(event.getSource())).getText();
	    	ObservableList<DishEntity> matched = FXCollections.observableArrayList();
	    	dishMatchedList.clear();
	    	for(DishEntity dish: dishList) {
	    		if(dish.getDish_name().contains(input)) {
		    		matched.add(dish);
	    		}
	    	}
	    	dishMatchedList.addAll(matched);
	    }
	
	    @FXML
	    void cancel(ActionEvent event) {
	    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	stage.close();
	    }
	    
	    void loadData() {
	    	Connection conn = SqliteConnection.Connector();
	    	String query = "select dish_id, dish_name from dishes;";
	    	Statement sttm = null;
			try {
				sttm = conn.createStatement();
				ResultSet rs = sttm.executeQuery(query);
				int index = 0;
				while(rs.next()) {
					index++;
					DishEntity dish = new DishEntity(rs.getInt("dish_id"), rs.getString("dish_name"), " ");
					dishList.add(dish);
				}
				dishMatchedList.addAll(dishList);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error query database");
			}
	    	try {
	        	conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    @FXML
	    void addMeal(MouseEvent event) {
	    	tb_meal_search.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	if(event.getClickCount() == 2) {
	            		 dishChoosedList.add(tb_meal_search.getSelectionModel().getSelectedItem());	
	            	}
	            }
	        });
	    }
	    @FXML
	    void minusMeal(MouseEvent event) {
	    	tb_choose_plan.setOnMouseClicked(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	if(event.getClickCount() == 2) {
	            		 dishChoosedList.remove(tb_choose_plan.getSelectionModel().getSelectedItem());	
	            	}
	            }
	        });
	    }
}

