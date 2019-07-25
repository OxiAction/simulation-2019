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
		
		System.out.println("=> starting Bulk testing");
		
		// start seed
		Config.SEED = 1;
		// 10 tests
		Config.NUM_TEST_RUNS = 10;
		
		// testing parameters
		final int TEST_HAIR_STYLISTS_START = 2;
		final int TEST_HAIR_STYLISTS_MAX = 5;
		final int[] TEST_CUSTOMER_ARRIVAL_MEAN_TIMES = {10, 20, 30};
		
		for (int j = 1; j < Config.NUM_TEST_RUNS + 1; ++j) {
			System.out.println("\n=> Test " + j + " / " + Config.NUM_TEST_RUNS);
			
			//different arrival time 10, 20, 30
			for (int i = 0; i < TEST_CUSTOMER_ARRIVAL_MEAN_TIMES.length; ++i) {
				if (i > 0) {
					bufferedWriter.newLine();
				}
				
				// set arrival
				Config.CUSTOMER_ARRIVAL_MEAN = TEST_CUSTOMER_ARRIVAL_MEAN_TIMES[i];
				
				System.out.println("\nConfig.CUSTOMER_ARRIVAL_MEAN = " + Config.CUSTOMER_ARRIVAL_MEAN);
				bufferedWriter.write("Config.CUSTOMER_ARRIVAL_MEAN = " + Config.CUSTOMER_ARRIVAL_MEAN);
				bufferedWriter.newLine();
			
				// test TEST_HAIR_STYLISTS_START to TEST_HAIR_STYLISTS_MAX hair stylists
				for (Config.MAX_HAIR_STYLISTS = TEST_HAIR_STYLISTS_START; Config.MAX_HAIR_STYLISTS < TEST_HAIR_STYLISTS_MAX; ++Config.MAX_HAIR_STYLISTS) {
					System.out.println("\nConfig.MAX_HAIR_STYLISTS = " + Config.MAX_HAIR_STYLISTS);
					bufferedWriter.write("Config.MAX_HAIR_STYLISTS =  " + Config.MAX_HAIR_STYLISTS);
					bufferedWriter.newLine();
					
					Config.SEED += 1;
					
					HairSalonModel hairSalonModel = new HairSalonModel(null, "model", false, false);
					// start experiment and get reports
					Reporter[] reports = hairSalonModel.run();

					for (Reporter reporter : reports) {
						String reporterEntriesToString = Arrays.toString(reporter.getEntries());
						System.out.println(reporterEntriesToString);
						bufferedWriter.write(reporterEntriesToString);
						bufferedWriter.newLine();
					}
				}
			}
		}
		
		bufferedWriter.close();
		
		System.out.println("\n=> done!");
	}
}
