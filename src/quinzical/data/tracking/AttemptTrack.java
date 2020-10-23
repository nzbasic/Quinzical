package quinzical.data.tracking;

import quinzical.QuinzicalExceptions;
import quinzical.data.RandomGenerator;
import quinzical.data.Sections;
import quinzical.data.model.Category;
import quinzical.data.model.Question;

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
 */
public class AttemptTrack {

	private final List<Category> _categories = new ArrayList<>();
	private final List<String> _categoryNames = new ArrayList<>();
	private final List<Question> _allQuestions = new ArrayList<>();
	private int[] _record; // default value 0 means never attempted before, 1 means attempted.
	private final WinningsTrack _winningRec = new WinningsTrack();
	private static File _tmpDir;

	private static final int _questionsPerCategoryNormal = 5;
	private static final int _questionsPerCategoryInternational = 2;
	private static final int _numberOfQuestions = 25;
	private static final String _attemptFolder = "./attempt";
	private static final String _questionsAttemptFile = _attemptFolder + "/questionsAttempt.txt";
	private static final String _internationalAttemptFile = _attemptFolder + "/internationalAttempt.txt";
	private static final String _attemptRecord = _attemptFolder + "/attemptRecord.txt";
	private static final String _internationalAttemptRecord = _attemptFolder + "/bonusAttemptRecord.txt";
	private static final String _wrongQuestions = _attemptFolder + "/wrongQuestions.txt";
	
	/**
	 * Constructor method, creates all necessary files and folder if not created
	 * already.
	 */
	public AttemptTrack() {
		boolean exists = checkDirExistence();
		if (!exists) {
			_tmpDir.mkdirs();
			resetAll();
		}
	}

	/**
	 * Checks if the directory named attempt exists
	 * 
	 * @return true if exists, false if doesn't exist.
	 */
	public static boolean checkDirExistence() {
		_tmpDir = new File(_attemptFolder);
		boolean existance = _tmpDir.exists();
		return existance;
	}

	/**
	 * get the names of the five categories generated from previous attempts
	 */
	public List<String> readCategoriesGenerated(Sections section) {
		readQuestionsAndCategoriesGenerated(section);
		for (Category c : _categories) {
			_categoryNames.add(c.getName());
		}
		return _categoryNames;
	}

