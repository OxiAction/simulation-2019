package barber;

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
	private BarberModel model;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public NewCustomerProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		model = (BarberModel) owner;
	}

	/**
	 * New customer activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// keep alive
		while (true) {
			// hold process until next customer needs to be created
			hold(new TimeSpan(model.getCustomerArrivalTime()));

			// create a new customer
			CustomerProcess customer = new CustomerProcess(model, "Customer", true);

			// activate new customer
			customer.activateAfter(this);

		}
	}
}