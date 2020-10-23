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
            return "./.attempt";
        }
    },
    CATEGORIES_INTERNATIONAL {
        @Override
        public String toString() {
            return CATEGORIES.toString() + "/International";
        }
    },
    CATEGORIES_NZ {
        @Override
        public String toString() {
            return CATEGORIES.toString() + "/NZ";
        }
    },
}
