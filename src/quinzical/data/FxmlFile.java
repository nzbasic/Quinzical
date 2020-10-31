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
	},
	ADD_QUESTION {
		@Override
		public String toString() {
			return "AddQuestion";
		}
	},
	INTERNATIONAL_QUESTIONS {
		@Override
		public String toString() {
			return "InternationalQuestions";
		}
	},
	GAME {
		@Override
		public String toString() {
			return "Game";
		}
	},
	PRACTICE {
		@Override
		public String toString() {
			return "Practice";
		}
	},
	QUESTION_AND_ANSWER {
		@Override
		public String toString() {
			return "QuestionAndAnswer";
		}
	},
	REWARD {
		@Override
		public String toString() {
			return "Reward";
		}
	},

	FOLDER {
		public String getPath() {
			return "fxml/";
		}
	}
}
