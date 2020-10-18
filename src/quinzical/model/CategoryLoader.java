package quinzical.model;

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

    public CategoryLoader(String section) {
        categories = new ArrayList<Category>();
        
        File[] files = new File("./categories/"+section).listFiles();
        Scanner sc = null;
        for (File file : files) {
            Category category = new Category(file.getName());
            try {
                sc = new Scanner(file);

                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] data = line.split(",");
                    Question question = new Question(data[0], data[1], "0", data[2], category);
                    category.add(question);
                }

                sc.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            categories.add(category);
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

}