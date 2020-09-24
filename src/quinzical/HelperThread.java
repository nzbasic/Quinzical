package quinzical;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class HelperThread extends Thread{
    private String textToSpeech;
    private int speechRate;
    
	public HelperThread(String text,int rate) {
		textToSpeech=text;
		speechRate=rate;
	}
	@Override
	public void run() {
		try {
			 
			FileWriter fw = new FileWriter("./attempt/question.scm");
			BufferedWriter bw = new BufferedWriter(fw);
		    bw.write("(voice_akl_nz_jdt_diphone)");
		    bw.newLine();
		    if (speechRate==0) {
		    bw.write("(Parameter.set \'Duration_Stretch 2.1)");
		    bw.newLine();
		    }
		    else if (speechRate==2) {
		    	bw.write("(Parameter.set \'Duration_Stretch 0.7)");
			    bw.newLine();
		    }
		    textToSpeech=textToSpeech.replace("ā", "aa");
		    bw.write("(SayText \""+textToSpeech+"\")");
			bw.close();
			ProcessBuilder verify = new ProcessBuilder("bash", "-c", "festival -b ./attempt/question.scm");
				Process ansProcess = verify.start();
	   } catch (Exception e) {
			//add our own exception class to handle runtime exceptions
			throw new quinzicalExceptions(e.getMessage());
		}
		//bash process
	}
}
