package hair_salon;

import desmoj.core.simulator.*;
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
		// customer arrival time: use mean time
		customerArrivalTime = new ContDistExponential(this, "Customer Arrival Time", 3.0, true, true);
		// exponential distribution may give us negative values
		customerArrivalTime.setNonNegative(true);

		// service time: min and max
		serviceTime = new ContDistUniform(this, "Service Time", 0.5, 10.0, true, true);

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
		// new experiment
		Experiment hairSalonExperiment = new Experiment("Hair-Salon-Process");

		// new model
		HairSalonModel hairSalonModel = new HairSalonModel(null, "Hair Salon Model", true, true);

		// connect model with experiment
		hairSalonModel.connectToExperiment(hairSalonExperiment);

		// trace / debug interval
		hairSalonExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		hairSalonExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));

		// set end time for simulation in minutes
		hairSalonExperiment.stop(new TimeInstant(240));

		// start experiment at time 0.0
		hairSalonExperiment.start();

		// ... simulation running now - on complete - continue:

		// generate report
		hairSalonExperiment.report();

		// cleanup
		hairSalonExperiment.finish();
	}
}