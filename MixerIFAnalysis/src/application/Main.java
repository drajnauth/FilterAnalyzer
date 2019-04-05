package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Notifications note = new Notifications();
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root,632,760);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("VE3OOI BFO/LO Mixer Byproduct Analysis");
			primaryStage.sizeToScene();
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			note.alert("Exception", "Exception loading initial scene: " + e.getMessage());
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
