package quinzical;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Loads categories into objects from local files. Used for practice module.
 */
public class CategoryLoader {

    private List<Category> categories;

    /**
     * Default constructor for CategoryLoader. Loads the categories from local files.
     */
    public CategoryLoader() {
        categories = new ArrayList<Category>();
        File[] files = new File("./categories").listFiles();
        Scanner sc = null;
        for (File file : files) {
            Category category = new Category(file.getName());
            try {
                sc = new Scanner(file);

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] data = line.split(",");
                    Question question = new Question(data[0], data[1], "0", category);
                    category.add(question);
                }

                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            categories.add(category);
        }
    }

    /**
     * Returns a list of categories found within the local files.
     * @return
     */
    public List<Category> getCategories() {
        return categories;
    }

}
