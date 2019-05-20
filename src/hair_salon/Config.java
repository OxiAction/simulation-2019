package hair_salon;

public class Config {
	/**
	 * Number of hair stylists.
	 */
	static final int MAX_HAIR_STYLISTS = 3;
	
	/**
	 * Runtime (in days) for the simulation.
	 */
	static final int RUNTIME_DAYS = 7;
	
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