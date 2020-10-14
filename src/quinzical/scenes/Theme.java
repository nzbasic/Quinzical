package quinzical.scenes;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public enum Theme implements ThemeInterface {
    NORMAL {
        public Style getStyle() {
            String color1 = "#FF851B";
            String color2 = "#FFFFFF";
            String color3 = "#121F26";
            String question = "starburst.jpg";
            String menu = "nz.jpg";
            return new Style(menu,question,color1,color2,color3);
        }
    },
    SYNTH {
        public Style getStyle() {
            return null;
        }
    }
}

class Style {
    private Image menu;
    private Image question;
    private Color color1;
    private Color color2;
    private Color color3;
    public Style(String menu, String question, String color1, String color2, String color3) {
        this.menu = generateImage(menu);
        this.question = generateImage(question);
        this.color1 = generateColor(color1);
        this.color2 = generateColor(color2);
        this.color3 = generateColor(color3);
    }
    public Image getMenuImage() {
        return menu;
    }
    private Image generateImage(String string) {
        return new Image(getClass().getResourceAsStream("assets/" + string));
    }
    private Color generateColor(String string) {
        return Color.web(string);
    }

    public Image getMenu() {
        return menu;
    }

    public Image getQuestion() {
        return question;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public Color getColor3() {
        return color3;
    }
}

