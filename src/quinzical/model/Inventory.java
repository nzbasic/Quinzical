package quinzical.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import quinzical.scenes.Theme;

public class Inventory {
    private List<Theme> themes;
    private int hintCount = 0;

    public Inventory() {
        themes = new ArrayList<Theme>();
    }

    public void addTheme(Theme theme) {
        themes.add(theme);
    }

    public void setHintCount(int number) {
        hintCount = number;
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public int getHints() {
        return hintCount;
    }

    public void addHint() {
        hintCount++;
        update();
    }

    public void removeHint() {
        hintCount--;
        update();
    }

    private void update() {
        File hints = new File("./inventory/hints.txt");
        FileWriter fw;
        try {
            fw = new FileWriter(hints);
            fw.write(hintCount);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
