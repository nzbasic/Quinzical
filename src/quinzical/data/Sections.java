package quinzical.data;

/**
 * Enum for Sections of the game 
 */
public enum Sections {
   NZ,
   INTERNATIONAL {
      @Override 
      public String toString() {
         return "International";
      }
   } 
}
