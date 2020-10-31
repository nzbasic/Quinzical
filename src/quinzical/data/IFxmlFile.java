package quinzical.data;

/**
 * Interface for FxmlFile enum
 */
public interface IFxmlFile {
	default String getPath() {
		return FxmlFile.FOLDER.getPath() + this.toString() + ".fxml";
	}
}
