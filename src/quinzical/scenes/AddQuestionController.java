package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import quinzical.quinzical;
import quinzical.model.Category;
import quinzical.model.CategoryLoader;

public class AddQuestionController {
    @FXML
    private ComboBox<Category> categoryBox;
    @FXML
    private TextField questionText;
    @FXML
    private TextField answerText;
    @FXML
    private Label popup;

    @FXML
    private void initialize() {
        CategoryLoader loader = new CategoryLoader();
        List<Category> list = loader.getCategories();
        for (Category category : list) {
            categoryBox.getItems().add(category);
        }
    }

    @FXML
    private void add() {
        Category category = categoryBox.getValue();
        String questionString = questionText.getText();
        String answerString = answerText.getText();
        if ( (category == null) || (questionString.equals("")) || (answerString.equals("")) ) {
            popup.setText("Please fill in the boxes");
            popup.setVisible(true);
            return;
        }
        popup.setText("Question added!");
        popup.setVisible(true);
        categoryBox.setValue(null);
        questionText.setText("");
        answerText.setText("");
    }

    @FXML
    private void cancel() throws IOException {
        quinzical.loadFXML("Menu");
    }
}
