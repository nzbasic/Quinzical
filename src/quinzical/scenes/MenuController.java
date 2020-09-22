package quinzical.scenes;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.stage.Stage;

public class MenuController {
@FXML
private Button newGame;

@FXML
public void startGame(Event e) throws IOException {
	   FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
	 
	   Parent gameParent = gameLoad.load();
	   GameController gc = gameLoad.getController();
	   //check id
	   String ButtonId=((Control)e.getSource()).getId();
	   if (ButtonId.equals("newGame")) {
	   gc.newGameData();
	   }
	   else {
		  gc.oldGameData();
	   }
	   
	   Scene gameScene= new Scene(gameParent);
	   Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	   quinzicalStage.setScene(gameScene);
	   quinzicalStage.show();
}

}
