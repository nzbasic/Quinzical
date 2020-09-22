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
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import quinzical.AttemptTrack;
import quinzical.Question;

public class GameController {
   @FXML
   private Label category1,category2,category3,category4,category5;
   @FXML
   private Button c1q1,c1q2,c1q3,c1q4,c1q5,c2q1,c2q2,c2q3,c2q4,c2q5,
                  c3q1,c3q2,c3q3,c3q4,c3q5,c4q1,c4q2,c4q3,c4q4,c4q5,
                  c5q1,c5q2,c5q3,c5q4,c5q5,returnMenu;

   private Button[] questionbuttons;
   private AttemptTrack attempt=new AttemptTrack();
   private List<String> categoryNames;
   private List<Question> allq;
   
   
   public void newGameData() {
	   attempt.resetAll();
	   setupGame();
   }
   
   public void oldGameData() {
	   setupGame();
   }
   
   public void setupGame() {
	   Label[] categories= {category1,category2,category3,category4,category5};
	   categoryNames=attempt.readCategoriesGenerated();
	   for (int i=0;i<5;i++) {
		   categories[i].setText(categoryNames.get(i));
	   }
	   Button[] questions= {c1q1,c1q2,c1q3,c1q4,c1q5,c2q1,c2q2,c2q3,c2q4,c2q5,
               c3q1,c3q2,c3q3,c3q4,c3q5,c4q1,c4q2,c4q3,c4q4,c4q5,
               c5q1,c5q2,c5q3,c5q4,c5q5};
	   questionbuttons=questions;
	   //Hide questions already attempted
	   boolean countCate=true;
	   int[] attemptedRecord=attempt.getAttemptedRecord();
	   for (int i=0;i<attemptedRecord.length;i++) {
		   //Enable one button for each category
		   if (i%5==0) {
			   countCate=true;
		   }
		   
		   if (attemptedRecord[i]==1) {
			   //If attempted, hide button
			   questions[i].setVisible(false);
			   //Only Enable lowest value question		   
		   }
		   else if (attemptedRecord[i]==0 && countCate==true) {
			   questions[i].setDisable(false);
			   countCate=false;
		   }
		   
	   }
   }
   
   @FXML
   public void changeToAnswerScreen(Event e) throws IOException {
	   //Get Id of button clicked, works if clicked object extends control
	   String ButtonId=((Control)e.getSource()).getId();
	   //Set button invisible
	   ((Button)e.getSource()).setVisible(false);
	   //Get Question
	   int categoryIndex=Integer.parseInt(ButtonId.substring(1,2));
	   int questionIndex=Integer.parseInt(ButtonId.substring(3));
	   int lineNumber=(categoryIndex-1)*5+questionIndex;
	   
	   //Enable nextbutton click
	   if (questionIndex<5) {
		   (questionbuttons[lineNumber]).setDisable(false);
	   }
	   allq = attempt.getQuestionsGenerated();
	   
	   String question=allq.get(lineNumber-1).getQuestion();
	   
	   FXMLLoader questionLoad = new FXMLLoader(getClass().getResource("QuestionAndAnswer.fxml"));
	   Parent questionParent = questionLoad.load();
	   QuestionController qc = questionLoad.getController();
	   qc.setQuestion(question);
	   qc.setQuestionLines(lineNumber,allq);
	   
	   Scene questionScene= new Scene(questionParent);
	   Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	   quinzicalStage.setScene(questionScene);
	   quinzicalStage.show();
	   
   }
   @FXML
   public void returnToMenu(Event e) throws IOException {
	   FXMLLoader menuLoad = new FXMLLoader(getClass().getResource("GameMenu.fxml"));
	   Parent menuParent = menuLoad.load();
	   
	   Scene questionScene= new Scene(menuParent);
	   Stage quinzicalStage = (Stage)((Node)e.getSource()).getScene().getWindow();
	   quinzicalStage.setScene(questionScene);
	   quinzicalStage.show();
   }
}
