package quinzical.scenes;

import java.io.File;
import java.io.FileWriter;
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
    private void initlialize() {
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
        if (category == null || questionString == null || answerString == null) {
            popup.setVisible(true);
            return;
        }

        String name = category.getName();
        File file = new File("./attempt/" + name);
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(questionString + "," + answerString + "\n");
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void cancel() throws IOException {
        quinzical.loadFXML("Menu");
    }
}
