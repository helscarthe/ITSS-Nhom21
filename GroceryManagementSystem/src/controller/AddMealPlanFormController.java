package controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
public class AddMealPlanFormController {
	@FXML
    private ChoiceBox<?> cb_minute;

    @FXML
    private ChoiceBox<?> cb_second;

    @FXML
    private TableColumn<?, ?> cl_add;

    @FXML
    private ChoiceBox<?> cb_hour;

    @FXML
    private TableColumn<?, ?> cl_meal_name;

    @FXML
    private Text form_title;

    @FXML
    private TableColumn<?, ?> cl_stt;

    @FXML
    private TableColumn<?, ?> cl_search_stt;

    @FXML
    private TableColumn<?, ?> cl_minus;

    @FXML
    private DatePicker dp_date_meal;

    @FXML
    private TableColumn<?, ?> cl_search_name;

    @FXML
    private TextField tf_meal;

    @FXML
    private TableColumn<?, ?> cl_num;

    @FXML
    private TableView<?> tb_choose_plan;

    @FXML
    void submit(ActionEvent event) {
    	
    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void cancel(ActionEvent event) {

    }

}
