package quinzical.data;

/**
 * Enum to store names of Folders we create
 */
public enum Folders {
    CATEGORIES {
        @Override
        public String toString() {
            return "./categories";
        }
    },
    ATTEMPT {
        @Override
        public String toString() {
            return "./attempt";
        }
    },
}
