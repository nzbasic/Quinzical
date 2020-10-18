package quinzical.scenes;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public abstract class Help {
	@FXML
	private AnchorPane helpOverlay;

	@FXML
	public void help() {
		helpOverlay.setVisible(true);
	}

	@FXML
	public void helpDown() {
		helpOverlay.setVisible(false);
	}
}
