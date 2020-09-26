package quinzical.scenes;

import java.io.IOException;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quinzical.AttemptTrack;
import quinzical.Winnings;

public class MenuController {
	@FXML
	private Label money;

	@FXML
	private AnchorPane popup;

	@FXML
	private ImageView background,playImage,exitImage; 

	public void initialize() {
		money.setText("$" + new Winnings().getWinnings());
		Thread imageLoader = new Thread(() -> {
			Image exit = new Image(getClass().getResourceAsStream("assets/exit.png"));
			Image nz = new Image(getClass().getResourceAsStream("assets/nz.jpg"));
			Image play = new Image(getClass().getResourceAsStream("assets/play.png"));
			Platform.runLater(() -> {
				exitImage.setImage(exit);
				background.setImage(nz);
				playImage.setImage(play);
			});
		});
		imageLoader.start();
	}

	@FXML
	public void startGame(Event e) throws IOException {

	   AttemptTrack track = new AttemptTrack();
	   int[] record = track.getAttemptedRecord();
	   boolean flag = false;
	   for (int i : record) {
		   if (i == 1) {
			flag = true;
		   }
	   }
	   if (!flag) {
			FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
			Parent gameParent = gameLoad.load();
			GameController gc = gameLoad.getController();
			gc.newGameData();
			Scene gameScene= new Scene(gameParent);
	    	Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    	quinzicalStage.setScene(gameScene);
	    	quinzicalStage.show();
	    	gc.checkIfAllAttempted();
	   } else {
		   popup.setVisible(true);
	   } 
	}

@FXML
public void startPractice(Event e) throws IOException {
	FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Practice.fxml"));
	Parent gameParent = gameLoad.load();
	Scene practiceScene = new Scene(gameParent);
	Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	quinzicalStage.setScene(practiceScene);
	quinzicalStage.show();
}

@FXML
public void exit(Event e) {
	Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	quinzicalStage.close();
}

@FXML
public void continueGame(Event e) throws IOException {
	FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
	Parent gameParent = gameLoad.load();
	GameController gc = gameLoad.getController();
	gc.oldGameData();
	Scene gameScene= new Scene(gameParent);
	Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	quinzicalStage.setScene(gameScene);
	quinzicalStage.show();
	gc.checkIfAllAttempted();
}

@FXML
public void newGame(Event e) throws IOException {
	FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
	Parent gameParent = gameLoad.load();
	GameController gc = gameLoad.getController();
	gc.newGameData();
	Scene gameScene= new Scene(gameParent);
	Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	quinzicalStage.setScene(gameScene);
	quinzicalStage.show();
}

}
