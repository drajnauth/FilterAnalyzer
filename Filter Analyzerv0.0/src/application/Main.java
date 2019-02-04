package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;



public class Main extends Application {
	SceneController sceneController = new SceneController();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			sceneController.setRootStage(primaryStage);
			
			Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
			Scene scene = new Scene(root,1100,750);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Crystal Ladder Filter Analyzer");
			primaryStage.sizeToScene();
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			System.out.println( "Exception: " + e.getMessage() );
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
