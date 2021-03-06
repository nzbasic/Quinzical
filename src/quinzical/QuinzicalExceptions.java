package quinzical;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class QuinzicalExceptions extends RuntimeException {
	/**
	 * This class shows the users an alert message for all QuinzicalExceptions
	 */
	private static final long serialVersionUID = 1L;

	public QuinzicalExceptions(String err) {

		Alert errormsg = new Alert(AlertType.ERROR);
		errormsg.setTitle("Error");
		errormsg.setHeaderText("An error has occured");

		errormsg.setContentText(
				err + "\nIf the program is not functioning as expected, please quit the game and try restarting");
		errormsg.showAndWait();

	}
}
