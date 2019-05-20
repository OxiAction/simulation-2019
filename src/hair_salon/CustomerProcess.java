package hair_salon;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 * DESMO-J Customer SimProcess.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class CustomerProcess extends SimProcess {

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
	public CustomerProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		model = (HairSalonModel) owner;
	}

	/**
	 * Customer activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// add customer to customer queue
		model.customerQueue.insert(this);

		// trace length of customer queue
		sendTraceNote("Customer queue length: " + model.customerQueue.length());

		// check if hair stylist is available
		if (!model.hairStylistsQueue.isEmpty()) {
			// fetch and remove hair stylist from hair stylists queue
			HairStylistProcess hairStylist = model.hairStylistsQueue.first();
			model.hairStylistsQueue.remove(hairStylist);

			// activate hair stylist
			hairStylist.activateAfter(this);

			// customer is being serviced
			passivate();
		}
		// all hair stylists busy
		else {
			// customer waiting in the queue
			passivate();
		}

		// trace customer was serviced
		sendTraceNote("Customer was serviced and is now leaving.");
	}
}