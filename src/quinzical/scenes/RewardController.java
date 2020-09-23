package quinzical.scenes;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RewardController {

	@FXML
	private Label points;
	@FXML
	private Button returnMenu,newGame;
	
	public void setPoints(String total) {
		points.setText(total);
	}
	
	@FXML
	public void returnToMainMenu(Event e) throws IOException {
		new GameController().returnToMenu(e);
	}
	
	@FXML
	public void restartGame(Event e) throws IOException {
		new MenuController().startGame(e);
	}
}
