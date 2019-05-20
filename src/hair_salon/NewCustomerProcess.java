package hair_salon;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 * DESMO-J New Customer SimProcess.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class NewCustomerProcess extends SimProcess {

	/**
	 * Model reference.
	 */
	private HairSalonModel model;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public NewCustomerProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		model = (HairSalonModel) owner;
	}

	/**
	 * New customer activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// keep alive
		while (true) {
			// hold process until next customer needs to be created
			// -> deactivate the process for the time being,
			//    using random number for customer arrival time
			hold(new TimeSpan(model.customerArrivalTime.sample()));

			// create a new customer
			CustomerProcess customer = new CustomerProcess(model, "Customer", true);

			// activate new customer
			customer.activateAfter(this);

		}
	}
}