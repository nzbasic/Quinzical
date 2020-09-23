package quinzical;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * This class manages the previous attempt history and game status
 * @author stephy
 *
 */
public class AttemptTrack {
	
	private boolean exists = false;
	private List<Category> categories= new ArrayList<Category>();
	private List<String> categoryNames= new ArrayList<String>();
	private List<Question> allQuestions= new ArrayList<Question>();
	private int[] record; //default value 0 means never attempted before, 1 means attempted.
	private Winnings winningRec=new Winnings();
	private static File tmpDir;
	
   /**
    * Constructor method, creates all necessary files and folder if
    * not created already.
    */
   public AttemptTrack() {
	   exists = checkDirExistence();
	   if (exists) {
		   //readQuestionsAndCategoriesGenerated();
		   //readAttempted();
		   
		} else {
			tmpDir.mkdirs();
			exists = true;
			resetAll();
		}
   }
   
   public static boolean checkDirExistence() {
	   tmpDir = new File("./attempt");
	   boolean existance = tmpDir.exists();
	   return existance;
   }
   
  
   /**
    * get the names of the five categories generated from previous attempts
    */
   public List<String> readCategoriesGenerated() {
	  readQuestionsAndCategoriesGenerated();
	   for (Category c:categories) {
		   categoryNames.add(c.getName());
	   }
		return categoryNames;
   }
   
   /**
    * Stores the 25 randomly generate questions in a file named questionsAttempt.txt
    * Each line contains question,answer,prize,category
    * @param q is a list of 25 Question Objects. The first 1-5 Questions are from the 
    *          first category, 6-10 from second category,11-15 from third category, ...
    */
   public void updateQuestionsGenerated(List<Question> qList) {
	   try {
			FileWriter fw = new FileWriter("./attempt/questionsAttempt.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (Question q:qList) {
				String questionLine=q.getQuestion()+","+q.getAnswer()+","+q.getPrize()+","+q.getParentCategory().getName();
				bw.write(questionLine);
				bw.newLine();
			}
			bw.close();
	   } catch (Exception e) {
			//add our own exception class to handle runtime exceptions
			throw new quinzicalExceptions(e.getMessage());
		}
   }
   
   /**
    * get 25 Question Objects
    */
   public List<Question> getQuestionsGenerated(){
	   readQuestionsAndCategoriesGenerated();
	   return allQuestions;
   }
   /**
    * Read the 25 questions from previous attempt/ continue game from last time
    */
   public void readQuestionsAndCategoriesGenerated(){
	   String line = null;
	   try {
			BufferedReader reader = new BufferedReader(new FileReader("./attempt/questionsAttempt.txt"));
			int i=0;
			int j=0;
			while ((line = reader.readLine()) != null) {
				// For each line, this section of code should be reused when reading files,maybe place inside another method
				//to avoid code duplication
					String[] questionfields = line.split(",");
				//Add Category names for every 5 element
				if (i%5==0) {
					categories.add(new Category(questionfields[3])); //Only 5 category names added, i=0,i=5,i=10,i=15,i=20
					j++; // 1,2,3,4,5
				}
				allQuestions.add(new Question(questionfields[0],questionfields[1],questionfields[2],categories.get(j-1))); //j=0,1,2,3,4
				//add the question
				categories.get(j-1).add(allQuestions.get(i)); //when j=0, adds question0,1,2,3,4.j=1 adds5,6,7,8,9
				i++;
			}
			reader.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
		
   }
   
   /**
    * resets previous attemptRecord for each question.
    */
   public void resetAttemptRecord() {
	   record=new int[25];
	   updateAttemptRecord();
	  
   }
   
   /**
    * Updates attemptRecord file
    */
   public void updateAttemptRecord() {
	   try {
			FileWriter fw = new FileWriter("./attempt/attemptRecord.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i:record) {
				bw.write(Integer.toString(i));
				bw.newLine();
			}
			bw.close();
	   } catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
   }
   
   /**
    * Sets the question index inside record to 1 when the question has been attempted
    */
   public void setAttempted(int questionIndex) {
	   readAttempted();
	   record[questionIndex]=1;
	   //save changes inside attemptRecord file
	   updateAttemptRecord();
   }
   
   /**
    * Returns an integer array of size 25, 0 means question[index] is not attempted, 1 means
    * the question at this index position has been attempted.
    * @return
    */
   public void readAttempted() {
	   record=new int[25];
	   String line = null;
	   try {
			BufferedReader reader = new BufferedReader(new FileReader("./attempt/attemptRecord.txt"));
			int i=0;
			while ((line = reader.readLine()) != null) {
				record[i]=Integer.parseInt(line);
				i++;
			}
			reader.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());	
			}
	   //return record;
   }
   
   /**
    * Get attempt Record
    */
   public int[] getAttemptedRecord() {
	   readAttempted();
	   return record;
   }
   /**
    * Returns true if all the questions have been  attempted by the user
    * @return
    */
   public boolean checkIfAllCluesAttempted() {
	   for (int i=0;i<25;i++) {
		   if (record[i]==0) {
			   return false; //if one question is not attempted return false
		   }
	   }
	   return true; //all the questions have been attempted
   }
   
   /**
    * Reset all attempts/ resets questions,categories and winnings
    */
   public void resetAll() {
	   //removes all the questions
	   allQuestions.clear();
	   //removes all the categories
	   categories.clear();
	    //CHANGE!!! CALL RANDOM GENERATOR METHOD AGAIN! TO RESET.
	   RandomGenerator rg= new RandomGenerator();
	   rg.generateCategoriesAtRandom();
	   rg.generateGameQuestions();
	   
	  
	   //clears attempt record
	   record=new int[25];
	   //writes to attemptRecord file
	   updateAttemptRecord();
	   winningRec.resetWinnings();
   }
   
}
