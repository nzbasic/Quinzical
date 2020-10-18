package quinzical.model;

import quinzical.quinzicalExceptions;

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
	private int[] points = { 100, 200, 300, 400, 500 };
	private List<Question> gameQuestions = new ArrayList<Question>();
	
	private AttemptTrack attempt = new AttemptTrack();

	/**
	 * Uses random number generation to generate lists of categories and questions from all loaded questions.
	 * n:number of categories to generate
	 * 
	 */
	public void generateCategoriesAtRandom(int n, String section) {
		allCategoryNames = new ArrayList<String>();
		categories = new ArrayList<Category>();
		randomCategoryNames = new ArrayList<String>();
		File[] files = new File("./categories/"+section).listFiles();
		for (File file : files) {
			allCategoryNames.add(file.getName());
		}

		// Generate n categories at random
		for (int i = 0; i < n; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, allCategoryNames.size());
			// Create n category objects
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
	public List<String> readAllLinesInFile(String nameOfFile,String section) {
		BufferedReader bufReader=null;
		List<String> listOfLines = new ArrayList<>();
		try {
			// read each file
			bufReader = new BufferedReader(new FileReader("./categories/"+section+"/" + nameOfFile));
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
	 * Creates  question objects, must have called generateCategoriesAtRandom()
	 * before calling this method
	 * n=number of questions per category
	 * section: NZ or International
	 * @return
	 */
	public void generateGameQuestions(int n,String section) {
	
		int categoryIndex = 0;
		gameQuestions = new ArrayList<Question>();
		Question q=null;;
		for (String cName : randomCategoryNames) {
			// get all lines inside file
			List<String> questionLines = readAllLinesInFile(cName,section);
			// Generate 5 question objects
			for (int i = 0; i < n; i++) {
				int randomNum = ThreadLocalRandom.current().nextInt(0, questionLines.size());
				// creates a question object and attach to its category object
				String[] questionfields = questionLines.get(randomNum).split(",");
				Category current = categories.get(categoryIndex);
				if (n==5) {
				q = new Question(questionfields[0], questionfields[1],  Integer.toString(points[i]), questionfields[2], current);
				}
				else {
					int index=1+i*2;
			    q = new Question(questionfields[0], questionfields[1],  Integer.toString(points[index]), questionfields[2], current);
				}
				current.add(q);
				gameQuestions.add(q);
				questionLines.remove(randomNum);
			}
			categoryIndex++;
		}
		// Update in file
		attempt.updateQuestionsGenerated(gameQuestions,section);

	}

	/**
	 * Generate one question at Random
	 */
	public Question generatePracticeQuestion(Category c) {
		List<String> avaiableQuestions = readAllLinesInFile(c.getName(),"NZ");
		System.out.println(c.getName());
		// Generate one question Object at Random
		int randomNum = ThreadLocalRandom.current().nextInt(0, avaiableQuestions.size());
		String[] questionfields = avaiableQuestions.get(randomNum).split(",");
		Question q = new Question(questionfields[0], questionfields[1], Integer.toString(0),questionfields[2],  c);
		return q;
	}

	public Question generateRandomQuestionFromList(List<Question> list) {
		int length = list.size();
		int randomNum = ThreadLocalRandom.current().nextInt(0, length);
		return list.get(randomNum);
	}
}