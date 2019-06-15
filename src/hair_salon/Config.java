package hair_salon;

public class Config {
	/**
	 * Number of hair stylists.
	 */
	static int MAX_HAIR_STYLISTS = 3;
	
	/**
	 * Runtime (in minutes) for the simulation.
	 * 6 days * 8 hours * 60 minutes = 48 hours / week or 2880 minutes / week
	 */
	static int RUNTIME_MINUTES = 6 * 8 * 60;
	
	/**
	 * Mean value for the customer arrival.
	 */
	static double CUSTOMER_ARRIVAL_MEAN = 20.0;
	
	/**
	 * Service time lower bound.
	 */
	static double SERVICE_TIME_LOWER = 20.0;
	
	/**
	 * Service time upper bound.
	 */
	static double SERVICE_TIME_UPPER = 40.0;
	
	/**
	 * Seed - default 979.
	 */
	static int SEED = 979;
	
	/**
	 * Number of test runs.
	 */
	static int NUM_TEST_RUNS = 10;
}