package hair_salon;

import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

import desmoj.core.dist.*;

/**
 * DESMO-J Hair Salon Model.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class HairSalonModel extends Model {

	/**
	 * Random generator for customer arrival.
	 */
	protected ContDistExponential customerArrivalTime;

	/**
	 * Random generator for service time (when serviced by a hair stylist).
	 */
	protected ContDistUniform serviceTime;

	/**
	 * Customers waiting queue.
	 */
	protected ProcessQueue<CustomerProcess> customerQueue;

	/**
	 * Hair Stylists queue.
	 */
	protected ProcessQueue<HairStylistProcess> hairStylistsQueue;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInReport
	 * @param showIntrace
	 */
	public HairSalonModel(Model owner, String name, boolean showInReport, boolean showIntrace) {
		super(owner, name, showInReport, showIntrace);
	}

	/**
	 * Model description.
	 */
	public String description() {
		return "Hair Salon Model (process oriented)";
	}

	/**
	 * Simulation initial stuff.
	 */
	public void doInitialSchedules() {
		// setup new customer process
		NewCustomerProcess newCustomer = new NewCustomerProcess(this, "New Customer", true);
		newCustomer.activate(new TimeSpan(0.0));

		// setup hair stylist process
		HairStylistProcess hairStylist = new HairStylistProcess(this, "Hair Stylist", true);
		hairStylist.activate(new TimeSpan(0.0));
	}

	/**
	 * DESMO-J init.
	 */
	public void init() {
		// customer arrival time
		// mean: 20 (minutes)
		customerArrivalTime = new ContDistExponential(this, "Customer Arrival Time", 20.0, true, true);
		// exponential distribution may give us negative values
		customerArrivalTime.setNonNegative(true);

		// service time (~ 30 minutes)
		// min: 20 (minutes)
		// max: 40 (minutes)
		serviceTime = new ContDistUniform(this, "Service Time", 20.0, 40.0, true, true);

		// customer queue
		customerQueue = new ProcessQueue<CustomerProcess>(this, "Customer Queue", true, true);

		// hair stylists queue
		hairStylistsQueue = new ProcessQueue<HairStylistProcess>(this, "Hair Stylists Queue", true, true);
	}

	/**
	 * Entry point.
	 * 
	 * @param args
	 */
	public static void main(java.lang.String[] args) {
		Experiment experiment = new Experiment("Hair-Salon-Process");
		
		HairSalonModel hairSalonModel = new HairSalonModel(null, "Hair Salon Model", true, true);
		hairSalonModel.connectToExperiment(experiment);
		
		experiment.setShowProgressBar(true);						// progress bar visibillity
		experiment.debugOn(new TimeInstant(0));						// enable debug
	    experiment.traceOn(new TimeInstant(0));						// enable trace
		experiment.stop(new TimeInstant(10080, TimeUnit.MINUTES));	// 60 (minutes) * 24 (hours) * 7 (days) = 10080 (minutes)
		experiment.start();											// start experiment at time 0.0
		// ... simulation running now - on complete - continue:
		experiment.report();										// generate report
		experiment.finish();										// cleanup
	}
}