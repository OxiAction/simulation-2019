package barber;

import desmoj.core.simulator.*;
import desmoj.core.dist.*;

/**
 * DESMO-J Barber Model.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class BarberModel extends Model {

	/**
	 * Random generator for customer arrival.
	 */
	private ContDistExponential customerArrivalTime;

	/**
	 * Returns random number for customer arrival.
	 * 
	 * @return
	 */
	public double getCustomerArrivalTime() {
		return customerArrivalTime.sample();
	}

	/**
	 * Random generator for service time at the terminal.
	 */
	private ContDistUniform serviceTime;

	/**
	 * Returns random number for customer service time.
	 * 
	 * @return
	 */
	public double getServiceTime() {
		return serviceTime.sample();
	}

	/**
	 * Customers waiting queue.
	 */
	protected ProcessQueue<CustomerProcess> customerQueue;

	/**
	 * Waiting queue for free terminals.
	 */
	protected ProcessQueue<TerminalProcess> freeTerminalsQueue;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInReport
	 * @param showIntrace
	 */
	public BarberModel(Model owner, String name, boolean showInReport, boolean showIntrace) {
		super(owner, name, showInReport, showIntrace);
	}

	/**
	 * Model description.
	 */
	public String description() {
		return "BarberModel (process oriented): Lorem ipsum";
	}

	/**
	 * Simulation initial stuff.
	 */
	public void doInitialSchedules() {
		// setup new customer process
		NewCustomerProcess newCustomer = new NewCustomerProcess(this, "New Customer", true);
		newCustomer.activate(new TimeSpan(0.0));

		// setup terminal process
		TerminalProcess terminal = new TerminalProcess(this, "Terminal", true);
		terminal.activate(new TimeSpan(0.0));
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

		// free terminals queue
		freeTerminalsQueue = new ProcessQueue<TerminalProcess>(this, "Free Terminals Queue", true, true);
	}

	/**
	 * Entry point.
	 * 
	 * @param args
	 */
	public static void main(java.lang.String[] args) {
		// new experiment
		Experiment barberExperiment = new Experiment("Barber-Process");

		// new model
		BarberModel barberModel = new BarberModel(null, "Barber Model", true, true);

		// connect model with experiment
		barberModel.connectToExperiment(barberExperiment);

		// trace / debug interval
		barberExperiment.tracePeriod(new TimeInstant(0.0), new TimeInstant(60));
		barberExperiment.debugPeriod(new TimeInstant(0.0), new TimeInstant(60));

		// set end time for simulation in minutes
		barberExperiment.stop(new TimeInstant(240));

		// start experiment at time 0.0
		barberExperiment.start();

		// ... simulation running now - on complete - continue:

		// generate report
		barberExperiment.report();

		// cleanup
		barberExperiment.finish();
	}
}