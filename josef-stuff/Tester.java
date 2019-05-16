import java.io.*;
import java.util.Arrays;

import desmoj.core.report.Reporter;

public class Tester {

	public static void main(String[] args) throws IOException {
		
		File logFile = new File("logFile.txt");

		logFile.createNewFile();

		FileWriter w = new FileWriter(logFile);
		BufferedWriter writer = new BufferedWriter(w); 
		
		//Parameters referenz
		
    int seed = 1;
    int friseurCount = 1;
    int ankunftFrequenz = 1*60; // Sekunden!
    int bedienMinimum = 20*60; // Sekunden!
    int bedienMaximum = 40*60; // Sekunden!
    boolean shufflePreferences = false;
    int zeitraum = 8*60*60; // Sekunden!
		
    int friseurMax = 10;
		int frequenzMax = 40*60;
    int frequenzIncr = 60;    
    
		Reporter[] reports = null;
		
		int[][] avg = new int[friseurMax-1][(frequenzMax-ankunftFrequenz)/frequenzIncr];
		int intervall = 0;
		
		for (int anFreq = ankunftFrequenz; anFreq < frequenzMax; anFreq+=frequenzIncr) {
			intervall++;
			for (int frCount = friseurCount; frCount < friseurMax; frCount++) {
				for (int pref = 0; pref < 2; pref++) {
					shufflePreferences = pref == 0 ? false : true;
				
					for (int seedCount = seed; seedCount < 11; seedCount++) {
					
					
						//Run experiment
						FriseurModell modell = new FriseurModell(null, "model", false, false);
						reports = modell.runExperiment(seedCount, frCount, anFreq, bedienMinimum, bedienMaximum, shufflePreferences, zeitraum);
						/*
						//Handle reports
						for(Reporter repo : reports) {
							*/
						
						String log = frCount + "," + anFreq + "," + shufflePreferences + "," + seedCount + ",";
							
						/*
						String[] repo = reports[0].getEntries();
							for (int i = 0; i < repo.length; i++) {
								if (i == 3)
									log += repo[i] + ",";
							}
							/*
							for (String s : repo.getEntries()) {
								log += s + ",";
							}
							*/
						
						avg[frCount-1][intervall-1] += Integer.parseInt(reports[0].getEntries()[3]);
						
						
							//write to log
							//writer.write(log);
							//writer.newLine();
					
					}
				}
				avg[frCount-1][intervall-1] /= 20;
				avg[frCount-1][intervall-1] = (avg[frCount-1][intervall-1]*35)-(frCount*8*40);
			}
		}
				
		for (int i = 0; i < avg.length; i++) {
			String log = "";
			for (int j = 0; j < avg[0].length; j++) {
				
				
				log += avg[i][j] + ",";
				
			}
			writer.write(log);
			writer.newLine();
		}
		
		
		writer.close();
		
		System.out.println("\nFinished!");
		/*
		for(Reporter repo : reports) {
			System.out.println(Arrays.toString(repo.getEntries()));
		}
*/
		
	}
}
