package src.hair_salon;

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
	 * Customers of this hair stylist.
	 */
	public ProcessQueue<CustomerProcess> customers;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public HairStylistProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		this.model = (HairSalonModel) owner;
		
		// queue - mainly for debugging
		this.customers = new ProcessQueue<CustomerProcess>(this.model, "Customers of " + name, true, true);
	}

	/**
	 * Hair Stylist life cycle.
	 */
	public void lifeCycle() throws SuspendExecution {
		// keep alive
		while (true) {

			//removing all non-serviced customers from hairsalon
			if (((int) this.presentTime().getTimeAsDouble()) % 8 * 60 == 0) {
				this.model.customersInHairSalonQueue.removeAll();
				this.model.customersWaitingForServiceQueue.removeAll();
			}


			// any customer waiting for being serviced?
			
			// no :(
			if (this.model.customersWaitingForServiceQueue.isEmpty()) {
				this.passivate();
			} 
			// yes :D
			else {
				// fetch first waiting customer
				CustomerProcess customer = this.model.customersWaitingForServiceQueue.first();
				
				// add it to this hair stylists customers queue
				this.customers.insert(customer);
				
				// customer is now being serviced...
				customer.setCustomerBeingServiced();
				// ...and this hair stylist is busy!
				this.hold(new TimeSpan(this.model.serviceTime.sample()));
				
				// -> when the hair stylist is ready:
				
				// remove customer from this hair stylists customers queue...
				this.customers.remove(customer);
				// ...and make the customer leave the hair salon
				customer.setCustomerLeaveHairSalon();
				customer.activate();	
			}
		}
	}
}