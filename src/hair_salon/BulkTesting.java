package src.hair_salon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

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

		double avgWait2 = 0;
		double avgWait3 = 0;
		double avgWait4 = 0;
		double avgWait5 = 0;


		System.out.println("starting Bulk testing");
		
		// start seed
		Config.SEED = 1;
		// 10 tests
		Config.NUM_TEST_RUNS = 10;


		for (int j = 1 ; j<= Config.NUM_TEST_RUNS; j++) {
			System.out.println("\nTest " + j + "/" + Config.NUM_TEST_RUNS);
//			bufferedWriter.write("Test " + j + "/" + Config.NUM_TEST_RUNS);
//			bufferedWriter.newLine();
//			for (int i = 1; i < 6; ++i) {
//				if (i > 1) {
//					bufferedWriter.newLine();
//				}
//
//				System.out.println("\nTest at " + Config.CUSTOMER_ARRIVAL_MEAN + " arrival mean");
//				bufferedWriter.write("Test at " + Config.CUSTOMER_ARRIVAL_MEAN + " arrival mean");
//				bufferedWriter.newLine();

				// test 1-5 hair stylists
				for (Config.MAX_HAIR_STYLISTS = 2; Config.MAX_HAIR_STYLISTS < 6; ++Config.MAX_HAIR_STYLISTS) {
					System.out.println("\nConfig.MAX_HAIR_STYLISTS = " + Config.MAX_HAIR_STYLISTS);
//					bufferedWriter.write("Config.MAX_HAIR_STYLISTS =  " + Config.MAX_HAIR_STYLISTS);
//					bufferedWriter.newLine();

					HairSalonModel hairSalonModel = new HairSalonModel(null, "model", false, false);
					// start experiment and get reports
					Reporter[] reports = hairSalonModel.run();

					String reporterEntriesToString = reports[1].getEntries()[10];
//					String reporterEntriesToString = Arrays.toString(reporter.getEntries());
//					System.out.println(reporterEntriesToString);
//					bufferedWriter.write(reporterEntriesToString);
//					bufferedWriter.newLine();

					if(Config.MAX_HAIR_STYLISTS == 2)
						avgWait2 += Double.parseDouble(reporterEntriesToString);
					else if(Config.MAX_HAIR_STYLISTS == 3)
						avgWait3 += Double.parseDouble(reporterEntriesToString);
					else if(Config.MAX_HAIR_STYLISTS == 4)
						avgWait4 += Double.parseDouble(reporterEntriesToString);
					else if(Config.MAX_HAIR_STYLISTS == 5)
						avgWait5 += Double.parseDouble(reporterEntriesToString);
					
					Config.SEED+=10;

//				}
//				Config.CUSTOMER_ARRIVAL_MEAN += 5.0;
			}

		}
		avgWait2/=10;
		avgWait3/=10;
		avgWait4/=10;
		avgWait5/=10;

		bufferedWriter.write(String.valueOf(avgWait2));
		bufferedWriter.newLine();
		bufferedWriter.write(String.valueOf(avgWait3));
		bufferedWriter.newLine();
		bufferedWriter.write(String.valueOf(avgWait4));
		bufferedWriter.newLine();
		bufferedWriter.write(String.valueOf(avgWait5));
		bufferedWriter.newLine();

		bufferedWriter.close();
		
		System.out.println("finished Bulk testing");
	}
}
