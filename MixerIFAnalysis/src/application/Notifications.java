package application;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Notifications {
	
	public Notifications () {

	}
	
	public void alert(String title, String body) {

		//obj
		Alert errorAlert;

		//assign
		errorAlert = new Alert(AlertType.ERROR);
		errorAlert.setHeaderText(title);
		errorAlert.setContentText(body);

		//show
		errorAlert.showAndWait();
	}
	
	public void warn(String title, String body) {

		//assign
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(body);

		//show
		alert.showAndWait();
		
	}

	public boolean confirm (String title, String body) {

		//assign
		Alert alert = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
		alert.setTitle("Confirmation");
		alert.setHeaderText(title);
		alert.setContentText(body);

		Optional<ButtonType> result = alert.showAndWait();
		 
		if (result.get() == ButtonType.YES) return true;
		return false;
	}	
	

}
