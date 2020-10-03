package quinzical;

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

	private List<Category> categories = new ArrayList<Category>();
	private List<String> allCategoryNames = new ArrayList<String>();
	private List<String> randomCategoryNames = new ArrayList<String>();
	private List<Question> gameQuestions = new ArrayList<Question>();
	private int[] points = { 100, 200, 300, 400, 500 };
	private AttemptTrack attempt = new AttemptTrack();

	/**
	 * Uses random number generation to generate lists of categories and questions from all loaded questions.
	 */
	public void generateCategoriesAtRandom() {
		allCategoryNames = new ArrayList<String>();
		File[] files = new File("./categories").listFiles();
		for (File file : files) {
			allCategoryNames.add(file.getName());
		}

		// Generate 5 categories at random
		for (int i = 0; i < 5; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, allCategoryNames.size());
			// Create 5 category objects
			categories.add(new Category(allCategoryNames.get(randomNum)));
			allCategoryNames.remove(randomNum);
		}
		// Store as list of strings
		for (Category s : categories) {
			randomCategoryNames.add(s.getName());
		}
	}

	/**
	 * Must call generateCategoriesAtRandom first.
	 * @return List of generated category names.
	 */
	public List<String> getGeneratedCategories() {
		return randomCategoryNames;
	}

	/**
	 * Reads all lines in a category file
	 * @param nameOfFile Name of the file to search
	 * @return A List of strings, one for each line in the file.
	 */
	public List<String> readAllLinesInFile(String nameOfFile) {
		List<String> listOfLines = new ArrayList<>();
		try {
			// read each file
			BufferedReader bufReader = new BufferedReader(new FileReader("./categories/" + nameOfFile));
			// read all lines in that file
			String line = bufReader.readLine();
			while (line != null) {
				listOfLines.add(line);
				line = bufReader.readLine();
			}
			bufReader.close();

		} catch (Exception e) {
			throw new quinzicalExceptions(e.getMessage());
		}
		return listOfLines;
	}

	/**
	 * Creates 25 question objects, must have called generateCategoriesAtRandom()
	 * before calling this method
	 * 
	 * @return
	 */
	public void generateGameQuestions() {
		int categoryIndex = 0;
		for (String cName : randomCategoryNames) {
			// get all lines inside file
			List<String> questionLines = readAllLinesInFile(cName);
			// Generate 5 question objects
			for (int i = 0; i < 5; i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, questionLines.size());
				// creates a question object and attach to its category object
				String[] questionfields = questionLines.get(randomNum).split(",");
				Category current = categories.get(categoryIndex);
				Question q = new Question(questionfields[0], questionfields[1], Integer.toString(points[i]), current);
				current.add(q);
				gameQuestions.add(q);
				questionLines.remove(randomNum);
			}
			categoryIndex++;
		}
		// Update in file
		attempt.updateQuestionsGenerated(gameQuestions);

	}

	/**
	 * Generate one question at Random
	 */
	public Question generatePracticeQuestion(Category c) {
		List<String> avaiableQuestions = readAllLinesInFile(c.getName());
		// Generate one question Object at Random
		int randomNum = ThreadLocalRandom.current().nextInt(0, avaiableQuestions.size());
		String[] questionfields = avaiableQuestions.get(randomNum).split(",");
		Question q = new Question(questionfields[0], questionfields[1], Integer.toString(0), c);
		return q;
	}
}