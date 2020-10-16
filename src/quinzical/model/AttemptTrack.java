package quinzical.model;

import quinzical.quinzicalExceptions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class manages the previous attempt history and game status
 * 
 * @author stephy
 *
 */
public class AttemptTrack {

	private boolean exists = false;
	private List<Category> categories = new ArrayList<Category>();
	private List<String> categoryNames = new ArrayList<String>();
	private List<Question> allQuestions = new ArrayList<Question>();
	private int[] record; // default value 0 means never attempted before, 1 means attempted.
	private Winnings winningRec = new Winnings();
	private static File tmpDir;

	/**
	 * Constructor method, creates all necessary files and folder if not created
	 * already.
	 */
	public AttemptTrack() {
		exists = checkDirExistence();
		if (!exists) {
			tmpDir.mkdirs();
			exists = true;
			resetAll();
		}
	}

	/**
	 * Checks if the directory named attempt exists
	 * 
	 * @return true if exists, false if doesn't exist.
	 */
	public static boolean checkDirExistence() {
		tmpDir = new File("./attempt");
		boolean existance = tmpDir.exists();
		return existance;
	}

	/**
	 * get the names of the five categories generated from previous attempts
	 */
	public List<String> readCategoriesGenerated(String section) {
		readQuestionsAndCategoriesGenerated(section);
		for (Category c : categories) {
			categoryNames.add(c.getName());
		}
		return categoryNames;
	}

	/**
	 * Stores the randomly generated questions in a file named
	 * questionsAttempt.txt or internatinalAttempt.txt Each line contains question,answer,prize,category
	 * 
	 * @param qList is a list of Question Objects. 
	 */
	public void updateQuestionsGenerated(List<Question> qList,String section) {
		FileWriter fw=null;
		try {
			if (section.equals("NZ")){
			fw = new FileWriter("./attempt/questionsAttempt.txt");
			}
			else {
	        fw = new FileWriter("./attempt/internationalAttempt.txt");
			}
			
			BufferedWriter bw = new BufferedWriter(fw);
			for (Question q : qList) {
				String questionLine = q.getQuestion() + "," + q.getAnswer() + "," + q.getPrize() + ","
						+ q.getParentCategory().getName();
				bw.write(questionLine);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			// add our own exception class to handle runtime exceptions
			throw new quinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * get 25 Question Objects
	 */
	public List<Question> getQuestionsGenerated(String section) {
		readQuestionsAndCategoriesGenerated(section);
		return allQuestions;
	}

	/**
	 * Read the questions from previous attempt/ continue game from last time
	 */
	public void readQuestionsAndCategoriesGenerated(String section) {
		String line = null;
		BufferedReader reader=null;
		int questionsPerCategory=0;
		try {
			if (section.equals("NZ")) {
			reader = new BufferedReader(new FileReader("./attempt/questionsAttempt.txt"));
			questionsPerCategory=5;
		}
			else {
				reader = new BufferedReader(new FileReader("./attempt/internationalAttempt.txt"));
				questionsPerCategory=2;
			}
			int i = 0;
			int j = 0;
			while ((line = reader.readLine()) != null) {
				// For each line, this section of code should be reused when reading files,maybe
				// place inside another method
				// to avoid code duplication
				String[] questionfields = line.split(",");
				// Add Category names for every 5or 2 elements
				if (i % questionsPerCategory == 0) {
					categories.add(new Category(questionfields[3])); 
																		
					j++; 
				}
				allQuestions.add(
						new Question(questionfields[0], questionfields[1], questionfields[2], categories.get(j - 1))); // j=0,1,2,3,4
				// add the question
				categories.get(j - 1).add(allQuestions.get(i)); // when j=0, adds question0,1,2,3,4.j=1 adds5,6,7,8,9
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
		record = new int[25];
		updateAttemptRecord("NZ");

	}
	

	/**
	 * Updates attemptRecord file
	 */
	public void updateAttemptRecord(String section) {
		FileWriter fw=null;
		try {
			if (section.equals("NZ")) {
			fw = new FileWriter("./attempt/attemptRecord.txt");
			}
			else {
				fw = new FileWriter("./attempt/bonusAttemptRecord.txt");
			}
			BufferedWriter bw = new BufferedWriter(fw);
			for (int i : record) {
				bw.write(Integer.toString(i));
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * Sets the question index inside record to 1 when the question has been
	 * attempted
	 */
	public void setAttempted(int questionIndex,String section) {
		readAttempted(section);
		record[questionIndex] = 1;
		// save changes inside attemptRecord file
		updateAttemptRecord(section);
	}

	/**
	 * Returns an integer array of size 25, 0 means question[index] is not
	 * attempted, 1 means the question at this index position has been attempted.
	 * 
	 * @return
	 */
	public void readAttempted(String section) {
		record = new int[25];
		BufferedReader reader=null;
		String line = null;
		try {
			if (section.equals("NZ")) {
			reader = new BufferedReader(new FileReader("./attempt/attemptRecord.txt"));
			}
			else {
				reader = new BufferedReader(new FileReader("./attempt/bonusAttemptRecord.txt"));	
			}
			int i = 0;
			while ((line = reader.readLine()) != null) {
				record[i] = Integer.parseInt(line);
				i++;
			}
			reader.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
		// return record;
	}

	/**
	 * Get attempt Record
	 */
	public int[] getAttemptedRecord(String section) {
		readAttempted(section);
		return record;
	}

	/**
	 * Reset all attempts/ resets questions,categories and winnings
	 * n:number of Q per category
	 */
	public void resetAll() {
		// removes all the questions
		allQuestions.clear();
		// removes all the categories
		categories.clear();
		RandomGenerator rg = new RandomGenerator();
		
		rg.generateCategoriesAtRandom(5,"NZ");
		rg.generateGameQuestions(5,"NZ");
		rg.generateCategoriesAtRandom(3, "International");
		rg.generateGameQuestions(2,"International");

		// clears attempt record
		record = new int[25];
		// writes to attemptRecord file
		updateAttemptRecord("NZ");
		updateAttemptRecord("International");
		winningRec.resetWinnings();
	}

	public void recordWrongQuestion(Question question) {
		List<Question> list = getWrongQuestions();
		list.add(question);
		writeWrongQuestions(list);
	}

	public List<Question> getWrongQuestions() {
		List<Question> output = new ArrayList<Question>();
		File file = new File("./attempt/wrongQuestions.txt");
		if (file.exists()) {
			List<String> list = new ArrayList<String>();
			try {
				String line = null;
				Scanner scanner = new Scanner(new FileReader("./attempt/wrongQuestions.txt"));
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					list.add(line);
				}
				scanner.close();
			} catch (Exception e) {
				throw new quinzicalExceptions(e.getMessage());
			}

			for (String string : list) {
				String[] data = string.split(",");
				String question = data[0];
				String answer = data[1];
				Question questionObj = new Question(question,answer, null, null);
				output.add(questionObj);
			}

		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new quinzicalExceptions(e.getMessage());
			}
		}
		return output;
	}

	private void writeWrongQuestions(List<Question> list) {
		try {
			FileWriter fw = new FileWriter("./attempt/wrongQuestions.txt");
			for (Question q : list) {
				String question = q.getQuestion();
				String answer = q.getAnswer();
				fw.write(question + "," + answer + "\n");
			}
			fw.close();
		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
	}
}
