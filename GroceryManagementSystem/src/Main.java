import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage){
		// TODO Auto-generated method stub
		try {
	    	Parent login = FXMLLoader.load(Main.class.getResource("/fxml/Login.fxml"));
			Scene scene = new Scene(login);
	//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace(System.out);
		}
	}
	public static void main(String[] args) {
		System.out.println("entry");
		launch(args);
	}

}
