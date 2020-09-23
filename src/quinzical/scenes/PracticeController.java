package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import quinzical.Category;
import javafx.scene.Node;
import quinzical.Question;
import quinzical.RandomGenerator;

public class PracticeController {
    private RandomGenerator generator = new RandomGenerator();
    private Button[] buttonList;
    private List<Category> categories;

    @FXML
    private Button ctg1, ctg2, ctg3, ctg4, ctg5;

    @FXML
    public void initialize() {
        buttonList = new Button[] { ctg1, ctg2, ctg3, ctg4, ctg5 };
        categories = generator.getPracticeCategories();
        for (int i = 0; i < 5; i++) {
            Button button = buttonList[i];
            Category category = categories.get(i);
            button.setText(category.getName());
        }
    }

    @FXML
    public void select(Event e) throws IOException {
        String ButtonId=((Control)e.getSource()).getId();
        int categoryNumber = Integer.parseInt(ButtonId.substring(3));
        Question questionSelected = generator.generatePracticeQuestion(categories.get(categoryNumber-1));
        FXMLLoader questionLoad = new FXMLLoader(getClass().getResource("QuestionAndAnswer.fxml"));
	    Parent questionParent = questionLoad.load();
        QuestionController qc = questionLoad.getController();
        qc.setPracticeMode();
	    qc.setQuestion(questionSelected);
	   
	    Scene questionScene= new Scene(questionParent);
	    Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    quinzicalStage.setScene(questionScene);
	    quinzicalStage.show();
    }
}
