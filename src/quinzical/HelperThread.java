package quinzical;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.stream.Stream;

/**
 * Thread which runs text to speech
 */
public class HelperThread extends Thread {
	private String textToSpeech;
	private int speechRate;

	public HelperThread(String text, int rate) {
		textToSpeech = text;
		speechRate = rate;
	}

	@Override
	public void run() {
		try {
			Stream<ProcessHandle> descendents = ProcessHandle.current().descendants();
			descendents.filter(ProcessHandle::isAlive).forEach(ph -> {
				ph.destroy();
			});
			FileWriter fw = new FileWriter("./attempt/question.scm");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("(voice_akl_nz_jdt_diphone)");
			bw.newLine();
			if (speechRate == 0) {
				bw.write("(Parameter.set \'Duration_Stretch 2.1)");
				bw.newLine();
			} else if (speechRate == 2) {
				bw.write("(Parameter.set \'Duration_Stretch 0.7)");
				bw.newLine();
			}
			textToSpeech = textToSpeech.replace("ā", "aa");
			textToSpeech = textToSpeech.replace("/", "or");
			bw.write("(SayText \"" + textToSpeech + "\")");
			bw.close();
			new ProcessBuilder("bash", "-c", "festival -b ./attempt/question.scm").start();

		} catch (Exception e) {
			// add our own exception class to handle runtime exceptions
			throw new quinzicalExceptions(e.getMessage());
		}
		// bash process
	}
}
