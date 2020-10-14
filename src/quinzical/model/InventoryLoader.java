package quinzical.model;

import java.io.File;
import java.util.Scanner;

import quinzical.scenes.Theme;

public class InventoryLoader {
    private Inventory inventory;
    public InventoryLoader() {
        inventory = new Inventory();
        File themes = new File("./inventory/themes.txt");
        File hints = new File("./inventory/hints.txt");
        try {
            Scanner themeScanner = new Scanner(themes);
            Scanner hintScanner = new Scanner(hints);
            while (themeScanner.hasNextLine()) {
                String line = themeScanner.nextLine();
                Theme theme = Theme.valueOf(line);
                inventory.addTheme(theme);
            }
            while (hintScanner.hasNextLine()) {
                String line = hintScanner.nextLine();
                int hintCount = Integer.parseInt(line);
                inventory.setHintCount(hintCount);
            }
            themeScanner.close();
            hintScanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Inventory getInventory() {
        return inventory;
    }
}
