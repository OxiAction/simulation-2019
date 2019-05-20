package hair_salon;

public class Config {
	/**
	 * Number of hair stylists.
	 */
	static final int MAX_HAIR_STYLISTS = 3;
	
	/**
	 * Runtime (in minutes) for the simulation.
	 * 6 days * 8 hours * 60 minutes = 48 hours / week or 2880 minutes / week
	 */
	static final int RUNTIME_MINUTES = 6 * 8 * 60;
	
	/**
	 * Mean value for the customer arrival.
	 */
	static final double CUSTOMER_ARRIVAL_MEAN = 20.0;
	
	/**
	 * Service time lower bound.
	 */
	static final double SERVICE_TIME_LOWER = 20.0;
	
	/**
	 * Service time upper bound.
	 */
	static final double SERVICE_TIME_UPPER = 40.0;
}