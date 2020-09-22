package quinzical.scenes;

import java.io.IOException;
import java.util.List;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quinzical.AttemptTrack;
import quinzical.Question;
import quinzical.Winnings;

public class QuestionController {
 @FXML
 private Text question;
 @FXML
 private TextField answer;
 @FXML
 private Button giveup;
 @FXML
 private Button submit;
 @FXML
 private Label message;
 @FXML
 private Button continueGame;
 private int lineNumber;
 private List<Question> questionsAndAnswers;
 
 public void setQuestion(String s) {
	 question.setText(s);
 }
 public void setQuestionLines(int index, List<Question> questionLines) {
	 lineNumber=index;
	 questionsAndAnswers=questionLines;
	
 }
 @FXML
 public void checkAnswer(Event e) {
	 String usrInput = answer.getText();
	 //Check Answers
	 Question q=questionsAndAnswers.get(lineNumber-1);
	 if (q.checkAnswer(usrInput)) {
		 //Add Winnings
		 new Winnings().updateWinnings(Integer.parseInt(q.getPrize()));
		 message.setText("Correct!");		 		 
	 }else {
		 message.setText("The correct answer is: "+q.getAnswer());
	 }
	//Set Question as attempted
		 new AttemptTrack().setAttempted(lineNumber-1);
	 submit.setVisible(false);
	 giveup.setVisible(false);
	 message.setVisible(true);
	 continueGame.setVisible(true);
	
 }
 
 @FXML
 public void returnToQuestionSelection(Event e) throws IOException {
	 FXMLLoader gameLoad = new FXMLLoader(getClass().getResource("Game.fxml"));
	 
	   Parent gameParent = gameLoad.load();
	   GameController gc = gameLoad.getController();
		  gc.oldGameData();
	   
	   Scene gameScene= new Scene(gameParent);
	   Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	   quinzicalStage.setScene(gameScene);
	   quinzicalStage.show();
 }

}
