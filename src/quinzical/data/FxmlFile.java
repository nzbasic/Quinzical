package quinzical.data;

/**
 * Enum to store names and paths of FXML files we have created
 */
public enum FxmlFile implements IFxmlFile {

    MENU {
        @Override
        public String toString() {
            return "Menu";
        }
        public String getPath() {
            return FOLDER.getPath() + "Menu.fxml";
        }
    },
    ADD_QUESTION {
        @Override
        public String toString() {
            return "AddQuestion";
        }
        public String getPath() {
            return FOLDER.getPath() + "AddQuestion.fxml";
        }
    },
    INTERNATIONAL_QUESTIONS {
        @Override
        public String toString() {
            return "InternationalQuestions";
        }
        public String getPath() {
            return FOLDER.getPath() + "InternationalQuestions.fxml";
        }
    },
    GAME {
        @Override
        public String toString() {
            return "Game";
        }
        public String getPath() {
            return FOLDER.getPath() + "Game.fxml";
        }
    },
    PRACTICE {
        @Override
        public String toString() {
            return "Practice";
        }
        public String getPath() {
            return FOLDER.getPath() + "Practice.fxml";
        }
    },
    QUESTION_AND_ANSWER {
        @Override
        public String toString() {
            return "QuestionAndAnswer";
        }
        public String getPath() {
            return FOLDER.getPath() + "QuestionAndAnswer.fxml";
        }
    },
    REWARD {
        @Override
        public String toString() {
            return "Reward";
        }
        public String getPath() {
            return FOLDER.getPath() + "Reward.fxml";
        }
    },

    FOLDER {
        public String getPath() {
            return "./fxml/";
        }
    }


}
