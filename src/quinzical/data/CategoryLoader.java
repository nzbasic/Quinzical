package quinzical.data;

import quinzical.data.model.Category;
import quinzical.data.model.Question;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads categories into objects from local files. Used for practice module.
 */
public class CategoryLoader {

	private List<Category> _categories;

	/**
	 * Constructor for category loader
	 * 
	 * @param section One of the sections of the game, eg NZ or INTERNATIONAL
	 */
	public CategoryLoader(Sections section) {
		_categories = new ArrayList<Category>();

		File[] files = new File(Folders.CATEGORIES.toString() + "/" + section).listFiles();
		Scanner sc = null;
		for (File file : files) {
			Category category = new Category(file.getName());
			try {
				sc = new Scanner(file);

				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] data = line.split(Question.delimiter);
					Question question = new Question(data[0], data[1], "0", data[2], category);
					category.add(question);
				}

				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			_categories.add(category);
		}
	}

	/**
	 * @return Stored list of categories.
	 */
	public List<Category> getCategories() {
		return _categories;
	}

}