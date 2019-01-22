package application;

import javafx.stage.Stage;

public class SceneController {
	
	//TODO Maybe make an array of stages and scenes associated with with a Window ID (e.g. userSignInID, homeViewID, questionViewID)
	// this way, it can be dynamic i.e. register a Window ID with a Stage and Screen
	// Look at video https://www.youtube.com/watch?v=5GsdaZWDcdY&index=28&list=PLf7rWERNu4mxSP2Fwyyaobs9XMZI6e3fc&t=1365s
	private static Stage rootStage;
	private static Stage chartStage;
	private static Stage progressBarStage;

	
	public void setProgressBarStage (Stage stage) {
		SceneController.progressBarStage = stage;
	}
	
	public Stage getProgressBarStage () {
		return SceneController.progressBarStage;
	}
	
	
	public void setChartStage (Stage stage) {
		SceneController.chartStage = stage;
	}
	
	public Stage getChartStage () {
		return SceneController.chartStage;
	}
	
	public void setRootStage (Stage stage) {
		SceneController.rootStage = stage;
	}
	
	public Stage getRootStage () {
		return SceneController.rootStage;
	}

		
}
