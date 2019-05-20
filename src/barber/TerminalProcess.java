package barber;

import desmoj.core.simulator.*;
import co.paralleluniverse.fibers.SuspendExecution;

/**
 * DESMO-J Terminal SimProcess.
 * 
 * @author Michael Schreiber, Markus Innerlohinger
 *
 */
public class TerminalProcess extends SimProcess {

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
	public TerminalProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		model = (BarberModel) owner;
	}

	/**
	 * Terminal activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// keep alive
		while (true) {
			if (model.customerQueue.isEmpty()) {
				// add terminal to queue
				model.freeTerminalsQueue.insert(this);

				// wait
				passivate();
			} else {
				// fetch and remove customer from customer queue
				CustomerProcess customer = model.customerQueue.first();
				model.customerQueue.remove(customer);

				// customer is being serviced
				// -> deactivate the process for the time being
				hold(new TimeSpan(model.getServiceTime()));

				// customer was serviced and can now leave terminal
				// -> reactivate
				customer.activate(new TimeSpan(0.0));
			}
		}
	}
}