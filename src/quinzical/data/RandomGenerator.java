package quinzical.data;

import quinzical.QuinzicalExceptions;
import quinzical.data.model.Category;
import quinzical.data.model.Question;
import quinzical.data.tracking.AttemptTrack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Use this class when when user clicks NEW GAME or RESET or PRACTICE
 * 
 * @author se2062020
 *
 */
public class RandomGenerator {

	private List<Category> _categories = new ArrayList<>();
	private List<String> _randomCategoryNames = new ArrayList<>();
	private final int[] _points = { 100, 200, 300, 400, 500 };
	private List<Question> _gameQuestions = new ArrayList<Question>();
	private AttemptTrack _attempt = new AttemptTrack();

	/**
	 * Uses random number generation to generate lists of categories and questions
	 * from all loaded questions. n:number of categories to generate
	 * 
	 */
	public void generateCategoriesAtRandom(int n, Sections section) {
		List<String> allCategoryNames = new ArrayList<String>();
		_categories = new ArrayList<Category>();
		_randomCategoryNames = new ArrayList<String>();
		File[] files = new File(Folders.CATEGORIES.toString() + "/" + section).listFiles();
		for (File file : files) {
			allCategoryNames.add(file.getName());
		}

		// Generate n categories at random
		for (int i = 0; i < n; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, allCategoryNames.size());
			// Create n category objects
			_categories.add(new Category(allCategoryNames.get(randomNum)));
			allCategoryNames.remove(randomNum);
		}
		// Store as list of strings
		for (Category s : _categories) {
			_randomCategoryNames.add(s.getName());
		}
	}

	/**
	 * Must call generateCategoriesAtRandom first.
	 * 
	 * @return List of generated category names.
	 */
	public List<String> getGeneratedCategories() {
		return _randomCategoryNames;
	}

	public void setGameCategories(List<String> usrChoice) {
		for (String s : usrChoice) {
			_randomCategoryNames.add(s);
			_categories.add(new Category(s));
		}
	}

	/**
	 * Reads all lines in a category file
	 * 
	 * @param nameOfFile Name of the file to search
	 * @return A List of strings, one for each line in the file.
	 */
	public List<String> readAllLinesInFile(String nameOfFile, Sections section) {
		BufferedReader bufReader = null;
		List<String> listOfLines = new ArrayList<>();
		try {
			// read each file
			bufReader = new BufferedReader(
					new FileReader(Folders.CATEGORIES.toString() + "/" + section.toString() + "/" + nameOfFile));
			// read all lines in that file
			String line = bufReader.readLine();
			while (line != null) {
				listOfLines.add(line);
				line = bufReader.readLine();
			}
			bufReader.close();

		} catch (Exception e) {
			throw new QuinzicalExceptions(e.getMessage());
		}
		return listOfLines;
	}

	/**
	 * Creates question objects, must have called generateCategoriesAtRandom()
	 * before calling this method
	 * 
	 * @param n=number of questions per category
	 * @param section: NZ or International
	 */
	public void generateGameQuestions(int n, Sections section) {

		int categoryIndex = 0;
		_gameQuestions = new ArrayList<Question>();
		Question q = null;
		;
		for (String cName : _randomCategoryNames) {
			// get all lines inside file
			List<String> questionLines = readAllLinesInFile(cName, section);
			// Generate 5 question objects
			for (int i = 0; i < n; i++) {
				int index = 0;
				int randomNum = ThreadLocalRandom.current().nextInt(0, questionLines.size());
				// creates a question object and attach to its category object
				String[] questionfields = questionLines.get(randomNum).split(Question.delimiter);
				Category current = _categories.get(categoryIndex);
				if (n == 5) {
					index = i;
				} else {
					index = 1 + i * 2; // 2 questions per category
				}
				q = new Question(questionfields[0], questionfields[1], Integer.toString(_points[index]),
						questionfields[2], current);
				current.add(q);
				_gameQuestions.add(q);
				questionLines.remove(randomNum);
			}
			categoryIndex++;
		}
		// Update in file
		_attempt.updateQuestionsGenerated(_gameQuestions, section);

	}

	/**
	 * Generate one question at Random
	 */
	public Question generatePracticeQuestion(Category c) {
		List<String> avaiableQuestions = readAllLinesInFile(c.getName(), Sections.NZ);
		// Generate one question Object at Random
		int randomNum = ThreadLocalRandom.current().nextInt(0, avaiableQuestions.size());
		String[] questionfields = avaiableQuestions.get(randomNum).split(Question.delimiter);
		Question q = new Question(questionfields[0], questionfields[1], Integer.toString(0), questionfields[2], c);
		return q;
	}

	/**
	 * Generated a random question from a list of questions.
	 * 
	 * @param list List of questions
	 * @return Random question object
	 */
	public Question generateRandomQuestionFromList(List<Question> list) {
		int length = list.size();
		int randomNum = ThreadLocalRandom.current().nextInt(0, length);
		return list.get(randomNum);
	}
}