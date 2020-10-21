package quinzical.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HighscoreTrack {

    private List<Score> scores = new ArrayList<Score>();

    public HighscoreTrack() {
        File file = new File("./scores.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Scanner sc;
        try {
            sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                Score score = new Score(line);
                scores.add(score);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Score> getScores() {
        return scores;
    }

    public void addScore(Score newScore) throws IOException {
        scores.add(newScore);
        File file = new File("./scores.txt");
        FileWriter fw = new FileWriter(file);
        for (Score score : scores) {
            fw.write(score.getScore() + "\n");
        }
        fw.close();
    }
}