	/**
	 * Stores the randomly generated questions in a file named questionsAttempt.txt
	 * or internatinalAttempt.txt Each line contains question,answer,prize,category
	 * 
	 * @param qList   is a list of Question Objects.
	 * @param section questions for NZ categories or International categories
	 */
	public void updateQuestionsGenerated(List<Question> qList, Sections section) {
		FileWriter fw = null;
		try {
			switch(section) {
				case NZ:
					fw = new FileWriter(_questionsAttemptFile);
					break;
				case INTERNATIONAL:
					fw = new FileWriter(_internationalAttemptFile);
					break;
			}

			BufferedWriter bw = new BufferedWriter(fw);
			for (Question q : qList) {
				String questionLine = q.getFormattedString();
				bw.write(questionLine);
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			// add our own exception class to handle runtime exceptions
			throw new QuinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * Get the question objects generated for the specified category.
	 * 
	 * @param section NZ or International
	 * @return List of question objects generated for the specified section.
	 */
	public List<Question> getQuestionsGenerated(Sections section) {
		readQuestionsAndCategoriesGenerated(section);
		return _allQuestions;
	}

	/**
	 * Read the questions from previous attempt/ continue game from last time
	 */
	private void readQuestionsAndCategoriesGenerated(Sections section) {
		String line = null;
		BufferedReader reader = null;
		int questionsPerCategory = 0;
		try {

			switch(section) {
				case NZ:
					reader = new BufferedReader(new FileReader(_questionsAttemptFile));
					questionsPerCategory = _questionsPerCategoryNormal;
					break;
				case INTERNATIONAL:
					reader = new BufferedReader(new FileReader(_internationalAttemptFile));
					questionsPerCategory = _questionsPerCategoryInternational;
					break;
			}
	
			int i = 0;
			int j = 0;
			while ((line = reader.readLine()) != null) {
				String[] questionfields = line.split(",");
				// Add Category names for every 5 or 2 elements
				if (i % questionsPerCategory == 0) {
					_categories.add(new Category(questionfields[3]));
					j++;
				}
				_allQuestions.add(new Question(questionfields[0], questionfields[1], questionfields[2],
						questionfields[4], _categories.get(j - 1))); // j=0,1,2,3,4
				// add the question
				_categories.get(j - 1).add(_allQuestions.get(i)); // when j=0, adds question0,1,2,3,4.j=1 adds5,6,7,8,9
				i++;
			}
			reader.close();
		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}

	}

	/**
	 * resets previous attemptRecord for each question.
	 */
	public void resetAttemptRecord() {
		_record = new int[_numberOfQuestions];
		updateAttemptRecord(Sections.NZ);

	}

	/**
	 * Updates attemptRecord file
	 */
	public void updateAttemptRecord(Sections section) {
		FileWriter fw = null;
		try {

			switch(section) {
				case NZ:
					fw = new FileWriter(_attemptRecord);
					break;
				case INTERNATIONAL:
					fw = new FileWriter(_internationalAttemptRecord);
					break;
			}

			BufferedWriter bw = new BufferedWriter(fw);
			for (int i : _record) {
				bw.write(Integer.toString(i));
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}
	}

	/**
	 * Sets the question index inside record to 1 when the question has been
	 * attempted
	 */
	public void setAttempted(int questionIndex, Sections section) {
		readAttempted(section);
		_record[questionIndex] = 1;
		// save changes inside attemptRecord file
		updateAttemptRecord(section);
	}

	/**
	 * Returns an integer array of size 25, 0 means question[index] is not
	 * attempted, 1 means the question at this index position has been attempted.
	 * 
	 * @return
	 */
	public void readAttempted(Sections section) {
		_record = new int[_numberOfQuestions];
		BufferedReader reader = null;
		String line = null;
		try {

			switch(section) {
				case NZ:
					reader = new BufferedReader(new FileReader(_attemptRecord));
					break;
				case INTERNATIONAL:
					reader = new BufferedReader(new FileReader(_internationalAttemptRecord));
					break;
			}
			int i = 0;
			while ((line = reader.readLine()) != null) {
				_record[i] = Integer.parseInt(line);
				i++;
			}
			reader.close();
		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}
		// return record;
	}

	/**
	 * Get attempt Record
	 */
	public int[] getAttemptedRecord(Sections section) {
		readAttempted(section);
		return _record;
	}

	/**
	 * Reset all attempts/ resets questions,categories and winnings n:number of Q
	 * per category
	 */
	public void resetAll() {
		// removes all the questions
		_allQuestions.clear();
		// removes all the categories
		_categories.clear();
		RandomGenerator rg = new RandomGenerator();

		rg.generateCategoriesAtRandom(3, Sections.INTERNATIONAL);
		rg.generateGameQuestions(2, Sections.INTERNATIONAL);

		// clears attempt record
		_record = new int[_numberOfQuestions];
		// writes to attemptRecord file
		updateAttemptRecord(Sections.NZ);
		updateAttemptRecord(Sections.INTERNATIONAL);
		_winningRec.resetWinnings();
	}

	/**
	 * Read file to get the questions the user got wrong
	 * 
	 * @return List of Question Objects which are the questions users got wrong
	 */
	public List<Question> getWrongQuestions() {
		List<Question> output = new ArrayList<Question>();
		File file = new File(_wrongQuestions);
		if (file.exists()) {
			List<String> list = new ArrayList<String>();
			try {
				String line = null;
				Scanner scanner = new Scanner(new FileReader(_wrongQuestions));
				while (scanner.hasNextLine()) {
					line = scanner.nextLine();
					list.add(line);
				}
				scanner.close();
			} catch (Exception e) {
				throw new QuinzicalExceptions(e.getMessage());
			}

			for (String string : list) {

				String[] data = string.split(",");
				String question = data[0];
				String type = data[2];
				String answer = data[1];
				Question questionObj = new Question(question, answer, null, type, null);
				output.add(questionObj);

			}

		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new QuinzicalExceptions(e.getMessage());
			}
		}
		return output;
	}

	/**
	 * The question user attempted correctly will be removed from the file which
	 * records the questions that they got wrong
	 * 
	 * @param correct the question they attempted correctly
	 */
	public void removeCorrectlyAttemptedQuestion(Question correct) {
		int qIndex = checkIfQuestionExistInFile(correct);
		if (qIndex != -1) {
			// If this question exists in wrongQuestion file
			List<Question> newWrongList = getWrongQuestions();
			newWrongList.remove(qIndex);
			// Write new list to file
			writeWrongQuestionList(newWrongList);
		}

	}

	/**
	 * Returns index in List if this question exists in file, returns -1 if the
	 * question has not been added to file.
	 * 
	 * @param check
	 * @return
	 */
	public int checkIfQuestionExistInFile(Question check) {
		List<Question> wrongQList = getWrongQuestions();
		for (Question wrong : wrongQList) {
			// found Question in file

			if (wrong.getQuestion().equals(check.getQuestion())) {
				return wrongQList.indexOf(wrong);
			}
		}
		return -1;
	}

	/**
	 * Append the question which the user got wrong to file wrongQuestion.txt
	 * 
	 * @param q Question Object
	 */
	public void writeWrongQuestion(Question q) {

		// Only append to file if the question is not inside text file already
		if (checkIfQuestionExistInFile(q) == -1) {
			try {
				FileWriter fw = new FileWriter(_wrongQuestions, true);

				fw.write(q.getFormattedStringWrong());

				fw.close();
			} catch (Exception e) {
				throw new QuinzicalExceptions(e.getMessage());
			}
		}
	}

	/**
	 * Write a list of Questions to wrongQuestion.txt
	 */
	public void writeWrongQuestionList(List<Question> qList) {
		try {
			FileWriter fw = new FileWriter(_wrongQuestions);

			for (Question q : qList) {
				fw.write(q.getFormattedStringWrong());
			}
			fw.close();

		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}
	}
}
