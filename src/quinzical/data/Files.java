package quinzical.data;

/**
 * Enum to store names of files we create
 */
public enum Files {
    ATTEMPT_RECORD {
        @Override
        public String toString() {
            return "/.attemptRecord.txt";
        }
    },
    BONUS_ATTEMPT_RECORD {
        @Override
        public String toString() {
            return "/.bonusAttemptRecord.txt";
        }
    },
    INTERNATIONAL_ATTEMPT {
        @Override
        public String toString() {
            return "/.internationalAttempt.txt";
        }
    },
    QUESTIONS_ATTEMPT {
        @Override
        public String toString() {
            return "/.questionsAttempt.txt";
        }
    },
    WINNINGS {
        @Override
        public String toString() {
            return "/.winnings.txt";
        }
    },
    WRONG_QUESTIONS {
        @Override
        public String toString() {
            return "/.wrongQuestions.txt";
        }
    },
    SCORES {
        @Override
        public String toString() {
            return "/.scores.txt";
        }
    },
    QUESTIONSCM {
        @Override
        public String toString() {
            return "/.question.scm";
        }
    }
}