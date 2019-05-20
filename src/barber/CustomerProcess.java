package barber;

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
	private BarberModel model;

	/**
	 * Constructor.
	 * 
	 * @param owner
	 * @param name
	 * @param showInTrace
	 */
	public CustomerProcess(Model owner, String name, boolean showInTrace) {
		super(owner, name, showInTrace);

		model = (BarberModel) owner;
	}

	/**
	 * Customer activities.
	 */
	public void lifeCycle() throws SuspendExecution {
		// add customer to customer queue
		model.customerQueue.insert(this);

		// trace length of customer queue
		sendTraceNote("Customer queue length: " + model.customerQueue.length());

		// check if terminal available
		if (!model.freeTerminalsQueue.isEmpty()) {
			// fetch and remove terminal from free terminals queue
			TerminalProcess terminal = model.freeTerminalsQueue.first();
			model.freeTerminalsQueue.remove(terminal);

			// activate terminal immediately
			terminal.activateAfter(this);

			// customer is being serviced
			passivate();
		}
		// all terminals busy
		else {
			// customer waiting in the queue
			passivate();
		}

		// trace customer was serviced
		sendTraceNote("Customer was serviced and is now leaving.");
	}
}