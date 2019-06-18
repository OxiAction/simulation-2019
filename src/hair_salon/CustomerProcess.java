package src.hair_salon;

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

		this.model = (HairSalonModel) owner;
	}

	/**
	 * Customer activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// this customer enters the hair salon
		this.model.customersInHairSalonQueue.insert(this);

		// trace length of customers in salon queue
		this.sendTraceNote("Customers In Salon Queue length: " + this.model.customersInHairSalonQueue.length());
		
		// now this customer is also waiting for being serviced
		this.model.customersWaitingForServiceQueue.insert(this);
		
		// go through all hair stylists
		// note: first is "A", second "B", ...
		for (HairStylistProcess hairStylist : model.hairStylistsQueue) {
			// get first hair stylist available
			if (hairStylist.customers.isEmpty()) {
				// schedule this customer
				hairStylist.activateAfter(this);
				
				break;
			}
		}
		
		// set passive and wait for being serviced
		this.passivate();
	}
	
	/**
	 * Customer leaves the "waiting for service" queue and enters the "being serviced" queue.
	 */
	public void setCustomerBeingServiced() {
		this.model.customersWaitingForServiceQueue.remove(this);
		this.model.customersBeingServicedQueue.insert(this);
	}
	
	/**
	 * Customer leaves the "being serviced" and the "in hair salon" queues.
	 */
	public void setCustomerLeaveHairSalon() {
		this.model.customersBeingServicedQueue.remove(this);
		this.model.customersInHairSalonQueue.remove(this);
	}
}