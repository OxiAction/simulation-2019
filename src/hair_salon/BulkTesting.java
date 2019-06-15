package hair_salon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import desmoj.core.report.Reporter;

public class BulkTesting {
	
	/**
	 * Entry point.
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(java.lang.String[] args) throws IOException {
		// log
		File file = new File("reports/bulk_testing_log.txt");
		file.createNewFile();
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		System.out.println("starting Bulk testing");
		
		// start seed
		Config.SEED = 1;
		// 10 tests
		Config.NUM_TEST_RUNS = 10;
		
		for (int i = 1; i < Config.NUM_TEST_RUNS + 1; ++i) {
			if (i > 1) {
				bufferedWriter.newLine();
			}
			
			System.out.println("\nTest " + i + " / " + Config.NUM_TEST_RUNS);
			bufferedWriter.write("Test " + i);
			bufferedWriter.newLine();
			
			// test 1-5 hair stylists
			for (Config.MAX_HAIR_STYLISTS = 1; Config.MAX_HAIR_STYLISTS < 6; ++Config.MAX_HAIR_STYLISTS) {
				System.out.println("\nConfig.MAX_HAIR_STYLISTS = " + Config.MAX_HAIR_STYLISTS);
				bufferedWriter.write("Config.MAX_HAIR_STYLISTS =  " + Config.MAX_HAIR_STYLISTS);
				bufferedWriter.newLine();
				
				HairSalonModel hairSalonModel = new HairSalonModel(null, "model", false, false);
				// start experiment and get reports
				Reporter[] reports = hairSalonModel.run();
				
				for (Reporter reporter : reports) {
					String reporterEntriesToString = Arrays.toString(reporter.getEntries());
					System.out.println(reporterEntriesToString);
					bufferedWriter.write(reporterEntriesToString);
					bufferedWriter.newLine();
				}
				
				++Config.SEED;
			}
		}
		
		bufferedWriter.close();
		
		System.out.println("finished Bulk testing");
	}
}
