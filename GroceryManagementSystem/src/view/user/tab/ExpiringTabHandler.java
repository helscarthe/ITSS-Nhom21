package view.user.tab;

import java.util.Comparator;

import controller.FridgeFoodController;
import entity.FridgeEntity;
import entity.UserSingleton;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ExpiringTabHandler {

    @FXML
    private TableColumn<FridgeEntity, Integer> colFoodCount;

    @FXML
    private TableColumn<FridgeEntity, String> colFoodName;

    @FXML
    private TableColumn<FridgeEntity, String> colFoodType;

    @FXML
    private TableColumn<FridgeEntity, String> colRemainingTime;

    @FXML
    private TableColumn<FridgeEntity, String> colUnit;

    @FXML
    private TableView<FridgeEntity> tdDoAnSapHetHan;
    
    private FridgeFoodController controller;
    
    ObservableList<FridgeEntity> data;
    
    @FXML
    private void initialize() {
    	controller = new FridgeFoodController();
		data = controller.getFoodListByUserId(UserSingleton.getInstance().getUser_id());

		colFoodName.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("raw_food_name"));
		colFoodType.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("food_typeString"));
		colRemainingTime.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("remainingDaysString"));
		colFoodCount.setCellValueFactory(new PropertyValueFactory<FridgeEntity, Integer>("number"));
		colUnit.setCellValueFactory(new PropertyValueFactory<FridgeEntity, String>("unit"));
		
		colRemainingTime.setSortable(true);
		colRemainingTime.setComparator(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return extractInt(o1) - extractInt(o2);
			}

	        int extractInt(String s) {
	            String num = s.replaceAll("\\D", "");
	            // return 0 if no digits found
	            return num.isEmpty() ? 0 : Integer.parseInt(num);
	        }
			
		});
		colRemainingTime.setSortType(TableColumn.SortType.ASCENDING);

		tdDoAnSapHetHan.setItems(data);
		tdDoAnSapHetHan.getSortOrder().add(colRemainingTime);
		tdDoAnSapHetHan.sort();
    }
    
    public void refreshData() {
		data = controller.getFoodListByUserId(UserSingleton.getInstance().getUser_id());
		tdDoAnSapHetHan.refresh();
    }

}
