package hair_salon;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 * DESMO-J Hair Stylist SimProcess.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class HairStylistProcess extends SimProcess {

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
	public HairStylistProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		model = (HairSalonModel) owner;
	}

	/**
	 * Terminal activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// keep alive
		while (true) {
			if (model.customerQueue.isEmpty()) {
				// add hair stylist to queue
				model.hairStylistsQueue.insert(this);

				// wait
				passivate();
			} else {
				// fetch and remove customer from customer queue
				CustomerProcess customer = model.customerQueue.first();
				model.customerQueue.remove(customer);

				// customer is being serviced
				// -> deactivate the process for the time being,
				//    using random number for customer service time
				hold(new TimeSpan(model.serviceTime.sample()));

				// customer was serviced and can now leave terminal
				// -> reactivate
				customer.activate(new TimeSpan(0.0));
			}
		}
	}
}